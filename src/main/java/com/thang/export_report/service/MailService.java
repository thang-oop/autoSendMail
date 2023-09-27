package com.thang.export_report.service;

import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.activation.DataHandler;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    private final String subject = "Credit card payment report of date: ";

    private static final Logger logger = LoggerFactory.getLogger(ReportServiceTask.class);

    @SneakyThrows
    public void sendEmailAttachment(final String fileName, final String fromEmailAddress, final String[] toEmailAddress, ByteArrayDataSource fds, String body) {

        logger.info("Start send email!!");

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom(fromEmailAddress);
        helper.setTo(toEmailAddress);
        helper.setSubject(subject + fileName);
        helper.setText("<html><body>" + body + "</html></body>", true);

        MimeBodyPart mbp1 = new MimeBodyPart();
        mbp1.setContent(body, "text/html; charset=utf-8" );

        MimeBodyPart mbp2 = new MimeBodyPart();
        mbp2.setDataHandler(new DataHandler(fds));
        System.out.println(fds.getName());
        mbp2.setFileName(fileName+".xls");

        Multipart mp = new MimeMultipart();
        mp.addBodyPart(mbp1);
        mp.addBodyPart(mbp2);

        mimeMessage.setContent(mp);
        mimeMessage.saveChanges();

        logger.info("Email sending ....");

        mailSender.send(mimeMessage);

        logger.info("Email sending complete.");
    }

}
