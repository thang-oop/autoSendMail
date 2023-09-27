package com.thang.export_report.repository;


import com.thang.export_report.entity.ReportInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.List;

@Repository
public class ReportRepository {

    @Autowired
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    private void postConstruct() {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<ReportInformation> getListReportInformation(String startDate, String endDate) {
        String sql = "select agent_number || ' - ' || get_text('ost_agent','name', agent_id) as agent\n" +
                "                 , account_number\n" +
                "                 , iss_api_card_pkg.get_card_mask(\n" +
                "                       iss_api_card_pkg.get_card_number(\n" +
                "                                            card_id\n" +
                "                                        )\n" +
                "                   ) as card_mask\n" +
                "                 , iss_api_cardholder_pkg.get_cardholder_name(\n" +
                "                       iss_api_cardholder_pkg.get_cardholder_by_card(\n" +
                "                                   card_id\n" +
                "                               )\n" +
                "                   ) as cardholder_name\n" +
                "                 , posting_date\n" +
                "                 , oper_type || '-' || com_api_dictionary_pkg.get_article_text(oper_type) as oper_type_desc\n" +
                "                 , case when oper_type in ('OPTP0026'\n" +
                "                                         , 'OPTP0028'\n" +
                "                                         , 'OPTP0422'\n" +
                "                                         , 'OPTP0043'\n" +
                "                                         )\n" +
                "                        then oper_amount\n" +
                "                        else 0\n" +
                "                   end as credit_amount\n" +
                "                 , case when oper_type in ('OPTP0000'\n" +
                "                                         , 'OPTP0001'\n" +
                "                                         , 'OPTP0402'\n" +
                "                                         )\n" +
                "                        then oper_amount\n" +
                "                        else 0\n" +
                "                   end as debit_amount\n" +
                "                 , oper_currency\n" +
                "                 , oper_id\n" +
                "         , fe_utrnno\n" +
                "                 , TRACE\n" +
                "              from (select o.host_date\n" +
                "                         , o.oper_amount\n" +
                "                         , o.oper_currency\n" +
                "                         , o.oper_type\n" +
                "                         , o.id as oper_id\n" +
                "                         , g.id as agent_id\n" +
                "                         , g.agent_number\n" +
                "                         , a.account_number\n" +
                "                         , cst_pvc_com_pkg.get_main_card_id(\n" +
                "                               a.id\n" +
                "                             , a.split_hash\n" +
                "                           ) as card_id\n" +
                "                         , m.posting_date\n" +
                "             , au.EXTERNAL_AUTH_ID fe_utrnno\n" +
                "                         , au.SYSTEM_TRACE_AUDIT_NUMBER TRACE\n" +
                "                      from opr_operation o\n" +
                "                         , opr_participant p\n" +
                "                         , acc_account a\n" +
                "                         , ost_agent g\n" +
                "             , aut_auth au\n" +
                "                         , (select min(posting_date) as posting_date\n" +
                "                                 , object_id as oper_id\n" +
                "                              from acc_macros\n" +
                "                             where entity_type = 'ENTTOPER'\n" +
                "                             group by object_id\n" +
                "                           ) m\n" +
                "                     where o.id = p.oper_id\n" +
                "                       and o.id = m.oper_id\n" +
                "                       and a.id = p.account_id\n" +
                "                       and g.id = a.agent_id\n" +
                "             and o.id  = au.id (+)\n" +
                "                       and p.participant_type = 'PRTYISS'\n" +
                "                       and a.account_type in ('ACTP0130'\n" +
                "                                            , 'ACTP9661'\n" +
                "                                            )\n" +
                "                       and o.sttl_type in ('STTT0010'\n" +
                "                                         , 'STTT0000'\n" +
                "                                         , 'STTT0007'\n" +
                "                                         )\n" +
                "                       and o.oper_currency = '704'\n" +
                "                       and o.status = 'OPST0400'\n" +
                "                       and o.oper_type in ('OPTP0026'\n" +
                "                                         , 'OPTP0028'\n" +
                "                                         , 'OPTP0422'\n" +
                "                                         , 'OPTP0402'\n" +
                "                                         , 'OPTP0043'\n" +
                "                                         )\n" +
                "                       and p.client_id_type = 'CITPACCT'\n" +
                "                       and to_char(m.posting_date,'yyyymmdd') between " + startDate + " and " + endDate +"\n" +
                "                    union\n" +
                "                    select o.host_date\n" +
                "                         , o.oper_amount\n" +
                "                         , o.oper_currency\n" +
                "                         , o.oper_type\n" +
                "                         , o.id as oper_id\n" +
                "                         , g.id as agent_id\n" +
                "                         , g.agent_number\n" +
                "                         , a.account_number\n" +
                "                         , n.card_id\n" +
                "                         , m.posting_date\n" +
                "             , au.EXTERNAL_AUTH_ID fe_utrnno\n" +
                "                         , au.SYSTEM_TRACE_AUDIT_NUMBER TRACE\n" +
                "                      from opr_operation o\n" +
                "                         , opr_participant p\n" +
                "                         , opr_card c\n" +
                "                         , iss_card_number n\n" +
                "                         , acc_account a\n" +
                "                         , acc_account_object b\n" +
                "                         , ost_agent g\n" +
                "             , aut_auth au\n" +
                "                         , (select min(posting_date) as posting_date\n" +
                "                                 , object_id as oper_id\n" +
                "                              from acc_macros\n" +
                "                             where entity_type = 'ENTTOPER'\n" +
                "                             group by object_id\n" +
                "                           ) m\n" +
                "                     where o.id = p.oper_id\n" +
                "                       and o.id = c.oper_id\n" +
                "                       and o.id = m.oper_id\n" +
                "                       and b.entity_type = 'ENTTCARD'\n" +
                "                       and b.object_id = n.card_id\n" +
                "                       and b.account_id = a.id\n" +
                "                       and a.agent_id = g.id\n" +
                "             and o.id  = au.id (+)\n" +
                "                       and reverse(c.card_number) = reverse(n.card_number)\n" +
                "                       and c.split_hash = p.split_hash\n" +
                "                       and c.participant_type = p.participant_type\n" +
                "                       and p.participant_type = 'PRTYISS'\n" +
                "                       and a.account_type in ('ACTP0130'\n" +
                "                                            , 'ACTP9661'\n" +
                "                                            )\n" +
                "                       and o.sttl_type in ('STTT0010'\n" +
                "                                         , 'STTT0000'\n" +
                "                                         , 'STTT0007'\n" +
                "                                         )\n" +
                "                       and o.oper_currency = '704'\n" +
                "                       and o.status = 'OPST0400'\n" +
                "                       and o.oper_type in ('OPTP0026'\n" +
                "                                         , 'OPTP0028'\n" +
                "                                         , 'OPTP0422'\n" +
                "                                         , 'OPTP0402'\n" +
                "                                         , 'OPTP0043'\n" +
                "                                         )\n" +
                "                       and p.client_id_type = 'CITPCARD'\n" +
                "                       and to_char(m.posting_date,'yyyymmdd') between " + startDate + " and " + endDate+ "\n" +
                "                    )\n" +
                "             order by oper_id\n" +
                "                    , agent_number\n";

        List<ReportInformation> reportInformations = jdbcTemplate.query(sql, new ReportMapper());
        return reportInformations;
    }

}
