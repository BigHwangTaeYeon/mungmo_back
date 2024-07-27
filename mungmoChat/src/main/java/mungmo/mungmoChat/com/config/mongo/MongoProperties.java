package mungmo.mungmoChat.com.config.mongo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "mongodb")
public class MongoProperties {
    private String client;
    private String database;
}
