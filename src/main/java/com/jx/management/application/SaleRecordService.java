package com.jx.management.application;

import com.jx.management.domain.GameItemSalesHist;
import com.jx.management.domain.GameItemSalesHistRepository;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SaleRecordService {

    private final GameItemSalesHistRepository gameItemSalesHistRepository;

    private static final int GAME_NAME_IDX = 0;
    private static final int SERVER_NAME_IDX = 1;


    // 엑셀 등록
    public void record(MultipartFile file) throws IOException {
        Workbook sheets;
        if (file.getOriginalFilename().endsWith(".xls")) {
            sheets = new HSSFWorkbook(file.getInputStream());
        } else {
            sheets = WorkbookFactory.create(file.getInputStream());
        }
        ;

        Sheet sheet = sheets.getSheetAt(0);
        List<GameItemSalesHist> gameItemSalesHistories = new ArrayList<>();

        for (Row row : sheet) {
            if (row.getRowNum() == 0) {
                StringBuilder stringBuilder = new StringBuilder();

                row.cellIterator().forEachRemaining(cell -> {
                    stringBuilder.append(cell.getStringCellValue() + " ");
                });
                log.info("title {}" , stringBuilder);
                continue;
            }

            String gameName = null;
            String gameServerName = null;
            Integer txAmount = null;
            String txTitle = null;
            LocalDateTime enrollDateTime = null;

            for (Cell cell : row) {
                // 카테고리 ROW = 0
                int columnIndex = cell.getColumnIndex();

                if (columnIndex == 0) {
                    String categoryValue = cell.getStringCellValue();
                    String[] categoryElements = categoryValue.split(">");
                    gameName = categoryElements[GAME_NAME_IDX].strip();
                    gameServerName = categoryElements[SERVER_NAME_IDX].strip(); // 공백처리 추가
                    log.debug("Category value is {} {}", gameName, gameServerName);

                }

                if (columnIndex == 2) {
                    txTitle = cell.getStringCellValue();
                    ;
                }

                if (columnIndex == 3) {
                    txAmount = (int) cell.getNumericCellValue();
                }
                if (columnIndex == 4) {
                    double excelSerial = cell.getNumericCellValue();
                    LocalDateTime excelEpoch = LocalDateTime.of(1899, 12, 30, 0, 0, 0);

                    // 정수 부분 = 날짜 변환
                    int days = (int) excelSerial;

                    // 소수 부분 = 하루 기준으로 시간 변환
                    double fractionalDay = excelSerial - days;
                    long secondsInDay = (long) (fractionalDay * 86400); // 24 * 60 * 60 = 86400초

                    // 최종 변환
                    enrollDateTime = excelEpoch.plusDays(days).plusSeconds(secondsInDay);
                }
            }

            GameItemSalesHist gameItemSalesHist = GameItemSalesHist.builder()
                    .txEnrollDateTime(enrollDateTime)
                    .txTitle(txTitle)
                    .txAmount(txAmount)
                    .userId("admin")
                    .gameServerName(gameServerName)
                    .gameName(gameName)
                    .build();

            gameItemSalesHistories.add(gameItemSalesHist);

        }
        for (GameItemSalesHist gameItemSalesHistory : gameItemSalesHistories) {
            log.debug(" {}", gameItemSalesHistory.toString());
        }

        try {
            gameItemSalesHistRepository.saveAll(gameItemSalesHistories);
        }
        // 제약 조건 위배 - 게임 명, 서버 명, 거래 등록일, 사용자 ID 멀티 인덱스 중복 X 조건 위배 확률
        catch (DataIntegrityViolationException exception) {
            log.error("입력이 완료된 거래 내역이 엑셀에 포함되어 있습니다", exception);
        }
    }
}
