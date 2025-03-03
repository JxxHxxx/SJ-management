package com.jx.management.salerecord.presentation;

import com.jx.management.common.endpoint.EndPointDto;
import com.jx.management.salerecord.application.GameAccountSelectDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class GameAccountViewController {

    private final GameAccountApiController gameAccountApiController;

    @GetMapping("/gameAccount/gaMain.do")
    public String gaMain(Model model, @RequestParam(value = "userId", defaultValue = "admin") String userId) {
        EndPointDto<List<GameAccountSelectDto>> endPointResponse = gameAccountApiController.selectGameAccounts(userId).getBody();
        model.addAttribute("gameAccounts", endPointResponse.getBody());
        System.out.println("test");
        return "account/gaMain";
    }

}
