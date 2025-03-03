package com.jx.management.salerecord.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameAccountRepository extends JpaRepository<GameAccount, String> {

    List<GameAccount> findByUserId(String userId);

}
