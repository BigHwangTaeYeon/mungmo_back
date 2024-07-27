//package mungmo.mungmoChat.com.config.kafka.producer;
//
//import com.google.common.collect.ImmutableMap;
//import mungmo.mungmoChat.otherDomain.Notification.dto.Notification;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.annotation.EnableKafka;
//import org.springframework.kafka.core.DefaultKafkaProducerFactory;
//import org.springframework.kafka.core.KafkaTemplate;
//import mungmo.mungmoChat.domain.massage.dto.Message;
//import org.springframework.kafka.core.ProducerFactory;
//import org.apache.kafka.common.serialization.StringSerializer;
//import org.springframework.kafka.support.serializer.JsonSerializer;
//import org.springframework.beans.factory.annotation.Value;
//
//import java.util.Map;
//
//@EnableKafka
//@Configuration
//public class ProducerConfig {
//
//    @Value("${kafka.server}")
//    private String kafkaServer;
//
//    // Kafka ProducerFactory를 생성하는 Bean 메서드
//    @Bean
//    public ProducerFactory<String, Message> chatProducerFactory() {
//        return new DefaultKafkaProducerFactory<>(chatProducerConfigurations());
//    }
//
//    // Kafka Producer 구성을 위한 설정값들을 포함한 맵을 반환하는 메서드
//    @Bean
//    public Map<String, Object> chatProducerConfigurations() {
//        return ImmutableMap.<String, Object>builder()
//                .put(org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer)
//                .put(org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class)
//                .put(org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class)
//                .build();
//    }
//
//    // KafkaTemplate 를 생성하는 Bean 메서드
//    @Bean
//    public KafkaTemplate<String, Message> chatKafkaTemplate() {
//        return new KafkaTemplate<>(chatProducerFactory());
//    }
//
//    // 채팅 메시지 알림을 위한 kafka 설정
//    @Bean
//    public ProducerFactory<String, Notification> notificationProducerFactory() {
//        return new DefaultKafkaProducerFactory<>(notificationProducerConfigurations());
//    }
//
//    @Bean
//    public Map<String, Object> notificationProducerConfigurations() {
//        return ImmutableMap.<String, Object>builder()
//                .put(org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer)
//                .put(org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class)
//                .put(org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class)
//                .build();
//    }
//
//    @Bean
//    public KafkaTemplate<String, Notification> notificationKafkaTemplate() {
//        return new KafkaTemplate<>(notificationProducerFactory());
//    }
//}