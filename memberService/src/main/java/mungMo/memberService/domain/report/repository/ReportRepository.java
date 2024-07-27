package mungMo.memberService.domain.report.repository;

import mungMo.memberService.domain.report.entity.ReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<ReportEntity, Long> {
    List<ReportEntity> findByFromMemberId(Long userId);

    ReportEntity findByFromMemberIdAndToMemberId(long fromMemberId, long toMemberId);
}
