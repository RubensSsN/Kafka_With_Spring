package com.rubens.reactivewithkafka.service;

import io.micrometer.core.lang.Nullable;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.rubens.reactivewithkafka.model.TestePerson;
import com.rubens.reactivewithkafka.model.TesteRobos;
import org.springframework.stereotype.Service;

@Service
@EnableKafka
public class FazTestes {



    @KafkaListener(topics = "WhatsApp", groupId = "Teste_spring", containerFactory = "kafkaListenerContainerFactory")
    public void consume(String message) {
        System.out.println(message + " Deu certinho mané");
    }

}
