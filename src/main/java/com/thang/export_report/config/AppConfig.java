package com.thang.export_report.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class AppConfig {

    @Value("${svng.url}")
    private String url;

    @Value("${svng.username}")
    private String username;

    @Value("${svng.password}")
    private String password;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName(oracle.jdbc.driver.OracleDriver.class.getName());
        ds.setUrl(url);
        ds.setUsername(username);
        ds.setPassword(password);
        ds.setSchema("main");
        return ds;
    }
}
