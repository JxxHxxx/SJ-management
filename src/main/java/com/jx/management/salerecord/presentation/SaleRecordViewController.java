package com.jx.management.salerecord.presentation;

import com.jx.management.common.endpoint.EndPointDto;
import com.jx.management.salerecord.transfer.AnnualSaleRecordStatTransfer;
import com.jx.management.salerecord.transfer.MonthlySaleRecordStatTransfer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.SortedMap;

@Slf4j
@Controller
@RequiredArgsConstructor
public class SaleRecordViewController {

    @Value("${search.default.year}")
    private Integer searchDefaultYear;

    private final SaleRecordApiController apiController;

    @GetMapping("/saleRecord/main.do")
    public String main(Model model,
                       @RequestParam(value = "year", defaultValue = "0") Integer year,
                       @RequestParam(value = "mys", defaultValue = "6") Integer mys) {
        requestGetAnnualSaleRecordStatistics(model, year);
        requestGetMonthlySaleRecordStatistics(model, mys);
        return "salerecord/main";
    }

    @GetMapping("/saleRecord/upload.do")
    public String uploadSaleRecord() {
        return "salerecord/record";
    }

    @GetMapping("/saleRecord/annualStat.do")
    public String showSaleRecord(Model model, @RequestParam(value = "year", defaultValue = "0") Integer year) {
        requestGetAnnualSaleRecordStatistics(model, year);
        return "salerecord/annualStat";
    }

    @GetMapping("/saleRecord/monthlyStat.do")
    public String showMonthlyStat(Model model, @RequestParam(value = "mys", defaultValue = "6") Integer mys) {
        requestGetMonthlySaleRecordStatistics(model, mys);
        return "salerecord/monthlyStat";
    }

    private void requestGetAnnualSaleRecordStatistics(Model model, Integer year) {
        if (year == 0) {
            year = searchDefaultYear;
        }
        List<AnnualSaleRecordStatTransfer> result = apiController.getAnnualSaleRecordStatistics(year).getBody().getBody();
        model.addAttribute("result", result);
        model.addAttribute("year", year);
    }

    private void requestGetMonthlySaleRecordStatistics(Model model, Integer mys) {
        SortedMap<String, Integer> result = apiController.getMonthlySaleRecordStatistics(mys).getBody().getBody();
        model.addAttribute("monthlySaleRecordStat", result);
        model.addAttribute("mys", mys);
    }
}
