package mungmo.mungmoChat.otherDomain.Notification.repository;

import mungmo.mungmoChat.otherDomain.Notification.entity.ChatNotification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatNotificationRepository extends JpaRepository<ChatNotification, Long> {
}
