package com.example.kafkaproducerdemo.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class KafkaMessageProducerTest {

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @Test
    void sendMessageShouldCallKafkaTemplateSend() {
        String topic = "testTopic";
        KafkaMessageProducer producer = new KafkaMessageProducer(kafkaTemplate, topic);
        String message = "Hello";

        producer.sendMessage(message);

        verify(kafkaTemplate).send(topic, message);
    }
}
