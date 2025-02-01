package com.jx.management.salerecord.presentation;

import com.jx.management.common.endpoint.EndPointDto;
import com.jx.management.common.endpoint.ResponseCode;
import com.jx.management.salerecord.application.SaleRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<EndPointDto<String>> record(@RequestParam("file") MultipartFile file) throws IOException {
        String result = saleRecordService.record(file);
        return ResponseEntity.ok(new EndPointDto<String>(ResponseCode.S_0001.name(), result));
    }
}
