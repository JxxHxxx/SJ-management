package com.jx.management.salerecord.transfer;

import java.time.LocalDate;

public record GameAccountCreateDto(
        String gameAccountName,
        String gameCode,
        String gameName,
        String serverName,
        String userId,
        LocalDate firstSaleDate
) {
}
