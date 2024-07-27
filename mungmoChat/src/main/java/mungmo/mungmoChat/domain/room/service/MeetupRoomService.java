package mungmo.mungmoChat.domain.room.service;

import mungmo.mungmoChat.com.exception.NotFoundException;
import mungmo.mungmoChat.domain.room.entity.MeetupRoom;
import mungmo.mungmoChat.domain.room.repository.MeetupRoomRepository;
import mungmo.mungmoChat.domain.room.dto.MeetupRoomDTO;
import mungmo.mungmoChat.otherDomain.member.service.MemberService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MeetupRoomService {
    private final MemberService memberService;
    private final MeetupRoomParticipantService participantService;
    private final MeetupRoomRepository roomRepository;

    public MeetupRoomService(MemberService memberService, MeetupRoomParticipantService participantService, MeetupRoomRepository roomRepository) {
        this.memberService = memberService;
        this.participantService = participantService;
        this.roomRepository = roomRepository;
    }

    public List<MeetupRoomDTO> meetupList() {
        return roomRepository.findAll()
                .stream()
                .map(MeetupRoom::changeToDTO)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public MeetupRoomDTO meetupInfo(Long roomId) throws NotFoundException {
        return roomRepository.findById(roomId)
                .map(MeetupRoom::changeToDTO)
                .orElseThrow(NotFoundException::new);
    }

    public void createMeetup(MeetupRoomDTO chatRoom, long id) {
        MeetupRoom room = MeetupRoom.of(chatRoom, memberService.findMemberById(id));
        roomRepository.save(room);
    }

    public void deleteMeetup(MeetupRoomDTO chatRoom) {
        // 참여자들 모두 제거
        participantService.deleteAllParticipant(chatRoom.getRoomId());
        // 번개 방 삭제
        roomRepository.deleteById(chatRoom.getRoomId());
    }

    public List<MeetupRoomDTO> meetupListByUser(long userId) {
        return participantService.participantListById(userId)
                .stream()
                .map(participantDTO ->
                        roomRepository.findById(participantDTO.getChatRoomId())
                                .map(MeetupRoom::changeToDTO)
                                .orElseThrow()
                )
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
