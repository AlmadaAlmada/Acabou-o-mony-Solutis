package com.acabouomony.solutis.payment_service.producer;

import com.acabouomony.solutis.payment_service.dto.EmailDto;
import com.acabouomony.solutis.payment_service.model.PaymentModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PaymentProducer {

    private final RabbitTemplate rabbitTemplate;

    public PaymentProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Value(value = "${broker.queue.email.name.confirmation}")
    private String routingKey;

    public void publishMessageEmail(PaymentModel paymentModel) {
        EmailDto emailDto = new EmailDto(paymentModel.getPaymentId(), paymentModel.getOrderNumber(),
                paymentModel.getUserId(), paymentModel.getNameUser(),
                paymentModel.getEmail(), paymentModel.getValue(), paymentModel.getPaymentStatus().toString());

        rabbitTemplate.convertAndSend("", routingKey, emailDto);
    }
}
