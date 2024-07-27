package mungMo.memberService.domain.member.repository;

import mungMo.memberService.domain.member.entity.MemberTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;

@Repository
public interface MemberTypeRepository extends JpaRepository<MemberTypeEntity, Long> {
    List<MemberTypeEntity> findByMemberId(Long memberId);

    LinkedList<MemberTypeEntity> findByMemberIdAndPublicCodeCodeType(Long id, String type);
}
