package com.jx.management.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GameItemSalesHist {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GAME_ITEM_SALES_HIST_NO")
    @Comment("게임 아이템 판매 내역 PK")
    private Long gameItemSalesHistNo;
    @Column(name = "GAME_NAME")
    @Comment("게임 명")
    private String gameName;
    @Column(name = "GAME_SERVER_NAME")
    @Comment("서버 명")
    private String gameServerName;
    @Column(name = "TX_TITLE")
    @Comment("거래 제목")
    private String txTitle;
    @Column(name = "TX_ENROLL_DATE_TIME")
    @Comment("거래 등록일")
    private LocalDateTime txEnrollDateTime;
    @Column(name = "TX_AMOUNT")
    @Comment("판매 금액")
    private Integer txAmount;
    @Column(name = "USER_ID")
    @Comment("사용자 ID")
    private String userId;
    @Column(name = "CRATE_DATE_TIME")
    @Comment("엔티티 생성 시간")
    private LocalDateTime createDateTime;
    @Column(name = "DELETED")
    @Comment("삭제 여부")
    private boolean deleted;

}
