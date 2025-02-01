package com.jx.management.salerecord.presentation;

import com.jx.management.common.endpoint.EndPointDto;
import com.jx.management.salerecord.domain.SaleRecordServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackages = {"com.jx.management.salerecord"})
public class SaleRecordApiExceptionHandler {

    @ExceptionHandler(SaleRecordServiceException.class)
    public ResponseEntity<EndPointDto> handleException(SaleRecordServiceException ex) {

        return ResponseEntity.badRequest().body(new EndPointDto(ex.getResponseCode().name(), ex.getMessage()));
    }
}
