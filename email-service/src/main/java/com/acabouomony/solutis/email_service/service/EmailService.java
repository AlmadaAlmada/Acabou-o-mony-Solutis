package com.acabouomony.solutis.email_service.service;

import com.acabouomony.solutis.email_service.dto.EmailDTO;
import com.acabouomony.solutis.email_service.dto.PaymentStatus;
import com.acabouomony.solutis.email_service.exception.SendEmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import jakarta.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String senderEmail;

    /**
     * Envia um email simples com um código de confirmação.
     *
     * @param recipient   O destinatário do email.
     * @param subject     O assunto do email.
     * @param confirmationCode O código de confirmação a ser enviado.
     */
    public void sendConfirmationEmail(String recipient, String subject, String confirmationCode) {
        String emailContent = buildConfirmationEmail(confirmationCode);

        sendEmail(recipient, subject, emailContent);
    }

    /**
     * Envia um email com os detalhes de um pedido.
     *
     * @param emailDTO Objeto contendo as informações do email.
     */
    public void sendOrderEmail(EmailDTO emailDTO) {
        String emailContent = buildOrderEmail(emailDTO);

        sendEmail(emailDTO.getEmail(), "Confirmação de Pedido - Acabou o Mony", emailContent);
    }

    private String buildConfirmationEmail(String confirmationCode) {
        return """
                <html>
                    <body style="font-family: Arial, sans-serif; text-align: center; color: #333;">
                        <h2>Bem-vindo à <strong>Acabou o Mony</strong></h2>
                        <p>Utilize o código abaixo para confirmar sua identidade:</p>
                        <div style="background-color: #ffe6f0; color: #b8860b; font-size: 24px; font-weight: bold; padding: 20px; border-radius: 8px;">
                            """ + confirmationCode + """
                        </div>
                        <p style="margin-top: 20px; color: #666;">Equipe Acabou o Mony</p>
                    </body>
                </html>
                """;
    }

    private String buildOrderEmail(EmailDTO emailDTO) {
        StringBuilder emailBuilder = new StringBuilder();

        emailBuilder.append("<html><body style=\"font-family: Arial, sans-serif; text-align: center; color: #333;\">")
                .append("<h2>Olá, <strong>").append(emailDTO.getNameUser()).append("</strong></h2>")
                .append("<p>Detalhes do seu pedido:</p>")
                .append("<h3 style=\"color: ")
                .append("<p><strong>Valor total:</strong> R$ ").append(emailDTO.getValue()).append("</p>");

        emailBuilder.append("<p>Obrigado por confiar em nós!</p>")
                .append("<p>Equipe Acabou o Mony</p>")
                .append("</body></html>");

        return emailBuilder.toString();
    }

    private String getPaymentStatusMessage(PaymentStatus status) {
        return switch (status) {
            case AGUARDANDO -> "Seu pedido está aguardando confirmação de pagamento.";
            case CONFIRMADO -> "Seu pagamento foi confirmado com sucesso!";
            default -> "Status do pedido: " + status;
        };
    }

    private String getPaymentStatusColor(PaymentStatus status) {
        return switch (status) {
            case AGUARDANDO -> "#ff9900";
            case CONFIRMADO -> "#28a745";
            default -> "#333";
        };
    }

    public void sendEmail(String recipient, String subject, String content) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setFrom(senderEmail);
            helper.setTo(recipient);
            helper.setSubject(subject);
            helper.setText(content, true);

            mailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new SendEmailException("Erro ao enviar o email: " + e.getMessage());
        }
    }
}
