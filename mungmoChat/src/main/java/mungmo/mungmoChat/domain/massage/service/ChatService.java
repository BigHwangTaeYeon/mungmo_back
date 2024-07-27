package mungmo.mungmoChat.domain.massage.service;

import mungmo.mungmoChat.domain.massage.dto.Message;
import mungmo.mungmoChat.otherDomain.Notification.dto.Notification;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ChatService {
    private final KafkaTemplate<String, Message> messageKafkaTemplate;
    private final KafkaTemplate<String, Notification> notificationKafkaTemplate;

    @Value("${kafka.topic.message.meetup}")
    private String meetupMessage;
    @Value("${kafka.topic.message.subgroup}")
    private String subgroupMessage;

    @Value("${kafka.topic.notification.meetup}")
    private String meetupNotification;
    @Value("${kafka.topic.notification.subgroup}")
    private String subgroupNotification;

    public ChatService(KafkaTemplate<String, Message> messageKafkaTemplate, KafkaTemplate<String, Notification> notificationKafkaTemplate) {
        this.messageKafkaTemplate = messageKafkaTemplate;
        this.notificationKafkaTemplate = notificationKafkaTemplate;
    }

    public void meetupMessageSend(Message message) {
        messageKafkaTemplate.send(meetupMessage, message);
    }

    public void meetupNotification(Notification notification) {
        notificationKafkaTemplate.send(meetupNotification, notification);
    }
}
