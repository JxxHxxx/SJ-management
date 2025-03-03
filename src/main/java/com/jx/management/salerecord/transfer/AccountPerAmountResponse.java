package com.jx.management.salerecord.transfer;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class AccountPerAmountResponse {
    private final String gameName;
    private List<AccountPerMonthlyAmount> AccountPerMonthlyAmounts;

    public void setAccountPerMonthlyAmounts(List<AccountPerMonthlyAmount> accountPerMonthlyAmounts) {
        AccountPerMonthlyAmounts = accountPerMonthlyAmounts;
    }
}
