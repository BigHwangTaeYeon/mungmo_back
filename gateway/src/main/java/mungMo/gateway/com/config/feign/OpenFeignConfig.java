package mungMo.gateway.com.config.feign;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients("mungMo.gateway.domain")
class OpenFeignConfig {

}
