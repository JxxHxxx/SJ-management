package com.jx.management.salerecord.application;

import com.jx.management.common.application.JxSheetUtils;
import com.jx.management.salerecord.domain.*;
import com.jx.management.salerecord.transfer.AnnualSaleRecordStatTransfer;
import com.jx.management.salerecord.transfer.MonthlySaleRecordStatTransfer;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.jx.management.common.endpoint.ResponseCode.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class SaleRecordService {

    @Value("${search.default.year}")
    private int searchDefaultYear;

    private static final int SALE_RECORD_SHEET_IDX = 0;
    private static final int GAME_NAME_IDX = 0;
    private static final int SERVER_NAME_IDX = 1;

    private static final int SALE_RECORD_METADATA_ROW_NUM = 0;

    private static final int CATEGORY_COLUMN_IDX = 0;
    private static final int TX_TITLE_COLUMN_IDX = 2;
    private static final int TX_AMOUNT_COLUMN_IDX = 3;
    private static final int TX_ENROLL_DATE_COLUMN_IDX = 4;

    private static final String CATEGORY_DELIMITER = ">";

    private final SaleRecordRepository saleRecordRepository;
    private final GameAccountRepository gameAccountRepository;

    // 엑셀 등록
    @Transactional(noRollbackFor = NoRollbackSaleRecordServiceException.class)
    public String record(MultipartFile file) throws IOException {
        // 시트 가져오기 시작 - 구 엑셀 버전 호환 로직
        Workbook sheets;
        if (file.getOriginalFilename().endsWith(".xls")) {
            sheets = new HSSFWorkbook(file.getInputStream());
        } else {
            sheets = WorkbookFactory.create(file.getInputStream());
        }
        // 시트 가져오기 종료
        Sheet sheet = sheets.getSheetAt(SALE_RECORD_SHEET_IDX);
        Set<SaleRecord> pendingSaleRecords = new HashSet<>();

        Row metaRow = sheet.getRow(SALE_RECORD_METADATA_ROW_NUM);
        // 검증 로직
        boolean validMetaRow = validateMetaRow(metaRow);
        if (!validMetaRow) {
            throw new SaleRecordServiceException(F_SR01);
        }
        else {
            log.info("valid meta row process ongoing");
        }

        sheet.removeRow(metaRow);

        for (Row row : sheet) {
            String gameName = null;
            String gameServerName = null;
            Integer txAmount = null;
            String txTitle = null;
            LocalDateTime enrollDateTime = null;

            for (Cell cell : row) {
                switch (cell.getColumnIndex()) {
                    case CATEGORY_COLUMN_IDX -> {
                        String categoryValue = cell.getStringCellValue();
                        String[] categoryElements = categoryValue.split(CATEGORY_DELIMITER);
                        gameName = categoryElements[GAME_NAME_IDX].strip();
                        gameServerName = categoryElements[SERVER_NAME_IDX].strip(); // 공백처리 추가
                        log.trace("Category value is {} {}", gameName, gameServerName);
                    }
                    case TX_TITLE_COLUMN_IDX -> txTitle = cell.getStringCellValue();
                    case TX_AMOUNT_COLUMN_IDX -> txAmount = (int) cell.getNumericCellValue();
                    case TX_ENROLL_DATE_COLUMN_IDX -> enrollDateTime = JxSheetUtils.format(cell.getNumericCellValue());
                }
            }

            SaleRecord saleRecord = SaleRecord.builder()
                    .txEnrollDateTime(enrollDateTime)
                    .txTitle(txTitle)
                    .txAmount(txAmount)
                    .userId("admin")
                    .gameServerName(gameServerName)
                    .gameName(gameName)
                    .build();

            // 중복되어 set 자료구조에 저장되지 않은 레코드는 로그로 기록
            if (!pendingSaleRecords.add(saleRecord)) {
                log.info("[duplicated sale record] {} not added", saleRecord.toString());
            }
        }
        // 이미 저장되어 있는 SaleRecord 제외
        List<SaleRecord> persistenceSaleRecords = saleRecordRepository.findByUserId("admin");
        for (SaleRecord persistenceSaleRecord : persistenceSaleRecords) {
            boolean remove = pendingSaleRecords.remove(persistenceSaleRecord);
            if (remove) {
                log.trace("except already persistence saleRecord {}", persistenceSaleRecord);
            }
        }

        try {
            saleRecordRepository.saveAll(pendingSaleRecords);
        }
        // 제약 조건 위배 - 게임 명, 서버 명, 거래 등록일, 사용자 ID 멀티 인덱스 중복 X 조건 위배 확률
        // 이미 저장되어 있는 거래 내역을 등록하려고 할 때 제약 사항 위배 에러가 뜨게 됨
        catch (DataIntegrityViolationException exception) {
            log.error("some sale record already persist, tx rollback" , exception);
            throw new SaleRecordServiceException(F_SR02);

        }
        pendingSaleRecords.isEmpty();
        return pendingSaleRecords.isEmpty() ?
                "success, but 0 saleRecord uploaded because already persistence" :
                "success," + persistenceSaleRecords.size() + " saleRecord";
    }

    // 엑셀 파일 내 첫 행이 형식에 맞게 배열되어 있는지 검증
    private boolean validateMetaRow(Row row) {
        String category = row.getCell(0).getStringCellValue();
        String classification = row.getCell(1).getStringCellValue();
        String title = row.getCell(2).getStringCellValue();
        String amount = row.getCell(3).getStringCellValue();
        String enrollDateTime = row.getCell(4).getStringCellValue();

        return StringUtils.pathEquals(category, "카테고리") && StringUtils.pathEquals(classification, "분류") &&
                StringUtils.pathEquals(title, "제목") && StringUtils.pathEquals(amount, "거래금액") &&
                StringUtils.pathEquals(enrollDateTime, "등록일");
    }

    public List<AnnualSaleRecordStatTransfer> getAnnualSaleRecordStatistics(Integer year) {
        if (year == 0) {
            year = searchDefaultYear;
        }
        return saleRecordRepository.getAnnualSaleRecordStatistics(year);
    }

    /**
     * @Params mys : 조회 기간
     *  e.g) 2025-02-02 기준 mys=3 -> 2024-12, 2025-01, 2025-02 판매 통계 데이터 조회
     *
     *  TODO 2025-02-15 만약 매출이 없는 달의 경우 DB상에 조회되지 않기에 수기 반영
     **/
    public SortedMap<String, Integer> getMonthlySaleRecordStatistics(int mys) {
        if (mys <= 0) {
            log.error("mys must be greater than 0");
            throw new SaleRecordServiceException(F_SR11);
        }

        SortedMap<String, Integer> results = new TreeMap(Collections.reverseOrder());
        for (int my = 0; my < mys; my++) {
            results.put(LocalDate.now()
                    .minusMonths(my)
                    .withDayOfMonth(1)
                    .format(DateTimeFormatter.ofPattern("yyyy-MM")), 0);
        }

        List<MonthlySaleRecordStatTransfer> monthlySaleRecordStatTransfers = saleRecordRepository.getMonthlySaleRecordStatistics(LocalDate.now()
                .minusMonths(mys - 1)
                .withDayOfMonth(1)
                .toString());

        for (MonthlySaleRecordStatTransfer msr : monthlySaleRecordStatTransfers) {
            results.put(msr.getYearMonth(), msr.getAmountSum());
        }

        return results;
    }
}
