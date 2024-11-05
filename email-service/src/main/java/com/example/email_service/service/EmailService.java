package com.example.email_service.service;

import com.example.email_service.dto.Code2FADto;
import com.example.email_service.dto.PaymentConfirmationDto;
import com.example.email_service.enums.StatusEmail;
import com.example.email_service.exception.EmailSendingException;
import com.example.email_service.model.EmailModel;
import com.example.email_service.repository.EmailRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class EmailService {

    final EmailRepository emailRepository;
    final JavaMailSender emailSender;

    public EmailService(EmailRepository emailRepository, JavaMailSender emailSender) {
        this.emailRepository = emailRepository;
        this.emailSender = emailSender;
    }

    @Value(value = "${spring.mail.username}")
    private String emailFrom;

    @Transactional
    public void sendEmail(EmailModel emailModel) {
        try{
            emailModel.setSendDateEmail(LocalDateTime.now());
            emailModel.setEmailFrom(emailFrom);

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(emailModel.getEmailTo());
            message.setSubject(emailModel.getSubject());
            message.setText(emailModel.getText());
            emailSender.send(message);

            emailModel.setStatusEmail(StatusEmail.SENT);
        } catch (Exception e){
            emailModel.setStatusEmail(StatusEmail.ERROR);
            throw new EmailSendingException("Erro ao tentar enviar o email", e);
        } finally {
            emailRepository.save(emailModel);
        }
    }

    public EmailModel createCodeEmail(Code2FADto code2FADto) {
        EmailModel emailModel = new EmailModel();
        emailModel.setUserId(code2FADto.userId());
        emailModel.setEmailTo(code2FADto.emailTo());
        emailModel.setSubject("Código de verificação: " + code2FADto.code2FA());
        emailModel.setText("O seu código de verificação é " + code2FADto.code2FA() + ".\n" +
                "Se você não solicitou isso, simplesmente ignore esta mensagem.\n\n" +
                "Atenciosamente,\n" );
        return emailModel;
    }

    public EmailModel createConfirmationEmail(PaymentConfirmationDto paymentConfirmationDto) {
        EmailModel emailModel = new EmailModel();
        emailModel.setUserId(paymentConfirmationDto.userId());
        emailModel.setEmailTo(paymentConfirmationDto.emailTo());
        emailModel.setSubject("Pagamento: #" + paymentConfirmationDto.paymentId() + " - " + paymentConfirmationDto.status());
        emailModel.setText(String.format(
                "Olá, " + paymentConfirmationDto.nameUser() + "!\n\n" +
                        "Seu pedido de pagamento #" + paymentConfirmationDto.paymentId() + " no valor de R$"
                        + paymentConfirmationDto.value() + " foi " + paymentConfirmationDto.status() + "\n\n" +
                        "Atenciosamente,\nEquipe do Acabou o Mony."
        ));
        return emailModel;
    }
}
