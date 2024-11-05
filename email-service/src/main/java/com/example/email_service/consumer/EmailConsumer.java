package com.example.email_service.consumer;

import com.example.email_service.dto.Code2FADto;
import com.example.email_service.dto.PaymentConfirmationDto;
import com.example.email_service.model.EmailModel;
import com.example.email_service.service.EmailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class EmailConsumer {

    private final EmailService emailService;

    public EmailConsumer(EmailService emailService) {
        this.emailService = emailService;
    }

    @RabbitListener(queues = "${broker.queue.email.name.authentication}")
    public void listenCode2FA (@Payload Code2FADto code2FADto) {
        EmailModel emailModel = emailService.createCodeEmail(code2FADto);
        emailService.sendEmail(emailModel);
    }

    @RabbitListener(queues = "${broker.queue.email.name.confirmation}")
    public void listenPaymentConfirmation (@Payload PaymentConfirmationDto paymentConfirmationDto) {
        EmailModel emailModel = emailService.createConfirmationEmail(paymentConfirmationDto);
        emailService.sendEmail(emailModel);
    }


}
