package com.rubens.reactivewithkafka;

import com.rubens.reactivewithkafka.config.KafkaConfig;
import com.rubens.reactivewithkafka.service.FazEnvio;
import com.rubens.reactivewithkafka.service.FazTestes;
import com.sun.tools.javac.Main;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Scanner;

@SpringBootApplication
@EnableKafka
public class ReactiveWithKafkaApplication {


	public static void main(String[] args) throws InterruptedException {
		ConfigurableApplicationContext context = SpringApplication.run(ReactiveWithKafkaApplication.class, args);

		Scanner teclado = new Scanner(System.in);

		Thread.sleep(5000);
		System.out.println("Deseja enviar uma mensagem? 1 - Sim, 2 - Não");
		var nmr = teclado.nextInt();
		Thread.sleep(3000);


		while (nmr == 1) {
			var a = new FazEnvio();
			a.producer();
			Thread.sleep(5000);
			System.out.println("Deseja enviar uma mensagem novamente? 1 - Sim, 2 - Não");
			var nmrs = teclado.nextInt();
			Thread.sleep(3000);
			nmr = nmrs;
			if (nmr == 2) {
				System.out.println("Até logo");
				context.close();
			}
		}

	}
}
