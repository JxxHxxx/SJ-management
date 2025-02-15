package com.jx.management.salerecord.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = {"saleRecordNo"})
public class SaleRecord {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SALE_RECORD_NO")
    @Comment("게임 아이템 판매 내역 PK")
    private Long saleRecordNo;
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


    @Builder
    public SaleRecord(String gameName, String gameServerName, String txTitle,
                      LocalDateTime txEnrollDateTime, Integer txAmount, String userId) {
        this.gameName = gameName;
        this.gameServerName = gameServerName;
        this.txTitle = txTitle;
        this.txEnrollDateTime = txEnrollDateTime;
        this.txAmount = txAmount;
        this.userId = userId;
        this.createDateTime = LocalDateTime.now();
        this.deleted = false;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        SaleRecord that = (SaleRecord) o;
        return Objects.equals(gameName, that.gameName) && Objects.equals(gameServerName, that.gameServerName) &&
                Objects.equals(txTitle, that.txTitle) && Objects.equals(txEnrollDateTime, that.txEnrollDateTime) &&
                Objects.equals(txAmount, that.txAmount) && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameName, gameServerName, txTitle, txEnrollDateTime, txAmount, userId);
    }

    /**
     * 동일한 거래임을 확인하기 위한 메서드 equal() 와 다른 용도이니 주의 바람
     **/
    public boolean applicationEqual(SaleRecord saleRecord) {
        return this.gameName.equals(saleRecord.getGameName()) && this.gameServerName.equals(saleRecord.getGameServerName()) &&
                this.txEnrollDateTime.equals(saleRecord.getTxEnrollDateTime())&& this.txAmount.equals(saleRecord.getTxAmount()) &&
                this.userId.equals(saleRecord.getUserId());

    }

    public void changeDelete(boolean deleted) {
        this.deleted =deleted;
    }
}
