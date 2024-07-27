package mungmo.mungmoChat.domain.massage.domain;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import mungmo.mungmoChat.domain.massage.dto.Message;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Document(collection = "chat_message")
public class ChatMessage {

    @Transient
    public static final String SEQUENCE_NAME = "chat_message_sequence";

    @Id @Setter
    private Long id;

    @Indexed
    private Long chatRoomId;

    private Long senderId;

    private String content;

    private LocalDateTime createdAt;

    private int readCount;

    // 소모임, 번개
    private ChatType chatType; // 채팅 타입 필드 추가('TEXT', 'IMAGE')

    private String imageName;

    private String imageUrl;

    public ChatMessage() {
    }

    @Builder
    public ChatMessage(String id, Long chatRoomId, Long senderId, String content, LocalDateTime createdAt, int readCount, ChatType chatType, String imageName, String imageUrl) {
        this.chatRoomId = chatRoomId;
        this.senderId = senderId;
        this.content = content;
        this.createdAt = createdAt;
        this.readCount = readCount;
        this.chatType = chatType;
        this.imageName = imageName;
        this.imageUrl = imageUrl;
    }

    public static ChatMessage from(Message message) {
        return ChatMessage.builder()
                .chatRoomId(message.getChatRoomId())
                .senderId(message.getSenderId())
                .content(message.getContent())
                .createdAt(LocalDateTime.now())
                .readCount(message.getReadCount())
                .chatType(message.getChatType())
                .imageName(message.getImageName())
                .imageUrl(message.getImageUrl())
                .build();
    }
}