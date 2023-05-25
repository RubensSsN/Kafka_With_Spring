package com.rubens.reactivewithkafka;

import com.rubens.reactivewithkafka.model.TestePerson;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication
public class ReactiveWithKafkaApplication {

	public static KafkaTemplate template;

	public void teste(String message) {
		template.send("teste", new TestePerson("teste", "teste"));
	}

	public static void main(String[] args) {
		SpringApplication.run(ReactiveWithKafkaApplication.class, args);
	}

}
