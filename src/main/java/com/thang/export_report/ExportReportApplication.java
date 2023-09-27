package com.thang.export_report;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ExportReportApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExportReportApplication.class, args);
	}
}
