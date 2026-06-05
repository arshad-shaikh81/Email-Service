package com.emailservice.emailservice.kafka;

import com.emailservice.emailservice.model.EmailFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailProducer {

    private final KafkaTemplate<String, EmailFormat> kafkaTemplate;

    @Value("${spring.kafka.topic.name}")
    private String topicName;

    public void sendEmailEvent(EmailFormat emailFormat) {
        System.out.println("==============================");
        System.out.println("📨 Producing Kafka message...");
        System.out.println("Topic: "   + topicName);
        System.out.println("To Email: " + emailFormat.getToEmail());
        System.out.println("==============================");

        kafkaTemplate.send(topicName, emailFormat)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        System.out.println("❌ Kafka send FAILED: " + ex.getMessage());
                    } else {
                        System.out.println("✅ Kafka send SUCCESS - partition: "
                                + result.getRecordMetadata().partition()
                                + " offset: "
                                + result.getRecordMetadata().offset());
                    }
                });
    }
}