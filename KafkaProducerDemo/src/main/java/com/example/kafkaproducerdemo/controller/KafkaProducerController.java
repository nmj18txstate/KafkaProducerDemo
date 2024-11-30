package com.example.kafkaproducerdemo.controller;

import com.example.kafkaproducerdemo.service.KafkaMessageProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/kafka")
public class KafkaProducerController {

    private final KafkaMessageProducer kafkaMessageProducer;

    public KafkaProducerController(KafkaMessageProducer kafkaMessageProducer) {
        this.kafkaMessageProducer = kafkaMessageProducer;
    }

    @PostMapping("/publish")
    public ResponseEntity<String> sendMessageToKafka(@RequestParam("message") String message) {
        kafkaMessageProducer.sendMessage(message);
        return ResponseEntity.ok("Message sent to Kafka topic successfully.");
    }
}
