package com.acabouomony.solutis.email_service.amqp;

import com.acabouomony.solutis.email_service.dto.ConfirmationCodeDTO;
import com.acabouomony.solutis.email_service.dto.EmailDTO;
import com.acabouomony.solutis.email_service.service.EmailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountListener {
    @Autowired
    private EmailService emailService;

    @RabbitListener(queues = "Queue.send.emails")
    public void envioEmailCodigo(ConfirmationCodeDTO codeDTO){
        emailService.sendEmail(codeDTO.email(), "Codigo de confirmação", codeDTO.code());
    }
    @RabbitListener(queues = "email-notification")
    public void envioEmailStatusPedido(EmailDTO emailDTO){
        System.out.println("Enviando email..." + emailDTO.getEmail());
        emailService.sendOrderEmail(emailDTO);
    }
}
