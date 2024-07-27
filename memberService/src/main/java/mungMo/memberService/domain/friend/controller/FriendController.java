package mungMo.memberService.domain.friend.controller;


import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import mungMo.memberService.com.config.ResponseMessage;
import mungMo.memberService.domain.friend.service.FriendService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
public class FriendController {
    private final FriendService friendService;

    public FriendController(FriendService friendService) {
        this.friendService = friendService;
    }

    /**
     * 친구 신청
     * @param request
     * @param friendId
     * @return
     */
    @PostMapping("/applyFriend/{id}")
    public ResponseEntity<?> applyFriend(HttpServletRequest request, @PathVariable("id") Long friendId) {
        friendService.apply(Long.parseLong(request.getHeader("userId")), friendId);
        return ResponseEntity.ok(ResponseMessage.valueOfCode("Ok").getMessage());
    }

    /**
     * 친구 수락
     * @param request
     * @param friendId
     * @return
     */
    @PatchMapping("/acceptFriend/{id}")
    public ResponseEntity<?> acceptFriend(HttpServletRequest request, @PathVariable("id") Long friendId) {
        friendService.accept(Long.parseLong(request.getHeader("userId")), friendId);
        return ResponseEntity.ok(ResponseMessage.valueOfCode("Ok").getMessage());
    }

    /**
     * 친구 삭제
     * @param request
     * @param friendId
     * @return
     */
    @DeleteMapping("/deleteFriend/{id}")
    public ResponseEntity<?> deleteFriend(HttpServletRequest request, @PathVariable("id") Long friendId) {
        friendService.delete(Long.parseLong(request.getHeader("userId")), friendId);
        return ResponseEntity.ok(ResponseMessage.valueOfCode("Ok").getMessage());
    }

    /**
     * 친구 리스트
     * @param request
     * @return
     */
    @GetMapping("/listFriend")
    public ResponseEntity<?> listFriend(HttpServletRequest request) {
        return ResponseEntity.ok(new Result(friendService.friendList(Long.parseLong(request.getHeader("userId")))));
    }

    @Getter
    private static class Result<T> {
        private final T data;

        public Result(T data) {
            this.data = data;
        }
    }
}
