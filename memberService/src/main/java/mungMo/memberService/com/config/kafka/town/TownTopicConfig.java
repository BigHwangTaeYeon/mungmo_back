package mungMo.memberService.com.config.kafka.town;

import com.google.common.collect.ImmutableMap;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.Map;

@EnableKafka
@Configuration
public class TownTopicConfig {

    @Value("${kafka.server}")
    private String kafkaServer;

    @Value("${kafka.town.filter.topic}")
    private String townTopic;

    // Kafka Admin 설정
    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = ImmutableMap.<String, Object>builder()
                .put(org.apache.kafka.clients.admin.AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer)
                .build();
        return new KafkaAdmin(configs);
    }

    // Kafka town Topic 설정
    @Bean
    public NewTopic townTopic() {
        return new NewTopic(townTopic, 3, (short) 3); // 파티션 3개, 리플리카 3개
    }
}