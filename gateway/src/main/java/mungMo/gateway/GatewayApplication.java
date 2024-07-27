package mungMo.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GatewayApplication {

//https://kauth.kakao.com/oauth/authorize?client_id=1b02a8c5d5f529866e3bb44855645b62&redirect_uri=http://223.130.154.200:8000/member-service/v1/auth/kakao&response_type=code

//https://kauth.kakao.com/oauth/authorize?client_id=1b02a8c5d5f529866e3bb44855645b62&redirect_uri=http://localhost:8000/member-service/v1/kakao&response_type=code
//https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=7suUMkLPrKL69mjXEPIr&redirect_uri=http://localhost:8000/member-service/v1/naver&state=wefkjn
	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

}
