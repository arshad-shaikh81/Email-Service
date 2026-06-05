package com.emailservice.emailservice.config;

import com.emailservice.emailservice.model.EmailFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;

    // ===== SHARED OBJECTMAPPER =====
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }

    // ===== PRODUCER =====
    @Bean
    public ProducerFactory<String, EmailFormat> producerFactory(ObjectMapper objectMapper) {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        DefaultKafkaProducerFactory<String, EmailFormat> factory =
                new DefaultKafkaProducerFactory<>(config);

        // Use Jackson manually to serialize to JSON string
        factory.setValueSerializer(new org.apache.kafka.common.serialization.Serializer<>() {
            @Override
            public byte[] serialize(String topic, EmailFormat data) {
                try {
                    return objectMapper.writeValueAsBytes(data);
                } catch (Exception e) {
                    throw new RuntimeException("Error serializing EmailFormat", e);
                }
            }
        });
        return factory;
    }

    @Bean
    public KafkaTemplate<String, EmailFormat> kafkaTemplate(
            ProducerFactory<String, EmailFormat> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic signupEmailTopic() {
        return new NewTopic("sendEmail", 1, (short) 1);
    }

    // ===== CONSUMER =====
    @Bean
    public ConsumerFactory<String, EmailFormat> consumerFactory(ObjectMapper objectMapper) {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        // Custom deserializer using Jackson
        org.apache.kafka.common.serialization.Deserializer<EmailFormat> valueDeserializer =
                new org.apache.kafka.common.serialization.Deserializer<>() {
                    @Override
                    public EmailFormat deserialize(String topic, byte[] data) {
                        try {
                            return objectMapper.readValue(data, EmailFormat.class);
                        } catch (Exception e) {
                            throw new RuntimeException("Error deserializing EmailFormat", e);
                        }
                    }
                };

        return new DefaultKafkaConsumerFactory<>(
                config,
                new StringDeserializer(),
                valueDeserializer
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, EmailFormat> kafkaListenerContainerFactory(
            ConsumerFactory<String, EmailFormat> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, EmailFormat> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }
}