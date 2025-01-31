package com.jx.management.salerecord.presentation;

import com.jx.management.salerecord.application.SaleRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class SaleRecordApiController {

    private final SaleRecordService saleRecordService;

    @PostMapping("/api/record")
    public void record(@RequestParam("file") MultipartFile file) throws IOException {
        saleRecordService.record(file);
    }
}
