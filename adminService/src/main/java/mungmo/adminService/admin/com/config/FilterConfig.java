package mungmo.adminService.admin.com.config;

import jakarta.servlet.Filter;
import mungmo.adminService.admin.com.filter.AuthorizationFilter;
import mungmo.adminService.admin.otherDomain.member.service.MemberServiceClient;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    private final MemberServiceClient memberServiceClient;

    public FilterConfig(MemberServiceClient memberServiceClient) {
        this.memberServiceClient = memberServiceClient;
    }

    @Bean
    public FilterRegistrationBean<Filter> AuthorizationFilter() {
        FilterRegistrationBean<Filter> bean = new FilterRegistrationBean<>();

        bean.setFilter(new AuthorizationFilter(memberServiceClient));
        bean.setOrder(1);
        bean.addUrlPatterns("/*");

        return bean;
    }
}
