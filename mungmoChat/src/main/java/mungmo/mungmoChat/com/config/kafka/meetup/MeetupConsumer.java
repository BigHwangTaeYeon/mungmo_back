package mungmo.mungmoChat.com.config.kafka.meetup;

import mungmo.mungmoChat.domain.massage.dto.Message;
import mungmo.mungmoChat.domain.massage.service.ChatMessageService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MeetupConsumer {
    private final ChatMessageService messageService;

    public MeetupConsumer(ChatMessageService messageService) {
        this.messageService = messageService;
    }

    @KafkaListener(topics = "${kafka.topic.message.meetup}", groupId = "${kafka.consumer.id}", containerFactory = "kafkaChatContainerFactory")
    public void consumeMessage(Message message) {
        // 데이터베이스에 저장
        messageService.saveMessage(message);
    }
}
