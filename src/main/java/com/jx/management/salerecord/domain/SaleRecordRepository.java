package com.jx.management.salerecord.domain;

import com.jx.management.salerecord.transfer.AnnualSaleRecordStatTransfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SaleRecordRepository extends JpaRepository<SaleRecord, Long> {

    @Query(value = "SELECT " +
            "GAME_NAME AS gameName, " +
            "SUM(TX_AMOUNT) AS amountSum, " +
            "COUNT(TX_AMOUNT) AS transactionCount FROM SALERECORD SR " +
            "WHERE SUBSTRING(TX_ENROLL_DATE_TIME, 1, 4) = :year " +
            "GROUP BY GAME_NAME , SUBSTRING(TX_ENROLL_DATE_TIME, 1, 4) " +
            "ORDER BY SUM(TX_AMOUNT) DESC", nativeQuery = true)
    List<AnnualSaleRecordStatTransfer> getAnnualSaleRecordStatistics(int year);

}
