package mungMo.memberService.domain.member.fcm.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
public class FcmToken {
    @Id @GeneratedValue
    @Column(name = "fcm_token_id")
    private Long id;

    @Getter
    @Column(name = "fcm_token")
    private String fcmToken;

    public void changeToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }
}
