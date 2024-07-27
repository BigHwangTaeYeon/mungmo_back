package mungmo.mungmoChat.domain.massage.dto;

import lombok.*;
import mungmo.mungmoChat.domain.massage.domain.ChatType;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter @Setter
@ToString
public class Message implements Serializable {

    private Long chatRoomId;

    private Long senderId;

    private String senderNickName;

    private String content;

    private LocalDateTime createdAt;

    private int readCount;

    // 소모임, 번개
    private ChatType chatType;

    private String imageName; // 이미지 파일 이름
    private String imageUrl; // 이미지 URL

    public Message() {
    }
}
