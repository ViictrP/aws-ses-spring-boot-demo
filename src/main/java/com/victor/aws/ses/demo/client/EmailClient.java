package com.victor.aws.ses.demo.client;

import com.victor.aws.ses.demo.client.exception.EmailException;
import com.victor.aws.ses.demo.controller.request.SendEmailRequest;

public interface EmailClient {

    void send(SendEmailRequest request) throws EmailException;
}
