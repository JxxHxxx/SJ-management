package com.jx.management.common.application;

import java.time.LocalDateTime;

public class JxSheetUtils {

    /** xls, csv 사용자 지정 타입(날짜)를 LocalDateTime 으로 변환하는 메서드
     * @Param numericValue : 엑셀 파일에서 불러온 사용자 지정 타입(날짜)
     * **/
    public static LocalDateTime format(double numericValue) {
        // 엑셀 기준 날짜 (1899-12-30)
        LocalDateTime excelEpoch = LocalDateTime.of(1899, 12, 30, 0, 0, 0);
        // 정수 부분 (날짜)
        int days = (int) numericValue;

        // 소수 부분 (시간, 분, 초 변환)
        double fractionalDay = numericValue - days;
        long totalNanoSeconds = (long) (fractionalDay * 86400 * 1_000_000_000L); // 하루(86400초) 기준 나노초 변환

        // 초 분리
        long seconds = totalNanoSeconds / 1_000_000_000L;
        // 나노 초는 객체와 DB 레코드 사이 datetime 자리 수 한계로 인한 오차 존재로 동등성 검증 실패 하게 됨
        int nanos = (int) (totalNanoSeconds % 1_000_000_000L);
        // 최종 변환
        return excelEpoch.plusDays(days).plusSeconds(seconds);
    }
}
