package mungmo.mungmoChat.otherDomain.member.repository;

import mungmo.mungmoChat.otherDomain.member.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
}
