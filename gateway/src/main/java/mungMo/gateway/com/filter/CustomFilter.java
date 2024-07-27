package mungMo.gateway.com.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class CustomFilter extends AbstractGatewayFilterFactory<CustomFilter.Config> {
    public CustomFilter(){
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain)-> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            //pre필터 적용
            log.info("{} PRE filter: request id-> {}", config.baseMessage, request.getId());

            //post 필터 적용
            return chain.filter(exchange).then(Mono.fromRunnable(()-> {
                log.info("{} POST filter: response code -> {}", config.baseMessage, response.getStatusCode());
            }));
        };
    }

    public static class Config {
        private String baseMessage;
    }
}