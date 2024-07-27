package mungmo.adminService.admin.com.config.kafka.meetup;

import mungmo.adminService.admin.domain.notification.dto.Notification;
import mungmo.adminService.admin.domain.notification.service.ChatNotificationService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MeetupConsumer {
    private final ChatNotificationService chatNotificationService;

    public MeetupConsumer(ChatNotificationService chatNotificationService) {
        this.chatNotificationService = chatNotificationService;
    }

    @KafkaListener(topics = "${kafka.topic.notification.meetup}", groupId = "${kafka.consumer.id}", containerFactory = "kafkaNotificationContainerFactory")
    public void consumeNotification(Notification notification) {
        // 알림 저장
        chatNotificationService.saveNotification(notification);
        // 알림 전송
        chatNotificationService.sendPushNotification(notification);
    }
}
