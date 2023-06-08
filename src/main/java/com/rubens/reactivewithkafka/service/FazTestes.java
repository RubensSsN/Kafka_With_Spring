package com.rubens.reactivewithkafka.service;

import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@EnableKafka
public class FazTestes {



    @KafkaListener(topics = "WhatsApp", groupId = "Teste_spring", containerFactory = "kafkaListenerContainerFactory")
    public void consume(String message) {
        System.out.println(message + " Deu certinho mané");
    }

}
