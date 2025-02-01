package com.jx.management.salerecord.presentation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class SaleRecordViewController {

    @GetMapping("/record.do")
    public String showSaleRecord() {
        log.info("call showSaleRecord");
        return "salerecord/record";
    }
}
