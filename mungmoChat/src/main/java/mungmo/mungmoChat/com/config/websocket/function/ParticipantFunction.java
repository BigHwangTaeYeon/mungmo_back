package mungmo.mungmoChat.com.config.websocket.function;

import mungmo.mungmoChat.com.exception.NotFoundException;
import mungmo.mungmoChat.domain.room.service.MeetupRoomParticipantService;
import mungmo.mungmoChat.domain.room.service.MeetupRoomService;
import mungmo.mungmoChat.otherDomain.Notification.service.ChatNotificationServiceClient;
import mungmo.mungmoChat.otherDomain.member.service.MemberService;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.util.ObjectUtils;

import java.util.Objects;
import java.util.Optional;

public class ParticipantFunction extends ChatFunctionByStatus {
    private final MeetupRoomParticipantService participantService;
    private final ChatNotificationServiceClient chatNotificationServiceClient;

    public ParticipantFunction(MemberService memberService, MeetupRoomService meetupRoomService, MeetupRoomParticipantService participantService, ChatNotificationServiceClient chatNotificationService) {
        super(memberService, meetupRoomService);
        this.participantService = participantService;
        this.chatNotificationServiceClient = chatNotificationService;
    }

    @Override
    public void validation(StompHeaderAccessor accessor) throws NotFoundException {
        Optional.of(accessor)
                .filter(acc -> Objects.requireNonNull(acc.getNativeHeader("userId")).isEmpty())
                .filter(acc -> Objects.requireNonNull(acc.getNativeHeader("roomNum")).isEmpty())
                .filter(ac ->
                        // 데이터가 존재하면 true
                        !ObjectUtils.isEmpty(participantService.findByUserIdAndChatRoomId(
                                Long.parseLong(ac.getNativeHeader("userId").get(0)),
                                Long.parseLong(ac.getNativeHeader("roomNum").get(0))
                        ))
                )
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public void join(StompHeaderAccessor accessor) {
        // 채팅 참여
        participantService.joinChatMeetup(getUserId(accessor), getRoomNum(accessor));
        // 채팅 읽음 처리
        chatNotificationServiceClient.changeStatusReadTrue(getUserId(accessor), getRoomNum(accessor));
    }

    @Override
    public void exit(StompHeaderAccessor accessor) {
        participantService.exitChatMeetup(getUserId(accessor), getRoomNum(accessor));
    }
}
