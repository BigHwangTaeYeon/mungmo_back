package mungmo.mungmoChat.domain.room.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import mungmo.mungmoChat.com.config.ResponseMessage;
import mungmo.mungmoChat.com.exception.NotFoundException;
import mungmo.mungmoChat.domain.room.dto.MeetupRoomDTO;
import mungmo.mungmoChat.domain.room.service.MeetupRoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
@Slf4j
public class MeetupRoomController {

    private final MeetupRoomService chatRoomService;

    public MeetupRoomController(MeetupRoomService chatRoomService) {
        this.chatRoomService = chatRoomService;
    }

    /**
     * 방 리스트
     * @return
     */
    @GetMapping(value = "/meetupList")
    public ResponseEntity<?> meetupList(){
        return ResponseEntity.ok(new Result<>(chatRoomService.meetupList()));
    }

    /**
     * 방 정보
     * @param roomId
     * @return
     * @throws NotFoundException
     */
    @GetMapping(value = "/meetupInfo/{id}")
    public ResponseEntity<?> meetupList(@PathVariable Long roomId) throws NotFoundException {
        return ResponseEntity.ok(new Result<>(chatRoomService.meetupInfo(roomId)));
    }

    /**
     * 사용자가 참여중인 방 리스트
     * @param request
     * @return Long roomId
     */
    @GetMapping(value = "/participantList")
    public ResponseEntity<?> participantList(HttpServletRequest request){
        return ResponseEntity.ok(
                new Result<>(chatRoomService.meetupListByUser(Long.parseLong(request.getHeader("userId"))))
        );
    }

    /**
     * 방 생성
     * @param chatRoom
     * @return
     */
    @PostMapping(value = "/createMeetup")
    public ResponseEntity<?> createMeetup(HttpServletRequest request, @RequestBody MeetupRoomDTO chatRoom){
        chatRoomService.createMeetup(chatRoom, Long.parseLong(request.getHeader("userId")));
        return ResponseEntity.ok(ResponseMessage.OK.getMessage());
    }

    /**
     * 방 파괴
     * @param chatRoom
     * @return
     */
    @DeleteMapping(value = "/deleteMeetup")
    public ResponseEntity<?> deleteMeetup(@RequestBody MeetupRoomDTO chatRoom){
        chatRoomService.deleteMeetup(chatRoom);
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
