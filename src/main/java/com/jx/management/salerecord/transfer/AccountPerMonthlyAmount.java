package com.jx.management.salerecord.transfer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
public class AccountPerMonthlyAmount {
    private final String yearMonth;
    private Integer accountNumber;
    private Integer accountPerAmount;

    public void setAccountNumberAndPerAmount(Integer accountNumber, Integer accountPerAmount) {
        this.accountNumber = accountNumber;
        this.accountPerAmount = accountPerAmount;
    }
}
