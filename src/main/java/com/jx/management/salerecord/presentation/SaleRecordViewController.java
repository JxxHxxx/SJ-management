package com.jx.management.salerecord.presentation;

import com.jx.management.common.endpoint.EndPointDto;
import com.jx.management.salerecord.transfer.AccountPerAmountResponse;
import com.jx.management.salerecord.transfer.AnnualSaleRecordStatTransfer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
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

    @GetMapping("/saleRecord/statMain.do")
    public String main(Model model,
                       @RequestParam(value = "year", defaultValue = "0") Integer year,
                       @RequestParam(value = "mys", defaultValue = "18") Integer mys,
                       @RequestParam(value = "userId", defaultValue = "admin") String userId,
                       @RequestParam(value = "apaYear", defaultValue = "2025") Integer apaYear ) {
        requestGetAnnualSaleRecordStatistics(model, year);
        requestGetMonthlySaleRecordStatistics(model, mys);

        List<AccountPerAmountResponse> accountPerAmounts = apiController.getAccountPerAmount(userId, apaYear).getBody().getBody();
        model.addAttribute("accountPerAmounts", accountPerAmounts);

        return "salerecord/statMain";
    }

    @GetMapping("/saleRecord/upload.do")
    public String uploadSaleRecord() {
        return "upload/recordMain";
    }

    @GetMapping("/saleRecord/annualStat.do")
    public String showSaleRecord(Model model, @RequestParam(value = "year", defaultValue = "0") Integer year) {
        requestGetAnnualSaleRecordStatistics(model, year);
        return "salerecord/annualStat";
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
