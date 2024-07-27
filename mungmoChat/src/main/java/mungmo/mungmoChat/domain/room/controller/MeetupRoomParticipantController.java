package mungmo.mungmoChat.domain.room.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import mungmo.mungmoChat.com.config.ResponseMessage;
import mungmo.mungmoChat.domain.room.dto.MeetupRoomParticipantDTO;
import mungmo.mungmoChat.domain.room.service.MeetupRoomParticipantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
@Slf4j
public class MeetupRoomParticipantController {
    private final MeetupRoomParticipantService participantService;

    public MeetupRoomParticipantController(MeetupRoomParticipantService participantService) {
        this.participantService = participantService;
    }

    /**
     * 번개 방에 참여중인 회원 리스트
     * @param roomId
     * @return Long userId
     */
    @GetMapping(value = "/participantListByMeetup/{id}")
    public ResponseEntity<?> participantListByMeetup(@PathVariable("id") Long roomId){
        return ResponseEntity.ok(new Result<>(participantService.participantListByRoom(roomId)));
    }

    /**
     * for adminService
     * 채팅중이지 않은 사람
     * @param chatRoom
     * @return
     */
    @GetMapping(value = "/chatNonParticipants/{chatRoomId}")
    public ResponseEntity<?> chatNonParticipants(@PathVariable(name = "chatRoomId") Long chatRoomId){
        return ResponseEntity.ok(participantService.chatNonParticipants(chatRoomId));
    }

    /**
     * 번개 참여
     * @param participantDTO
     * @return
     */
    @PostMapping(value = "/joinMeetup")
    public ResponseEntity<?> joinMeetup(@RequestBody MeetupRoomParticipantDTO participantDTO){
        participantService.joinMeetup(participantDTO);
        return ResponseEntity.ok(ResponseMessage.OK.getMessage());
    }

    /**
     * 번개 미참여
     * @param participantDTO
     * @return
     */
    @DeleteMapping(value = "/exitMeetup")
    public ResponseEntity<?> exitMeetup(@RequestBody MeetupRoomParticipantDTO participantDTO){
        participantService.exitMeetup(participantDTO);
        return ResponseEntity.ok(ResponseMessage.OK.getMessage());
    }

    @Getter
    public static class Result<T> {
        private final T data;
        public Result(T data) {
            this.data = data;
        }
    }
    
}
