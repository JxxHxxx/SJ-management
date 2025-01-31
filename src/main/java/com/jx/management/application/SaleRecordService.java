package com.jx.management.application;

import com.jx.management.domain.SaleRecord;
import com.jx.management.domain.GameItemSalesHistRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SaleRecordService {

    private static final int SALE_RECORD_SHEET_IDX = 0;
    private static final int GAME_NAME_IDX = 0;
    private static final int SERVER_NAME_IDX = 1;

    private static final int SALE_RECORD_METADATA_ROW_NUM = 0;

    private static final int CATEGORY_COLUMN_IDX = 0;
    private static final int TX_TITLE_COLUMN_IDX = 2;
    private static final int TX_AMOUNT_COLUMN_IDX = 3;
    private static final int TX_ENROLL_DATE_COLUMN_IDX = 4;

    private final GameItemSalesHistRepository gameItemSalesHistRepository;

    // 엑셀 등록
    @Transactional
    public void record(MultipartFile file) throws IOException {
        // 시트 가져오기 시작 - 구 엑셀 버전 호환 로직
        Workbook sheets;
        if (file.getOriginalFilename().endsWith(".xls")) {
            sheets = new HSSFWorkbook(file.getInputStream());
        } else {
            sheets = WorkbookFactory.create(file.getInputStream());
        }
        // 시트 가져오기 종료
        Sheet sheet = sheets.getSheetAt(SALE_RECORD_SHEET_IDX);
        List<SaleRecord> pendingSaleRecords = new ArrayList<>();


        for (Row row : sheet) {
            if (row.getRowNum() == SALE_RECORD_METADATA_ROW_NUM) {
                StringBuilder stringBuilder = new StringBuilder();

                row.cellIterator().forEachRemaining(cell -> {
                    stringBuilder.append(cell.getStringCellValue() + " ");
                });
                log.trace("title {}" , stringBuilder);
                continue;
            }

            String gameName = null;
            String gameServerName = null;
            Integer txAmount = null;
            String txTitle = null;
            LocalDateTime enrollDateTime = null;

            for (Cell cell : row) {
                // 카테고리 ROW = 0
                if (CATEGORY_COLUMN_IDX == cell.getColumnIndex()) {
                    String categoryValue = cell.getStringCellValue();
                    String[] categoryElements = categoryValue.split(">");
                    gameName = categoryElements[GAME_NAME_IDX].strip();
                    gameServerName = categoryElements[SERVER_NAME_IDX].strip(); // 공백처리 추가
                    log.trace("Category value is {} {}", gameName, gameServerName);

                }

                if (TX_TITLE_COLUMN_IDX == cell.getColumnIndex()) {
                    txTitle = cell.getStringCellValue();
                }

                if (TX_AMOUNT_COLUMN_IDX == cell.getColumnIndex()) {
                    txAmount = (int) cell.getNumericCellValue();
                }
                if (TX_ENROLL_DATE_COLUMN_IDX == cell.getColumnIndex()) {
                    double excelSerial = cell.getNumericCellValue();

                    // 엑셀 기준 날짜 (1899-12-30)
                    LocalDateTime excelEpoch = LocalDateTime.of(1899, 12, 30, 0, 0, 0);

                    // 정수 부분 (날짜)
                    int days = (int) excelSerial;

                    // 소수 부분 (시간, 분, 초 변환)
                    double fractionalDay = excelSerial - days;
                    long totalNanoSeconds = (long) (fractionalDay * 86400 * 1_000_000_000L); // 하루(86400초) 기준 나노초 변환

                    // 초와 나노초 분리
                    long seconds = totalNanoSeconds / 1_000_000_000L;
                    int nanos = (int) (totalNanoSeconds % 1_000_000_000L);

                    // 최종 변환
                    enrollDateTime = excelEpoch.plusDays(days).plusSeconds(seconds).plusNanos(nanos);
                }
            }

            pendingSaleRecords.add(SaleRecord.builder()
                    .txEnrollDateTime(enrollDateTime)
                    .txTitle(txTitle)
                    .txAmount(txAmount)
                    .userId("admin")
                    .gameServerName(gameServerName)
                    .gameName(gameName)
                    .build());

        }

        try {
            // 저장 전 중복 사전 검사
            List<SaleRecord> pendingSaleRecordsCopy = new ArrayList<>(pendingSaleRecords);
            for (SaleRecord sr : pendingSaleRecordsCopy) {
                for (SaleRecord sr1 : pendingSaleRecordsCopy) {
                    // 자기 자신은 비교 대상에서 제외
                    if (sr.equals(sr1)) {
                        log.trace("자기 자신과의 비교는 제외합니다.");
                        continue;
                    }

                    // 동일하다고 판단 되면, 모두 제거
                    if (sr.applicationEqual(sr1)) {
                        log.info("중복이 감지되어 제거합니다. {}", sr1);
                        pendingSaleRecords.remove(sr1);
                    }
                }
            }

            gameItemSalesHistRepository.saveAll(pendingSaleRecords);
        }
        // 제약 조건 위배 - 게임 명, 서버 명, 거래 등록일, 사용자 ID 멀티 인덱스 중복 X 조건 위배 확률
        catch (DataIntegrityViolationException exception) {
            log.error("입력이 완료된 거래 내역이 엑셀에 포함되어 있습니다", exception);
        }
    }
}
