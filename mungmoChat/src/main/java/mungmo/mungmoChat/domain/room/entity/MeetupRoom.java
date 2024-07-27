package mungmo.mungmoChat.domain.room.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import mungmo.mungmoChat.domain.room.dto.MeetupRoomDTO;
import mungmo.mungmoChat.otherDomain.member.entity.MemberEntity;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "chat_room")
public class MeetupRoom {

    @jakarta.persistence.Id @GeneratedValue
    @Column(name = "chat_room_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(name = "meeting_date", nullable = false)
    private LocalDateTime meetingDate;

    // 참여자 수
    @Column(name = "participants_num", nullable = false)
    @ColumnDefault("30")
    private int participantsNum;

    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private MemberEntity owner;

    public MeetupRoom() {
    }

    @Builder
    public MeetupRoom(String title, LocalDateTime meetingDate, int participantsNum, LocalDateTime createDate, MemberEntity owner) {
        this.title = title;
        this.meetingDate = meetingDate;
        this.participantsNum = participantsNum;
        this.createDate = createDate;
        this.owner = owner;
    }

    private MeetupRoom(MeetupRoomDTO chatRoom, MemberEntity member) {
        this.title = chatRoom.getTitle();
        this.meetingDate = chatRoom.getMeetingDate();
        this.participantsNum = chatRoom.getParticipantsNum();
        this.createDate = LocalDateTime.now();
        this.owner = member;
    }

    public static MeetupRoom of(MeetupRoomDTO chatRoom, MemberEntity member) {
        return new MeetupRoom(chatRoom, member);
    }

    public MeetupRoomDTO changeToDTO() {
        return MeetupRoomDTO.builder()
                .roomId(id)
                .title(title)
                .participantsNum(participantsNum)
                .meetingDate(meetingDate)
                .owner(owner.changeToDTO())
                .build();
    }
}
