package mungMo.memberService.com.config.oauth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
class ClientConfig {
// oauth http 통신용
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
