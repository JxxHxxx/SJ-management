package com.jx.management.salerecord.application;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class GameAccountSelectDto {
    private final String gameAccountUUID;
    private final LocalDateTime lastUpdateDateTime;
    private final LocalDate createDate;
    private final LocalDate firstSaleDate;
    private final String gameAccountName;
    private final String gameCode;
    private final String gameName;
    private final String serverName;
    private final Boolean used;
    private final String userId;
}

