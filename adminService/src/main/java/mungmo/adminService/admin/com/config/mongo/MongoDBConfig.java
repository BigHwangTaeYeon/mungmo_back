package mungmo.adminService.admin.com.config.mongo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "mungmo.adminService.admin")
@EnableMongoAuditing
public class MongoDBConfig {

    private final MongoProperties mongoProperties;

    public MongoDBConfig(MongoProperties mongoProperties) {
        this.mongoProperties = mongoProperties;
    }

    @Bean
    public MongoClient mongoClient() {
        return MongoClients.create(mongoProperties.getClient());
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), mongoProperties.getDatabase());
    }
}