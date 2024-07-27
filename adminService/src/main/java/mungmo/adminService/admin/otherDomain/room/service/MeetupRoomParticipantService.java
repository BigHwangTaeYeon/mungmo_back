package mungmo.adminService.admin.otherDomain.room.service;

import mungmo.adminService.admin.otherDomain.room.repository.MeetupRoomParticipantRepository;
import org.springframework.stereotype.Service;

@Service
public class MeetupRoomParticipantService {
    private final MeetupRoomParticipantRepository participantRepository;

    public MeetupRoomParticipantService(MeetupRoomParticipantRepository chatRoomParticipantRepository) {
        this.participantRepository = chatRoomParticipantRepository;
    }
}
