package mungMo.gateway.com.filter;

import lombok.extern.slf4j.Slf4j;
import mungMo.gateway.com.jwt.JwtTokenProvider;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Slf4j
@Component
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {

    private final JwtTokenProvider jwtUtil;

    public AuthorizationHeaderFilter(JwtTokenProvider jwtUtil) {
        super(Config.class);
        this.jwtUtil = jwtUtil;
    }

    // GatewayFilter 설정을 위한 Config 클래스
    public static class Config {

    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            // HTTP 요청 헤더에서 Authorization 헤더를 가져옴
            HttpHeaders headers = request.getHeaders();
            if (!headers.containsKey(HttpHeaders.AUTHORIZATION)) {
                return onError(exchange, "HTTP 요청 헤더에 Authorization 헤더가 포함되어 있지 않습니다.", HttpStatus.UNAUTHORIZED);
            }

            String authorizationHeader = Objects.requireNonNull(headers.get(HttpHeaders.AUTHORIZATION)).get(0);

            // JWT 토큰 가져오기
            String accessToken = authorizationHeader.replace("Bearer ", "");
            log.info("[*] Token exists");

            // JWT 토큰 유효성 검사 & id 추출하여 HTTP 요청 헤더에 추가하여 전달
            String subject = jwtUtil.extractSubject(accessToken);

            // 사용자 email를 HTTP 요청 헤더에 추가하여 전달
            ServerHttpRequest newRequest = request.mutate()
                    .header("userId", subject)
                    .build();

            return chain.filter(exchange.mutate().request(newRequest).build());
        };
    }

    // Mono(단일 값), Flux(다중 값) -> Spring WebFlux
    private Mono<Void> onError(ServerWebExchange exchange, String errorMsg, HttpStatus httpStatus) {
        log.error(errorMsg);

        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);

        return response.setComplete();
    }

}