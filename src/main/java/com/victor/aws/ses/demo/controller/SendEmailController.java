package com.victor.aws.ses.demo.controller;

import com.victor.aws.ses.demo.controller.request.SendEmailRequest;
import com.victor.aws.ses.demo.service.SendEmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/emails")
@Slf4j
public class SendEmailController {

    private final SendEmailService service;

    public SendEmailController(SendEmailService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> sendEmail(@RequestBody SendEmailRequest request) {
        log.info("email request received {}", request);
        if (request.getDestination() != null) {
            log.info("request destination email validated {}", request.getDestination());
            return ResponseEntity.ok(service.sendEmail(request.getDestination()));
        }
        log.info("the destination email is invalid {}", request.getDestination());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body("The destination email is required");
    }
}
