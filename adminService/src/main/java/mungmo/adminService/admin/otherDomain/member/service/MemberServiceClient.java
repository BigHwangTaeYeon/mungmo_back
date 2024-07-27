package mungmo.adminService.admin.otherDomain.member.service;

import feign.Headers;
import mungmo.adminService.admin.otherDomain.member.entity.MemberEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="member-service", url = "http://localhost:8000")
public interface MemberServiceClient {
    @GetMapping(value = "/member-service/v1/feignClient/memberInfo/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    MemberEntity getMember(@PathVariable(name = "id") Long userId);
}
