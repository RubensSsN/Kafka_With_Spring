package com.rubens.reactivewithkafka;

import com.rubens.reactivewithkafka.service.FazTestes;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication
@EnableKafka
public class ReactiveWithKafkaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReactiveWithKafkaApplication.class, args);
	}

}
