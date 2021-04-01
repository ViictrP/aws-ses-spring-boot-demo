package com.victor.aws.ses.demo.service.impl;

import com.victor.aws.ses.demo.client.EmailClient;
import com.victor.aws.ses.demo.client.exception.EmailException;
import com.victor.aws.ses.demo.controller.request.SendEmailRequest;
import com.victor.aws.ses.demo.service.SendEmailService;
import org.springframework.stereotype.Service;

@Service
public class SendEmailServiceImpl implements SendEmailService {

    private final EmailClient client;

    public SendEmailServiceImpl(EmailClient client) {
        this.client = client;
    }

    @Override
    public String sendEmail(SendEmailRequest request) {
        try {
            client.send(request);
            return "Email sent";
        } catch (EmailException ex) {
            return "Email sent failed due to " + ex.getMessage();
        }
    }
}
