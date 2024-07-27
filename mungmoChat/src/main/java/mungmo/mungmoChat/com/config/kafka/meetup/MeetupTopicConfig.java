package mungmo.mungmoChat.com.config.kafka.meetup;

import com.google.common.collect.ImmutableMap;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.beans.factory.annotation.Value;

import java.util.Map;

@EnableKafka
@Configuration
public class MeetupTopicConfig {

    @Value("${kafka.server}")
    private String kafkaServer;

    @Value("${kafka.topic.message.meetup}")
    private String chatTopic;

    // Kafka Admin 설정
    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = ImmutableMap.<String, Object>builder()
                .put(org.apache.kafka.clients.admin.AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer)
                .build();
        return new KafkaAdmin(configs);
    }

    // Kafka Chat Topic 설정
    @Bean
    public NewTopic chatTopic() {
        return new NewTopic(chatTopic, 3, (short) 3); // 파티션 3개, 리플리카 3개
    }
}