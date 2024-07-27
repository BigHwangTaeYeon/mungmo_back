package mungmo.mungmoChat.domain.room.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import mungmo.mungmoChat.otherDomain.member.dto.MemberDTO;

import java.time.LocalDateTime;

@Getter
@Setter
public class MeetupRoomDTO {
    private Long roomId;

    private String title;

    private LocalDateTime meetingDate;

    private int participantsNum;

    private MemberDTO owner;

    public MeetupRoomDTO() {
    }

    @Builder
    public MeetupRoomDTO(Long roomId, String title, LocalDateTime meetingDate, int participantsNum, MemberDTO owner) {
        this.roomId = roomId;
        this.title = title;
        this.meetingDate = meetingDate;
        this.participantsNum = participantsNum;
        this.owner = owner;
    }
}
