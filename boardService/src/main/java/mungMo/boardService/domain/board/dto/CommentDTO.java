package mungMo.boardService.domain.board.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class CommentDTO {
    private Long boardId;
    private String comment;
    private LocalDateTime create_date;
    private LocalDateTime update_date;
}
