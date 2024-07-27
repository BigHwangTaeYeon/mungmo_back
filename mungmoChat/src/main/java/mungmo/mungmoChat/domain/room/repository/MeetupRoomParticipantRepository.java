package mungmo.mungmoChat.domain.room.repository;

import mungmo.mungmoChat.domain.room.entity.MeetupRoomParticipant;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeetupRoomParticipantRepository extends MongoRepository<MeetupRoomParticipant, Long> {
    MeetupRoomParticipant findByMemberIdAndChatRoomId(Long memberId, Long chatRoomId);

    List<MeetupRoomParticipant> findByMemberId(Long userId);

    List<MeetupRoomParticipant> findByChatRoomId(Long chatRoomId);

    void deleteByChatRoomId(Long roomId);

    List<MeetupRoomParticipant> findByChatRoomIdAndJoinChat(Long chatRoomId, boolean joinChat);
}
