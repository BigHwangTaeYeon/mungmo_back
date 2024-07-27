package mungmo.mungmoChat.domain.massage.service;

import mungmo.mungmoChat.domain.massage.domain.ChatMessage;
import mungmo.mungmoChat.domain.massage.dto.Message;
import mungmo.mungmoChat.domain.massage.repository.ChatMessageRepository;
import org.springframework.stereotype.Service;

@Service
public class ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;

    public ChatMessageService(ChatMessageRepository chatMessageRepository) {
        this.chatMessageRepository = chatMessageRepository;
    }

    public void saveMessage(Message message) {
        chatMessageRepository.save(ChatMessage.from(message));
    }
}
