package mungmo.mungmoChat.domain.room.service;

import mungmo.mungmoChat.domain.room.entity.MeetupRoomParticipant;
import mungmo.mungmoChat.domain.room.dto.MeetupRoomParticipantDTO;
import mungmo.mungmoChat.domain.room.repository.MeetupRoomParticipantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MeetupRoomParticipantService {
    private final MeetupRoomParticipantRepository participantRepository;

    public MeetupRoomParticipantService(MeetupRoomParticipantRepository chatRoomParticipantRepository) {
        this.participantRepository = chatRoomParticipantRepository;
    }

    @Transactional(readOnly = true)
    public List<MeetupRoomParticipantDTO> participantListById(Long userId) {
        return participantRepository.findByMemberId(userId)
                .stream()
                .map(MeetupRoomParticipant::changeToDTO)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Transactional(readOnly = true)
    public List<MeetupRoomParticipantDTO> participantListByRoom(Long chatRoomId) {
        return participantRepository.findByChatRoomId(chatRoomId)
                .stream()
                .map(MeetupRoomParticipant::changeToDTO)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Transactional(readOnly = true)
    public MeetupRoomParticipantDTO findByUserIdAndChatRoomId(Long userId, Long chatRoomId) {
        return participantRepository.findByMemberIdAndChatRoomId(userId, chatRoomId)
                .changeToDTO();
    }

    @Transactional(readOnly = true)
    public List<MeetupRoomParticipantDTO> chatNonParticipants(Long chatRoomId) {
        return participantRepository.findByChatRoomIdAndJoinChat(chatRoomId, false)
                .stream()
                .map(MeetupRoomParticipant::changeToDTO)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Transactional
    public void joinChatMeetup(Long userId, Long chatRoomId) {
        participantRepository.findByMemberIdAndChatRoomId(userId, chatRoomId).joinChatMeetup();
    }

    @Transactional
    public void exitChatMeetup(Long userId, Long chatRoomId) {
        participantRepository.findByMemberIdAndChatRoomId(userId, chatRoomId).exitChatMeetup();
    }

    @Transactional
    public void joinMeetup(MeetupRoomParticipantDTO participantDTO) {
        participantRepository.insert(MeetupRoomParticipant.fromJoinMeetup(participantDTO));
    }

    @Transactional
    public void exitMeetup(MeetupRoomParticipantDTO participantDTO) {
        MeetupRoomParticipant participant = participantRepository.findByMemberIdAndChatRoomId(participantDTO.getMemberId(), participantDTO.getChatRoomId());
        participantRepository.delete(participant);
    }

    @Transactional
    public void deleteAllParticipant(Long roomId) {
        participantRepository.deleteByChatRoomId(roomId);
    }

}
