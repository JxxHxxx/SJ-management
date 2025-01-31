package com.jx.management.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GameItemSalesHistRepository extends JpaRepository<SaleRecord, Long> {
}
