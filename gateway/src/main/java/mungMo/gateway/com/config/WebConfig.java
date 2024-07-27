package mungMo.gateway.com.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
public class WebConfig implements WebFluxConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
//                .allowedOrigins("http://118.67.132.171")
//                .allowedOrigins("http://dev.utteok.com")
                .allowedOrigins("*")
                .allowedMethods("GET","POST","PATCH","PUT","DELETE")
                .allowCredentials(false);
        System.out.println("WebConfig addCorsMappings");
    }
}