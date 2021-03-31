package com.victor.aws.ses.demo.client;

import com.victor.aws.ses.demo.client.exception.EmailException;

public interface EmailClient {

    void send(String destination, String body) throws EmailException;
}
