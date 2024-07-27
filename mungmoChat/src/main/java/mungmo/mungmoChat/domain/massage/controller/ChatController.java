package mungmo.mungmoChat.domain.massage.controller;

import lombok.extern.slf4j.Slf4j;
import mungmo.mungmoChat.domain.massage.dto.Message;
import mungmo.mungmoChat.domain.massage.service.ChatService;
import mungmo.mungmoChat.otherDomain.Notification.dto.Notification;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
@Slf4j
public class ChatController {

    private final ChatService chatService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    public ChatController(ChatService chatService, SimpMessagingTemplate simpMessagingTemplate) {
        this.chatService = chatService;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @MessageMapping(value = "/meetup")
    public void test(@RequestBody Message message){
        chatService.meetupMessageSend(message);
        chatService.meetupNotification(Notification.from(message));

        simpMessagingTemplate.convertAndSend("/sub/meetup/" + message.getChatRoomId(), message);
    }
}
