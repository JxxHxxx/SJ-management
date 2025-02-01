package com.jx.management.salerecord.domain;

import com.jx.management.common.endpoint.ResponseCode;
import lombok.Getter;

@Getter
public class SaleRecordServiceException extends RuntimeException{

    private final ResponseCode responseCode;

    public SaleRecordServiceException(ResponseCode responseCode) {
        super(responseCode.getDescription());
        this.responseCode = responseCode;
    }
}
