package mungmo.adminService.admin.domain.notification.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter @Setter
@ToString
public class Notification implements Serializable {

    private Long chatRoomId;
    private Long recipientId;
    private Long sendId;
    private String content;
    private String senderNick;
    private LocalDateTime createdDate;

    public Notification() {
    }

    public Notification(Long chatRoomId, Long recipientId, Long sendId, String content, String senderNick, LocalDateTime createdDate) {
        this.chatRoomId = chatRoomId;
        this.recipientId = recipientId;
        this.sendId = sendId;
        this.content = content;
        this.senderNick = senderNick;
        this.createdDate = createdDate;
    }
}