package com.jx.management.salerecord.presentation;

import com.jx.management.common.endpoint.EndPointDto;
import com.jx.management.common.endpoint.ResponseCode;
import com.jx.management.salerecord.application.SaleRecordService;
import com.jx.management.salerecord.transfer.AnnualSaleRecordStatTransfer;
import com.jx.management.salerecord.transfer.MonthlySaleRecordStatTransfer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SaleRecordApiController {

    private final SaleRecordService saleRecordService;

    @GetMapping("/api/sale-records")
    public ResponseEntity<EndPointDto<List<AnnualSaleRecordStatTransfer>>> getAnnualSaleRecordStatistics(
            @RequestParam(value = "year",  defaultValue = "0") Integer year) {
        log.info("request getAnnualSaleRecordStatistics()");
        List<AnnualSaleRecordStatTransfer> result = saleRecordService.getAnnualSaleRecordStatistics(year);
        return ResponseEntity.ok(new EndPointDto<>(ResponseCode.S_0001.name(), result));
    }

    @GetMapping("/api/sale-records/monthly")
    public ResponseEntity<EndPointDto<List<MonthlySaleRecordStatTransfer>>> getMonthlySaleRecordStatistics(
            @RequestParam(value = "mys",  defaultValue = "6") Integer mys) {
        log.info("request getMonthlySaleRecordStatistics()");
        List<MonthlySaleRecordStatTransfer> result = saleRecordService.getMonthlySaleRecordStatistics(mys);
        return ResponseEntity.ok(new EndPointDto<>(ResponseCode.S_0001.name(), result));
    }

    @PostMapping("/api/sale-records")
    public ResponseEntity<EndPointDto<String>> record(@RequestParam("file") MultipartFile file) throws IOException {
        log.info("request record()");
        String result = saleRecordService.record(file);
        return ResponseEntity.status(CREATED.value()).body(new EndPointDto<>(ResponseCode.S_0001.name(), result));
    }
}
