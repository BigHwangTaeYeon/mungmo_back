package mungmo.adminService.admin.otherDomain.room.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter @Builder
public class MeetupRoomParticipantDTO {
    private Long memberId;

    private Long chatRoomId;

    private LocalDateTime joinedAt;
}
