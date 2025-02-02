package com.jx.management.salerecord.presentation;

import com.jx.management.salerecord.transfer.AnnualSaleRecordStatTransfer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class SaleRecordViewController {

    private final SaleRecordApiController apiController;

    @GetMapping("/saleRecord/main.do")
    public String main(Model model, @RequestParam(value = "year") Integer year) {
        List<AnnualSaleRecordStatTransfer> result = apiController.getAnnualSaleRecordStatistics(year).getBody().getBody();
        model.addAttribute("result", result);
        model.addAttribute("year", year);

        return "salerecord/main";
    }

    @GetMapping("/saleRecord/upload.do")
    public String uploadSaleRecord() {
        return "salerecord/record";
    }

    @GetMapping("/saleRecord/showStatistics.do")
    public String showSaleRecord(Model model , @RequestParam(value = "year", defaultValue = "0") Integer year) {
        List<AnnualSaleRecordStatTransfer> result = apiController.getAnnualSaleRecordStatistics(year).getBody().getBody();
        model.addAttribute("result", result);
        model.addAttribute("year", year);
        return "salerecord/showStatistics";
    }
}
