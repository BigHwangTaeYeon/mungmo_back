package mungmo.adminService.admin.com.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mungmo.adminService.admin.com.config.ResponseMessage;
import mungmo.adminService.admin.otherDomain.member.entity.MemberAuthority;
import mungmo.adminService.admin.otherDomain.member.service.MemberServiceClient;

import java.io.IOException;

public class AuthorizationFilter implements Filter {
    private final MemberServiceClient memberServiceClient;

    public AuthorizationFilter(MemberServiceClient memberServiceClient) {
        this.memberServiceClient = memberServiceClient;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;

        if (authorized(req)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            res.setStatus(HttpServletResponse.SC_FORBIDDEN);
            res.setContentType("text/html;charset=utf-8");
            res.getWriter().write(ResponseMessage.FORBIDDEN.getMessage());
        }
    }

    private boolean authorized(HttpServletRequest req) {
        Long userId = Long.valueOf(req.getHeader("userId"));
        MemberAuthority memberAuthority = memberServiceClient.getMember(userId).getMemberAuthority();
        return memberAuthority.equals(MemberAuthority.ROLE_ADMIN);
    }
}
