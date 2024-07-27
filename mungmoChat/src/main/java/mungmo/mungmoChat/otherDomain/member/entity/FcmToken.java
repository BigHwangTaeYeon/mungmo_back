package mungmo.mungmoChat.otherDomain.member.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
public class FcmToken {
    @Id @GeneratedValue
    @Column(name = "fcm_token_id")
    private Long id;

    @Getter
    @Column(name = "fcm_token")
    private String fcmToken;
}
