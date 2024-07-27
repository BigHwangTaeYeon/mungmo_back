package mungmo.mungmoChat.otherDomain.member.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FcmTokenDTO {
    private String fcmToken;

    public FcmTokenDTO() {
    }

    public FcmTokenDTO(String fcmToken) {
        this.fcmToken = fcmToken;
    }
}
