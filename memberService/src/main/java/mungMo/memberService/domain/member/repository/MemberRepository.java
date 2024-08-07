package mungMo.memberService.domain.member.repository;

import mungMo.memberService.domain.member.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    MemberEntity findByEmail(String email);
}
