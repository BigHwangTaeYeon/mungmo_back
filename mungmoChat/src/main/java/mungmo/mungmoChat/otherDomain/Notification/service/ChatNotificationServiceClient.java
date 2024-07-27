package mungmo.mungmoChat.otherDomain.Notification.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="admin-service", url = "http://localhost:8000")
public interface ChatNotificationServiceClient {
    @PatchMapping("/admin-service/v1/changeStatusReadTrue")
    void changeStatusReadTrue(@RequestParam("roomId") Long roomId, @RequestParam("userId") Long userId);
}
