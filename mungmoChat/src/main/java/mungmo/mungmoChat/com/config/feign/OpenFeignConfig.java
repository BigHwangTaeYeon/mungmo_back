package mungmo.mungmoChat.com.config.feign;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients("mungmo.mungmoChat.otherDomain")
class OpenFeignConfig {

}
