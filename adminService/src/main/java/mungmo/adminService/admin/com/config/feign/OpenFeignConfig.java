package mungmo.adminService.admin.com.config.feign;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients("mungmo.adminService.admin.otherDomain")
class OpenFeignConfig {

}
