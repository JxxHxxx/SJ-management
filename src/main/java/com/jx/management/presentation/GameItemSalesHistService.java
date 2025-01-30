package com.jx.management.presentation;

import com.jx.management.domain.GameItemSalesHistRepository;
import lombok.RequiredArgsConstructor;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class GameItemSalesHistService {

    private final GameItemSalesHistRepository gameItemSalesHistRepository;

    // 엑셀 등록
    public void sst(MultipartFile file) throws IOException {
        Workbook sheets = WorkbookFactory.create(file.getInputStream());
        Sheet sheet = sheets.getSheetAt(0);
    }
    // 조회

}
