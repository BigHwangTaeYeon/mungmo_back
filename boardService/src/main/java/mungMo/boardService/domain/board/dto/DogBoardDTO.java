package mungMo.boardService.domain.board.dto;

import lombok.Builder;

@Builder
public class DogBoardDTO {
    private DogInfoDTO dogInfo;
    private BoardDTO board;
}
