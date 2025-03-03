package com.jx.management.salerecord.transfer;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class YearMonthAccountNum {
    private final String yearMonth;
    private final String gameName;
    private Integer accountNum;

    public void addOneAccountNum() {
        accountNum += 1;
    }
}
