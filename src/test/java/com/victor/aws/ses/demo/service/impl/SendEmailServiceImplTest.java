package com.victor.aws.ses.demo.service.impl;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import com.victor.aws.ses.demo.client.EmailClient;
import com.victor.aws.ses.demo.client.exception.EmailException;
import com.victor.aws.ses.demo.controller.request.SendEmailRequest;
import com.victor.aws.ses.demo.service.SendEmailService;
import org.junit.jupiter.api.Test;

class SendEmailServiceImplTest {

    EmailClient client = mock(EmailClient.class);
    SendEmailService service = new SendEmailServiceImpl(client);

    @Test
    void givenValidDestinationEmailThenShouldSendEmailWithSuccess() {
        doNothing().when(client).send(any(SendEmailRequest.class));

        SendEmailRequest request = new SendEmailRequest();
        request.setDestination("teste@teste.com");
        request.setSubject("Teste");
        request.setMessage("Teste");
        String response = service.sendEmail(request);

        then(response).isEqualTo("Email sent");
    }

    @Test
    void givenInvalidDestinationEmailThenShouldThrowEmailException() {
        doThrow(EmailException.class).when(client).send(any(SendEmailRequest.class));

        String response = service.sendEmail(new SendEmailRequest());

        then(response).isEqualTo("Email sent failed due to null");
    }
}
