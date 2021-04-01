package com.victor.aws.ses.demo.infra;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.SendTemplatedEmailRequest;
import com.victor.aws.ses.demo.client.EmailClient;
import com.victor.aws.ses.demo.client.exception.EmailException;
import com.victor.aws.ses.demo.controller.request.SendEmailRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AwsSesEmailClient implements EmailClient {

    @Value("${aws.email.from}")
    private String from;

    @Value("${aws.email.template}")
    private String template;

    @Value("${aws.credentials.user_key}")
    private String userKey;

    @Value("${aws.credentials.user_password_key}")
    private String userPasswordKey;

    @Override
    public void send(SendEmailRequest request) throws EmailException {
        try {
            log.info("creating the aws credentials object");
            BasicAWSCredentials awsCreds = new BasicAWSCredentials(userKey, userPasswordKey);
            log.info("creating the SES client in <PUT YOUR REGION>");
            AmazonSimpleEmailService ses = AmazonSimpleEmailServiceClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .withRegion(Regions.US_EAST_1) // <- PUT YOUT REGION
                .build();

            log.info("creating the request with destination {}", request.getDestination());
            SendTemplatedEmailRequest sendEmailRequest = new SendTemplatedEmailRequest()
                .withDestination(new Destination().withToAddresses(request.getDestination()))
                .withSource(from)
                .withTemplateData("{ \"subject\":\"" + request.getSubject() + "\", \"userName\":\""+ request.getMessage() +"\" }")
                .withTemplate(template);

            ses.sendTemplatedEmail(sendEmailRequest);
            log.info("email sent");
        } catch (Exception ex) {
            log.info("{}", ex.getMessage());
            throw new EmailException(ex.getMessage());
        }
    }
}
