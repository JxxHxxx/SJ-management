package com.jx.management.salerecord.application;

import org.junit.jupiter.api.Test;

import java.util.HashSet;

class SaleRecordServiceTest {

    @Test
    void test1() {
        HashSet<String> words = new HashSet<>();

        boolean added1 = words.add("apple");
        boolean added2 = words.add("apple");

        System.out.println("added1" + added1);
        System.out.println("added2" + added2);
    }

}