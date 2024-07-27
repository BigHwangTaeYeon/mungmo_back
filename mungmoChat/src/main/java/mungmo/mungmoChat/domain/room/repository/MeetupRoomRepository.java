package mungmo.mungmoChat.domain.room.repository;

import mungmo.mungmoChat.domain.room.entity.MeetupRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetupRoomRepository extends JpaRepository<MeetupRoom, Long> {
}
