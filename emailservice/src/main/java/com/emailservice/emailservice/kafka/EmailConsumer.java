package com.emailservice.emailservice.kafka;

import com.emailservice.emailservice.model.EmailFormat;
import com.emailservice.emailservice.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailConsumer {

    private final EmailService emailService;

    @KafkaListener(
            topics = "${spring.kafka.topic.name}",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consumeEmailEvent(EmailFormat emailFormat) {
        System.out.println("==============================");
        System.out.println("✅ Kafka Message Received!");
        System.out.println("To: " + emailFormat.getToEmail());
        System.out.println("==============================");
        emailService.sendActualEmail(emailFormat);
    }
}