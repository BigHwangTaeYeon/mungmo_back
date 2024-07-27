package mungmo.adminService.admin.otherDomain.member.repository;

import mungmo.adminService.admin.otherDomain.member.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
}
