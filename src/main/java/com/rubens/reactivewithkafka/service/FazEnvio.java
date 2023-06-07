package com.rubens.reactivewithkafka.service;

import com.rubens.reactivewithkafka.config.KafkaConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Properties;
import java.util.Scanner;

@Primary
@Service
@EnableKafka
public class FazEnvio {

    public void producer() throws InterruptedException {

        var producers = new KafkaProducer<String, String>(properties());

        Scanner teclado = new Scanner(System.in);

        Thread.sleep(5000);
        System.out.println("Informe a mensagem: ");
        String value = teclado.next();

        var record = new ProducerRecord<String, String>("WhatsApp", value, value);

        producers.send(record, (metadata, exception) -> {
            if (exception != null) {
                exception.printStackTrace();
                return;
            }
            try {
                System.out.println("Mensagem enviada com sucesso! " + metadata.topic() + ":::partition"
                        + metadata.partition() + "/ offset " + metadata.offset() + "/ timestamp " + metadata.timestamp());
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        });

    }

    private static Properties properties() {
        var properties =  new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return properties;
    }
}
