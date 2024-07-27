package mungmo.adminService.admin.otherDomain.room.service;

import mungmo.adminService.admin.otherDomain.room.dto.MeetupRoomParticipantDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name="chat-service", url = "http://localhost:8000")
public interface MeetupRoomParticipantServiceClient {
    @GetMapping("/chat-service/v1/chatNonParticipants/{id}")
    List<MeetupRoomParticipantDTO> chatNonParticipants(@PathVariable(name = "chatRoomId") Long chatRoomId);
}
