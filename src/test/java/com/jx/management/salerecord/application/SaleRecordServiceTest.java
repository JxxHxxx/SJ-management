package com.jx.management.salerecord.application;

import com.jx.management.salerecord.domain.SaleRecord;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

class SaleRecordServiceTest {

    private static final Logger log = LoggerFactory.getLogger(SaleRecordServiceTest.class);

    @Test
    void test1() {
        HashSet<String> words = new HashSet<>();

        boolean added1 = words.add("apple");
        boolean added2 = words.add("apple");

        System.out.println("added1" + added1);
        System.out.println("added2" + added2);
    }

    @Test
    void equals() {
        LocalDateTime now = LocalDateTime.now();
        SaleRecord saleRecord1 = SaleRecord.builder()
                .gameName("로드나인")
                .gameServerName("라엘02")
                .txAmount(50000)
                .txTitle("10000 다이아")
                .userId("admin")
                .txEnrollDateTime(now)
                .build();

        SaleRecord saleRecord2 = SaleRecord.builder()
                .gameName("로드나인")
                .gameServerName("라엘02")
                .txAmount(50000)
                .txTitle("10000 다이아")
                .userId("admin")
                .txEnrollDateTime(now)
                .build();
        saleRecord2.changeDelete(true);

        SaleRecord saleRecord3 = SaleRecord.builder()
                .gameName("로드나인")
                .gameServerName("라엘05")
                .txAmount(500000)
                .txTitle("100000 다이아")
                .userId("admin")
                .txEnrollDateTime(now)
                .build();

        Set<Object> saleRecords = new HashSet<>();
        boolean added1 = saleRecords.add(saleRecord1);
        System.out.println(added1);
        boolean added2 = saleRecords.add(saleRecord3);
        System.out.println(added2);

        assertThat(saleRecord2.equals(saleRecord1)).isTrue();
        assertThat(saleRecords.remove(saleRecord2)).isTrue();
    }

    @Test
    void yearMonthTest() {
        YearMonth yearMonth = YearMonth.of(2024, 1);
        log.info("yearMonth.toString() {}", yearMonth.toString());
    }
}