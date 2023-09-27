package com.thang.export_report.excel;

import com.thang.export_report.entity.ReportInformation;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import javax.mail.util.ByteArrayDataSource;

public class ExcelGenerator {
    private List<ReportInformation> reportInformationList;
    private String date;

    public ExcelGenerator(List <ReportInformation> reportInformationList, String date) {
        this.reportInformationList = reportInformationList;
        this.date = date;
    }

    public ByteArrayDataSource exportDataExcel() throws IOException{

        SXSSFWorkbook wb = new SXSSFWorkbook();

        Sheet sheet = wb.createSheet();

        CellStyle titleCellStyle = createTitleCellStyle(wb);
        CellStyle headCellStyle = createHeadCellStyle(wb);
        CellStyle cellStyle = createCellStyle(wb);

        int rowNum = 0;
        Row row0 = sheet.createRow(rowNum++);
        row0.setHeight((short) 800);

        String title = "CREDIT CARD PAYMENT REPORT";
        Cell c00 = row0.createCell(0);
        c00.setCellValue(title);
        c00.setCellStyle(titleCellStyle);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 11));

        Row row2 = sheet.createRow(rowNum++);
        row2.setHeight((short) 700);
        String[] row_second = {"Agent", "Account_number", "Card_mask", "Cardholder_name", "Posting_date", "Oper_type_desc", "Credit_amount", "Debit_amount", "Oper_currency", "Oper_id", "Fe_utrnno", "Trace"};
        for (int i = 0; i < row_second.length; i++) {
            Cell tempCell = row2.createCell(i);
            tempCell.setCellValue(row_second[i]);
            tempCell.setCellStyle(headCellStyle);
        }

        for (ReportInformation reportInformation : reportInformationList) {
            Row tempRow = sheet.createRow(rowNum++);
            tempRow.setHeight((short) 500);
            for (int i = 0; i < row_second.length; i++) {
                Cell tempCell = tempRow.createCell(i);
                tempCell.setCellStyle(cellStyle);
                String tempValue;
                if (i == 0)
                    tempValue = reportInformation.getAgent();
                else if (i == 1)
                    tempValue = reportInformation.getAccount_number();
                else if (i == 2)
                    tempValue = reportInformation.getCard_mask();
                else if (i == 3)
                    tempValue = reportInformation.getCardholder_name();
                else if (i == 4)
                    tempValue = reportInformation.getPostingDate();
                else if (i == 5)
                    tempValue = reportInformation.getOperatorTypeDesc();
                else if (i == 6)
                    tempValue = reportInformation.getCreditAmount();
                else if (i == 7)
                    tempValue = reportInformation.getDebitAmount();
                else if (i == 8)
                    tempValue = reportInformation.getOperatorCurrency();
                else if (i == 9)
                    tempValue = reportInformation.getOperatorId();
                else if (i == 10)
                    tempValue = reportInformation.getFE_UTRNNO();
                else
                    tempValue = reportInformation.getTrace();
                tempCell.setCellValue(tempValue);
            }
        }

        sheet.setColumnWidth(0, 10000);
        sheet.setColumnWidth(1, 7000);
        sheet.setColumnWidth(2, 7000);
        sheet.setColumnWidth(3, 7000);
        sheet.setColumnWidth(4, 7000);
        sheet.setColumnWidth(5, 10000);
        sheet.setColumnWidth(6, 4000);
        sheet.setColumnWidth(7, 4000);
        sheet.setColumnWidth(8, 4000);
        sheet.setColumnWidth(9, 7000);
        sheet.setColumnWidth(10, 4000);
        sheet.setColumnWidth(11, 4000);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        wb.write(bos);
        ByteArrayDataSource fds = new ByteArrayDataSource(bos.toByteArray(), "application/vnd.ms-excel");
        fds.setName(date);

        return fds;
    }

    private static CellStyle createTitleCellStyle(Workbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        return cellStyle;
    }

    private static CellStyle createCellStyle(Workbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);

        return cellStyle;
    }

    private static CellStyle createHeadCellStyle(Workbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        return cellStyle;
    }
}
