package mungMo.memberService.domain.town.repository;

import mungMo.memberService.domain.town.entity.TownEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.lang.Long;

@Repository
public interface TownRepository extends JpaRepository<TownEntity, Long> {
    Optional<List<TownEntity>> findByCertificationDateBefore(LocalDateTime localDateTime);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE town t SET t.certification = false, area = null " +
            "WHERE t.certification = true " +
            "AND t.certification_date BETWEEN :startDate AND :endDate", nativeQuery = true)
    void bulkCertificationFalse(@Param("startDate") LocalDateTime start, @Param("endDate") LocalDateTime end);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE town t SET t.certification = false, area = null " +
            "WHERE t.certification = true " +
            "AND t.certification_date < :day ", nativeQuery = true)
    void bulkCertificationFalseToDay(@Param("day") LocalDateTime day);
}
