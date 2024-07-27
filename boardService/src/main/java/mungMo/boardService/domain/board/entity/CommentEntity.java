package mungMo.boardService.domain.board.entity;

import jakarta.persistence.*;
import lombok.Getter;
import mungMo.boardService.otherDomain.member.entity.MemberEntity;

import java.time.LocalDateTime;

@Entity
@Table(name = "board_comment")
public class CommentEntity {
    @Id @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    private String comment;

    @ManyToOne
    private BoardEntity board;

    @OneToOne
    @Getter
    private MemberEntity writer;

    private LocalDateTime create_date;
    private LocalDateTime update_date;

    public void modify(String comment) {
        this.comment = comment;
        this.update_date = LocalDateTime.now();
    }

    public CommentEntity(String comment, BoardEntity board, MemberEntity member) {
        this.comment = comment;
        this.board = board;
        this.writer = member;
        this.create_date = LocalDateTime.now();
    }

    public static CommentEntity of(String comment, BoardEntity board, MemberEntity member) {
        return new CommentEntity(comment, board, member);
    }
}
