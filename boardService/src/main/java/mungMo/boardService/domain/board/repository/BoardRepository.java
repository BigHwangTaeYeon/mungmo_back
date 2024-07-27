package mungMo.boardService.domain.board.repository;

import mungMo.boardService.domain.board.entity.BoardEntity;
import mungMo.boardService.domain.board.entity.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Long> {

//    @EntityGraph
//    @Query(value = "select b from board b where b.subject = :subject", nativeQuery = true)
    Page<BoardEntity> findAllBySubject(Subject subject, Pageable pageable);

}
