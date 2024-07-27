package mungMo.memberService.com.config.kafka.town;

import com.google.common.collect.ImmutableMap;
import mungMo.memberService.domain.town.dto.TownDTO;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.Map;

@EnableKafka
@Configuration
public class ListenerConfig {

    @Value("${kafka.server}")
    private String kafkaServer;

    @Value("${kafka.town.consumer.id}")
    private String kafkaConsumerGroupId;

    @Bean
    ConcurrentKafkaListenerContainerFactory<String, TownDTO> kafkaTownContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, TownDTO> factory = new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(kafkaTownConsumer());
        factory.setConcurrency(3); // 컨슈머 수를 2로 설정
        return factory;
    }

    @Bean
    public ConsumerFactory<String, TownDTO> kafkaTownConsumer() {

        JsonDeserializer<TownDTO> deserializer = new JsonDeserializer<>();

        deserializer.addTrustedPackages("*");

        Map<String, Object> consumerConfigurations =
                ImmutableMap.<String, Object>builder()
                        .put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer)
                        .put(ConsumerConfig.GROUP_ID_CONFIG, kafkaConsumerGroupId)
                        .put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class)
                        .put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserializer)
                        .put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest")
                        .put(ConsumerConfig.FETCH_MIN_BYTES_CONFIG, "10000")
                        .put(ConsumerConfig.FETCH_MAX_WAIT_MS_CONFIG, "200")
                        .build();

        return new DefaultKafkaConsumerFactory<>(consumerConfigurations, new StringDeserializer(), deserializer);
    }
}