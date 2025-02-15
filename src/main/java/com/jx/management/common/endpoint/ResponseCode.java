package com.jx.management.common.endpoint;

import lombok.Getter;

@Getter
public enum ResponseCode {

    S_0001("success"),

    // sale record domain fail code start
    F_SR01("Incorrect meta row format"),
    F_SR02("some sale record already persist, throw error"),
    F_SR03("unsupported file type"),
    // sale record domain fail code end

    F_SR11("invalid params"),
    F_SR99("system error, contact system administrator"),;


    private final String description;

    ResponseCode(String description) {
        this.description = description;
    };
}

