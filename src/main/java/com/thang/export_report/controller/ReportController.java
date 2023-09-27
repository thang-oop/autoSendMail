package com.thang.export_report.controller;

import com.thang.export_report.entity.ReportInformation;
import com.thang.export_report.service.ReportServiceTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class ReportController {

    @Autowired
    ReportServiceTask reportService;

    @GetMapping("/callByRice")
    public List<ReportInformation> callByRice(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate) throws IOException {
        return reportService.callByRice(startDate, endDate);
    }

}
