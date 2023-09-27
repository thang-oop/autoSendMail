package com.thang.export_report.repository;

import com.thang.export_report.entity.ReportInformation;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReportMapper implements RowMapper<ReportInformation> {

    @Override
    public ReportInformation mapRow(ResultSet rs, int rowNum) throws SQLException {
        return ReportInformation.builder()
                .agent(rs.getString("agent"))
                .account_number(rs.getString("account_number"))
                .card_mask(rs.getString("card_mask"))
                .cardholder_name(rs.getString("cardholder_name"))
                .postingDate(rs.getString("posting_date"))
                .operatorTypeDesc(rs.getString("oper_type_desc"))
                .creditAmount(rs.getString("credit_amount"))
                .debitAmount(rs.getString("debit_amount"))
                .operatorCurrency(rs.getString("oper_currency"))
                .operatorId(rs.getString("oper_id"))
                .fE_UTRNNO(rs.getString("FE_UTRNNO"))
                .trace(rs.getString("trace"))
                .build();
    }
}
