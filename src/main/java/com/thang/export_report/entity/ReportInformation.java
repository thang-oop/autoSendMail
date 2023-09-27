package com.thang.export_report.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReportInformation {

    @JsonProperty("AGENT")
    private String agent;

    @JsonProperty("ACCOUNT_NUMBER")
    private String account_number;

    @JsonProperty("CARD_MASK")
    private String card_mask;

    @JsonProperty("CARDHOLDER_NAME")
    private String cardholder_name;

    @JsonProperty("POSTING_DATE")
    private String postingDate;

    @JsonProperty("OPER_TYPE_DESC")
    private String operatorTypeDesc;

    @JsonProperty("CREDIT_AMOUNT")
    private String creditAmount;

    @JsonProperty("DEBIT_AMOUNT")
    private String debitAmount;

    @JsonProperty("OPER_CURRENCY")
    private String operatorCurrency;

    @JsonProperty("OPER_ID")
    private String operatorId;

    @JsonProperty("FE_UTRNNO")
    private String fE_UTRNNO;

    @JsonProperty("TRACE")
    private String trace;

}
