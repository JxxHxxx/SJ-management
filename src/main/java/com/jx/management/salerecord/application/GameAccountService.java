package com.jx.management.salerecord.application;

import com.jx.management.common.endpoint.ResponseCode;
import com.jx.management.salerecord.domain.GameAccount;
import com.jx.management.salerecord.domain.GameAccountRepository;
import com.jx.management.salerecord.domain.SaleRecordServiceException;
import com.jx.management.salerecord.transfer.GameAccountCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class GameAccountService {

    private static final String GAME_ACCOUNT_NAME_DEFAULT_VALUE = "anonymous";

    private final GameAccountRepository gameAccountRepository;

    @Transactional
    public void create(GameAccountCreateDto dto) {
        // 검증 및 기본 값 설정
        String gameAccountName = dto.gameAccountName();
        if (!StringUtils.hasText(gameAccountName)) {
            gameAccountName = GAME_ACCOUNT_NAME_DEFAULT_VALUE;
        }

        LocalDate firstSaleDate = dto.firstSaleDate();
        if (Objects.isNull(firstSaleDate)) {
            firstSaleDate = LocalDate.now();
        }

        GameAccount newGameAccount = GameAccount.builder()
                .used(true)
                .createDateTime(LocalDateTime.now())
                .gameAccountName(gameAccountName)
                .gameCode(dto.gameCode())
                .gameName(dto.gameName())
                .serverName(dto.serverName())
                .userId(dto.userId())
                .firstSaleDate(firstSaleDate)
                .build();

        gameAccountRepository.save(newGameAccount);
    }

    public List<GameAccountSelectDto> selectGameAccounts(String userId) {
        List<GameAccount> gameAccounts = gameAccountRepository.findByUserId(userId);

        return gameAccounts.stream()
                .map(ga -> new GameAccountSelectDto(
                        ga.getGameAccountUUID(),
                        ga.getLastUpdateTime(),
                        ga.getCreateDateTime().toLocalDate(),
                        ga.getFirstSaleDate(),
                        ga.getGameAccountName(),
                        ga.getGameCode(),
                        ga.getGameName(),
                        ga.getServerName(),
                        ga.isUsed(),
                        ga.getUserId()))
                .toList();
    }

    @Transactional
    public void changeUsed(String gameAccountUUID, boolean changeUsed) {
        GameAccount gameAccount = gameAccountRepository.findById(gameAccountUUID)
                .orElseThrow(() -> new SaleRecordServiceException(ResponseCode.F_SR11));
        gameAccount.changeUsed(changeUsed);
    }
}
