package mungmo.adminService.admin.domain.notification.controller;

import lombok.extern.slf4j.Slf4j;
import mungmo.adminService.admin.com.config.ResponseMessage;
import mungmo.adminService.admin.domain.notification.service.ChatNotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
@Slf4j
public class NotificationController {
    private final ChatNotificationService notificationService;

    public NotificationController(ChatNotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PatchMapping("/changeStatusReadTrue")
    public ResponseEntity<?> changeStatusReadTrue(Long roomId, Long userId) {
        notificationService.changeStatusReadTrue(userId, roomId);
        return ResponseEntity.ok(ResponseMessage.valueOfCode("Ok").getMessage());
    }
}
