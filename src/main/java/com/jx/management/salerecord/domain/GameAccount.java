package com.jx.management.salerecord.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = {"gameAccountNo"})
@Table(name = "GAME_ACCOUNT")
public class GameAccount {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "GAME_ACCOUNT_UUID")
    @Comment("게임 계정 UUID(PK)")
    private String gameAccountUUID;
    @Column(name = "GAME_ACCOUNT_NAME")
    @Comment("게임 계정 명(UI 편의 용)")
    private String gameAccountName;
    @Column(name = "GAME_CODE")
    @Comment("게임 코드")
    private String gameCode;
    @Comment("게임 명")
    @Column(name = "GAME_NAME")
    private String gameName;
    @Comment("게임 서버 명")
    @Column(name = "SERVER_NAME")
    private String serverName;
    @Column(name = "FIRST_SALE_DATE")
    @Comment("수익 창출 시작 일자")
    private LocalDate firstSaleDate;
    @Column(name = "USED")
    @Comment("계정 사용 여부")
    private boolean used;
    @Column(name = "CREATE_DATE_TIME")
    @Comment("레코드 생성일")
    private LocalDateTime createDateTime;
    @Column(name = "LAST_UPDATE_TIME")
    @Comment("계정 정보 마지막 수정 시간")
    private LocalDateTime lastUpdateTime;
    @Column(name = "USER_ID")
    @Comment("사용자 ID")
    private String userId;

    @Builder
    public GameAccount(String gameAccountName, String gameCode, String gameName, String serverName, LocalDate firstSaleDate,
                       boolean used, LocalDateTime createDateTime, String userId) {
        this.gameAccountName = gameAccountName;
        this.gameCode = gameCode;
        this.gameName = gameName;
        this.serverName = serverName;
        this.firstSaleDate = firstSaleDate;
        this.used = used;
        this.createDateTime = createDateTime;
        this.userId = userId;
    }

    public void changeUsed(boolean used) {
        this.used = used;
        this.lastUpdateTime = this.used ? null : LocalDateTime.now();
    }
}
