package com.jx.management.salerecord.domain;

import com.jx.management.salerecord.transfer.AnnualSaleRecordStatTransfer;
import com.jx.management.salerecord.transfer.MonthlySaleRecordStatTransfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SaleRecordRepository extends JpaRepository<SaleRecord, Long> {

    /** 게임당 년간 판매 금액, 건 조회 **/
    @Query(value = "SELECT " +
            "GAME_NAME AS gameName, " +
            "SUM(TX_AMOUNT) AS amountSum, " +
            "COUNT(TX_AMOUNT) AS transactionCount FROM SALERECORD SR " +
            "WHERE SUBSTRING(TX_ENROLL_DATE_TIME, 1, 4) = :year " +
            "GROUP BY GAME_NAME , SUBSTRING(TX_ENROLL_DATE_TIME, 1, 4) " +
            "ORDER BY SUM(TX_AMOUNT) DESC", nativeQuery = true)
    List<AnnualSaleRecordStatTransfer> getAnnualSaleRecordStatistics(int year);

    /** 월간 총 게임의 판매 금액 **/
    @Query(value = "SELECT " +
            "SUM(TX_AMOUNT) AS amountSum, " +
            "DATE_FORMAT(TX_ENROLL_DATE_TIME,'%Y-%m') AS yearMonth " +
            "FROM SALERECORD SR " +
            "WHERE TX_ENROLL_DATE_TIME > :yearMonth " +
            "GROUP BY  DATE_FORMAT(TX_ENROLL_DATE_TIME,'%Y-%m') " +
            "ORDER BY DATE_FORMAT(TX_ENROLL_DATE_TIME,'%Y-%m') DESC ", nativeQuery = true)
    List<MonthlySaleRecordStatTransfer> getMonthlySaleRecordStatistics(String yearMonth);

    List<SaleRecord> findByUserId(String userId);
}
