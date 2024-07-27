package mungmo.adminService.admin.domain.notification.repository;

import mungmo.adminService.admin.domain.notification.domain.ChatNotification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatNotificationRepository extends JpaRepository<ChatNotification, Long> {
    List<ChatNotification> findByRecipientIdAndChatRoomId(Long userId, Long roomNum);
}
