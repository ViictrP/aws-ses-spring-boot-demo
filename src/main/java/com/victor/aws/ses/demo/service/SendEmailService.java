package com.victor.aws.ses.demo.service;

import com.victor.aws.ses.demo.controller.request.SendEmailRequest;

public interface SendEmailService {

    String sendEmail(SendEmailRequest request);
}
