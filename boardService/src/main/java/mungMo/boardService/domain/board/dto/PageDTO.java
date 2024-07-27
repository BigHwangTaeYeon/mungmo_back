package mungMo.boardService.domain.board.dto;

import lombok.Getter;
import lombok.Setter;
import mungMo.boardService.domain.board.entity.Subject;

@Getter @Setter
public class PageDTO {
    private Subject subject;
    private int page;
    private int size;

    public PageDTO(Subject subject, int page, int size) {
        this.subject = subject;
        this.page = page;
        this.size = size;
    }
}
