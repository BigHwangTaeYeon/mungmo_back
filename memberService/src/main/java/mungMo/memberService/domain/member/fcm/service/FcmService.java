package mungMo.memberService.domain.member.fcm.service;

import mungMo.memberService.domain.member.fcm.domain.FcmToken;
import mungMo.memberService.domain.member.fcm.repository.FcmRepository;
import org.springframework.stereotype.Service;

@Service
public class FcmService {
    private final FcmRepository fcmRepository;

    public FcmService(FcmRepository fcmRepository) {
        this.fcmRepository = fcmRepository;
    }

    public FcmToken findToken(Long id) {
        return fcmRepository.findById(id).orElseThrow();
    }
}
