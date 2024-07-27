package mungmo.mungmoChat.otherDomain.Notification.service;

import mungmo.mungmoChat.otherDomain.Notification.repository.ChatNotificationRepository;
import org.springframework.stereotype.Service;

@Service
public class ChatNotificationService {
    private final ChatNotificationRepository chatNotificationRepository;

    public ChatNotificationService(ChatNotificationRepository chatNotificationRepository) {
        this.chatNotificationRepository = chatNotificationRepository;
    }
}
