package mungmo.adminService.admin.otherDomain.room.entity;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import mungmo.adminService.admin.otherDomain.room.dto.MeetupRoomParticipantDTO;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.LocalDateTime;

@Getter
@Document(collection = "chat_room_participant")
public class MeetupRoomParticipant {

    @Transient
    public static final String SEQUENCE_NAME = "chat_room_participant_sequence";

    @Id @Setter
    private Long id;

    @Indexed
    private Long chatRoomId;

    @Indexed
    private Long memberId;

    private LocalDateTime joinedAt;

    private boolean joinChat;

    public MeetupRoomParticipant() {
    }

    @Builder
    private MeetupRoomParticipant(Long memberId, Long chatRoomId, LocalDateTime joinedAt, boolean joinChat) {
        this.memberId = memberId;
        this.chatRoomId = chatRoomId;
        this.joinedAt = joinedAt;
        this.joinChat = joinChat;
    }

    public MeetupRoomParticipantDTO changeToDTO() {
        return MeetupRoomParticipantDTO.builder()
                .chatRoomId(chatRoomId)
                .memberId(memberId)
                .joinedAt(joinedAt)
                .build();
    }
}