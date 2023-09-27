package com.thang.export_report.service;


import com.thang.export_report.entity.ReportInformation;
import com.thang.export_report.excel.ExcelGenerator;
import com.thang.export_report.repository.ReportRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReportServiceTask {

    @Autowired
    ReportRepository reportRepository;

    @Autowired
    private MailService emailSender;

    @Value("${email.fromEmailAddress}")
    private String fromEmailAddress;

    @Value("${email.toEmailAddress}")
    private String[] toEmailAddress;

    private static final Logger logger = LoggerFactory.getLogger(ReportServiceTask.class);

    @Scheduled(cron = "0 0 8 * * ?")
    public void scheduleReportTask() throws IOException {

        logger.info("Start report at 8AM everyday");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
        List<ReportInformation> reportInformationList = reportRepository.getListReportInformation(dtf.format(LocalDateTime.now().minusDays(1)),dtf.format(LocalDateTime.now()));

        logger.info("Start create file excel!!");

        ExcelGenerator generator = new ExcelGenerator(reportInformationList, dtf.format(LocalDateTime.now().minusDays(1)));

        logger.info("Done create file excel!!");

        String body = "<h4>Credit card payment report dated:  " + dtf.format(LocalDateTime.now().minusDays(1)) + "</h4>\n" +
                "<h4>Thanks & Best regards!!</h4>";
        emailSender.sendEmailAttachment(dtf.format(LocalDateTime.now().minusDays(1)),  fromEmailAddress, toEmailAddress,  generator.exportDataExcel(), body);
    }

    public List<ReportInformation> callByRice(String startDate, String endDate) throws IOException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
        logger.info("Start report by rice");

        List<ReportInformation> reportInformationList = reportRepository.getListReportInformation(startDate,endDate);
        logger.info("Start create file excel!!");

        ExcelGenerator generator = new ExcelGenerator(reportInformationList, dtf.format(LocalDateTime.now().minusDays(1)));

        logger.info("Done create file excel!!");

        String body = "<h4>Credit card payment report dated:  " + dtf.format(LocalDateTime.now().minusDays(1)) + "</h4>\n" +
                "<h4>Thanks & Best regards!!</h4>";

        emailSender.sendEmailAttachment(dtf.format(LocalDateTime.now().minusDays(1)),  fromEmailAddress, toEmailAddress,  generator.exportDataExcel(), body);

        return reportInformationList;
    }
}
