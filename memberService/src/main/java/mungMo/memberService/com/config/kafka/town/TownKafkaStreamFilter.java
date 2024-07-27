package mungMo.memberService.com.config.kafka.town;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.Properties;

@Configuration
public class TownKafkaStreamFilter {

    @Value("${kafka.server}")
    private String bootstrapServers;

    @Value("${kafka.town.topic}")
    private String townTopic;

    @Value("${kafka.town.filter.topic}")
    private String townFilterTopic;

    @Value("${kafka.town.filter.standardCertification}")
    private String townFilterStandardCertification;

    @Value("${kafka.town.filter.standardDate}")
    private String townFilterStandardDate;

    @Bean
    public KafkaStreams kafkaStreams() {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "date-filter-application");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        StreamsBuilder builder = new StreamsBuilder();
        KStream<String, String> source = builder.stream(townTopic);

        KStream<String, String> filteredStream = source.filter((key, value) -> {
            try {

                JsonNode jsonNode = new ObjectMapper().readTree(value);

                boolean isCertified = jsonNode.get(townFilterStandardCertification).asBoolean();
                LocalDate date = LocalDate.parse(jsonNode.get(townFilterStandardDate).asText());

                return isCertified && date.isBefore(LocalDate.now().minusMonths(1));

            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });

        filteredStream.to(townFilterTopic);

        KafkaStreams streams = new KafkaStreams(builder.build(), props);
        streams.start();

        return streams;
    }
}
