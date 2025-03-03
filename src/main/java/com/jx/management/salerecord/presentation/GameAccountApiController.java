package com.jx.management.salerecord.presentation;

import com.jx.management.common.endpoint.EndPointDto;
import com.jx.management.common.endpoint.ResponseCode;
import com.jx.management.salerecord.application.GameAccountSelectDto;
import com.jx.management.salerecord.application.GameAccountService;
import com.jx.management.salerecord.transfer.GameAccountCreateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.jx.management.common.endpoint.ResponseCode.*;
import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class GameAccountApiController {

    private final GameAccountService gameAccountService;

    @PostMapping("/api/game-accounts")
    public ResponseEntity<EndPointDto> create(@RequestBody GameAccountCreateDto gameAccountCreateDto) {
        log.info("request create()");
        gameAccountService.create(gameAccountCreateDto);
        return ResponseEntity.status(CREATED.value()).body(new EndPointDto(S_0001.name(), ""));
    }

    @GetMapping("/api/game-accounts")
    public ResponseEntity<EndPointDto<List<GameAccountSelectDto>>> selectGameAccounts(@RequestParam String userId) {
        log.info("request selectGameAccounts()");
        List<GameAccountSelectDto> results = gameAccountService.selectGameAccounts(userId);
        return ResponseEntity.ok(new EndPointDto(S_0001.name(), results));
    }

    @PatchMapping("/api/game-accounts")
    public ResponseEntity<EndPointDto> changeGameAccountsInfo(@RequestParam String userId) {
//        List<GameAccountSelectDto> results = gameAccountService.changeGameAccountsInfo(userId);
//        return ResponseEntity.ok(new EndPointDto(S_0001.name(), results));
        return null;
    }
}
