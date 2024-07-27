package mungMo.memberService.domain.member.fcm.repository;

import mungMo.memberService.domain.member.fcm.domain.FcmToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FcmRepository extends JpaRepository<FcmToken, Long> {
}
