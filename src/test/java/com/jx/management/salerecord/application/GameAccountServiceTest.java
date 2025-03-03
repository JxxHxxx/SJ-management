package com.jx.management.salerecord.application;

import org.junit.jupiter.api.Test;
import org.springframework.util.StringUtils;

import static org.assertj.core.api.Assertions.*;


class GameAccountServiceTest {

    @Test
    void hasText() {

        boolean whiteSpace1 = StringUtils.hasText(" ");
        boolean whiteSpace2 = StringUtils.hasText("");
        boolean nullValue = StringUtils.hasText(null);

        assertThat(whiteSpace1).isFalse();
        assertThat(whiteSpace2).isFalse();
        assertThat(nullValue).isFalse();
    }

}