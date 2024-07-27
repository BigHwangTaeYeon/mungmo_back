package mungmo.mungmoChat.com.config.websocket.function;

import mungmo.mungmoChat.com.exception.NotFoundException;
import mungmo.mungmoChat.domain.room.service.MeetupRoomService;
import mungmo.mungmoChat.otherDomain.member.service.MemberService;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;

import java.util.Optional;

public abstract class ChatFunctionByStatus {

    protected final MemberService memberService;
    protected final MeetupRoomService meetupRoomService;

    public ChatFunctionByStatus(MemberService memberService, MeetupRoomService meetupRoomService) {
        this.memberService = memberService;
        this.meetupRoomService = meetupRoomService;
    }

    public abstract void validation(StompHeaderAccessor accessor) throws NotFoundException;
    public abstract void join(StompHeaderAccessor accessor);
    public abstract void exit(StompHeaderAccessor accessor);

    protected Long getUserId(StompHeaderAccessor accessor) {
        return Optional.of(accessor)
                .map(acc -> Long.parseLong(acc.getNativeHeader("userId").get(0)))
                .orElseThrow(IllegalAccessError::new);
    }

    protected Long getRoomNum(StompHeaderAccessor accessor) {
        return Optional.ofNullable(accessor)
                .map(acc -> Long.parseLong(acc.getNativeHeader("roomNum").get(0)))
                .orElseThrow(IllegalAccessError::new);
    }
}
