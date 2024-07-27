package mungmo.adminService.admin.domain.publicCode.repository;

import mungmo.adminService.admin.domain.publicCode.domain.PublicCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublicCodeRepository extends JpaRepository<PublicCodeEntity, Long> {
    List<PublicCodeEntity> findByCodeTypeAndUseYN(String type, boolean useYN);
}
