package mungmo.adminService.admin.otherDomain.room.repository;

import mungmo.adminService.admin.otherDomain.room.entity.MeetupRoomParticipant;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetupRoomParticipantRepository extends MongoRepository<MeetupRoomParticipant, Long> {
}
