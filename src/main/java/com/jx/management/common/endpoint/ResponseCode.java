package com.jx.management.common.endpoint;

import lombok.Getter;

@Getter
public enum ResponseCode {

    S_0001("success"),

    // sale record domain fail code start
    F_SR01("manipulated meta row, transaction rollback"),
    F_SR02("some sale record already persist, throw error");
    // sale record domain fail code end

    private final String description;

    ResponseCode(String description) {
        this.description = description;
    };
}

