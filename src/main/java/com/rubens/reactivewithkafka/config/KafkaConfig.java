package com.rubens.reactivewithkafka.config;

import com.rubens.reactivewithkafka.model.TestePerson;
import com.rubens.reactivewithkafka.model.TesteRobos;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.kafka.support.mapping.DefaultJackson2JavaTypeMapper;
import org.springframework.kafka.support.mapping.Jackson2JavaTypeMapper;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.kafka.support.serializer.StringOrBytesSerializer;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Configuration
@EnableKafka
public class KafkaConfig {

    private static final Logger log = LoggerFactory.getLogger(KafkaConfig.class);

    private final KafkaProperties kafkaProperties;

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    private final Boolean createTopics;

    private KafkaConfig(KafkaAdmin kafkaAdmin, KafkaProperties kafkaProperties, @Value("true") final Boolean createTopics) {
        this.createTopics = Objects.requireNonNull(createTopics);
        this.kafkaProperties = Objects.requireNonNull(kafkaProperties);
        kafkaAdmin.setAutoCreate(createTopics);
        kafkaAdmin.setFatalIfBrokerNotAvailable(createTopics);
    }

    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>(
                kafkaProperties.buildProducerProperties());
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(JsonSerializer.TYPE_MAPPINGS, "testeperson:com.rubens.reactivewithkafka.model.TestePerson, testerobos:com.rubens.reactivewithkafka.model.TesteRobos");
        return props;
    }

    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public ConsumerFactory<String, Object> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class);
        props.put(
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                JsonDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object>
    kafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, Object> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setMessageConverter(multiTypeConverter()); // Seta o conversor da mensagem recebida.
        return factory;
    }

    public RecordMessageConverter multiTypeConverter() {
        StringJsonMessageConverter converter = new StringJsonMessageConverter();  // Conversor, ele transforma a mensagem recebida String em JSON e vice e versa.
        DefaultJackson2JavaTypeMapper typeMapper = new DefaultJackson2JavaTypeMapper(); // Ele que mapeia o tipo do objeto da mensagem.

        typeMapper.setTypePrecedence(Jackson2JavaTypeMapper.TypePrecedence.TYPE_ID);  // Aqui estamos dizendo que queremos mapear através do ID que é uma propriedades especial adicionada ao JSON para indicar o tipo
                                                                                      // de objeto correspondente. no nosso caso iremos pegar do cabeçalho de tipo (header type).
        Map<String, Class<?>> mappings = new HashMap<>();
        mappings.put("testeperson", TestePerson.class); // Está fazendo o mapeamento de quando encontrarmos "testeperson" entender que é uma TestePerson.class
        mappings.put("testerobos", TesteRobos.class); // Está fazendo o mapeamento de quando encontrarmos "testerobos" entender que é uma TesteRobos.class

        typeMapper.setIdClassMapping(mappings);  // Setando qual é o id das classes que vão ser mapeadas, ao
                                                 // encontrar no JSON essas propriedades já identificará que aquela mensagem é do tipo devido

        typeMapper.addTrustedPackages("com.rubens.reactivewithkafka.model"); // Informa aonde as classes devidas de mapeamento estão

        converter.setTypeMapper(typeMapper);  // Seta o mapeamento do conversor para o nosso typeMapper que fizemos acima.
        return converter;

    }

}
