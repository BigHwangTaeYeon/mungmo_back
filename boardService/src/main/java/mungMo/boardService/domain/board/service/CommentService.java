package mungMo.boardService.domain.board.service;

import mungMo.boardService.com.response.exception.NotFoundException;
import mungMo.boardService.domain.board.entity.CommentEntity;
import mungMo.boardService.domain.board.repository.BoardRepository;
import mungMo.boardService.domain.board.repository.CommentRepository;
import mungMo.boardService.otherDomain.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    public CommentService(CommentRepository commentRepository, MemberRepository memberRepository, BoardRepository boardRepository) {
        this.commentRepository = commentRepository;
        this.memberRepository = memberRepository;
        this.boardRepository = boardRepository;
    }

    public void commentResister(String comment, Long boardId, Long userId) {
        commentRepository.save(CommentEntity.of(
                    comment,
                    boardRepository.findById(boardId).orElseThrow(NotFoundException::new),
                    memberRepository.findById(userId).orElseThrow(NotFoundException::new)
        ));
    }

    public void commentDelete(Long userId, long commentId) {
        commentRepository.findById(commentId)
                .filter(entity -> Objects.equals(entity.getWriter().getId(), userId))
                .ifPresent(commentRepository::delete);
    }

    public void commentModify(Long userId, long commentId, String comment) {
        commentRepository.findById(commentId)
                .filter(entity -> Objects.equals(entity.getWriter().getId(), userId))
                .ifPresent(commentEntity -> commentEntity.modify(comment));
    }
}
