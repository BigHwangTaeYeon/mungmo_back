package mungMo.boardService.domain.board.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import mungMo.boardService.com.response.ResponseMessage;
import mungMo.boardService.com.response.exception.FileUploadException;
import mungMo.boardService.domain.board.dto.BoardDTO;
import mungMo.boardService.domain.board.dto.PageDTO;
import mungMo.boardService.domain.board.service.BoardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/v1")
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    /**
     * @return board list(paging), (작성자 dog, nickname, id)
     */
    @GetMapping("boardList")
    public ResponseEntity<?> boardList(@RequestBody PageDTO pageDTO) {
        return ResponseEntity.ok(boardService.boardList(pageDTO));
    }

    @GetMapping("board/{id}")
    public ResponseEntity<?> board(@PathVariable("id") long id) {
        return ResponseEntity.ok(boardService.boardOne(id));
    }

    @PostMapping("auth/boardRegister")
    public ResponseEntity<?> boardResister(HttpServletRequest request, BoardDTO dto, MultipartFile file) throws FileUploadException {
        boardService.boardResister(dto, Long.valueOf(request.getHeader("userId")), file);
        return ResponseEntity.ok(ResponseMessage.valueOfCode("Ok").getMessage());
    }

    /**
     * DELETE
     * @param <T>
     */
    @DeleteMapping("auth/boardDelete/{id}")
    public ResponseEntity<?> boardDelete(HttpServletRequest request, @PathVariable("id") long boardId) {
        boardService.boardDelete(Long.valueOf(request.getHeader("userId")), boardId);
        return ResponseEntity.ok(ResponseMessage.valueOfCode("Ok").getMessage());
    }

    /**
     * MODIFY
     * @param <T>
     */
    @PatchMapping("auth/boardModify/{id}")
    public ResponseEntity<?> boardModify(HttpServletRequest request, @PathVariable("id") long boardId, BoardDTO dto) {
        boardService.boardModify(Long.valueOf(request.getHeader("userId")), boardId, dto);
        return ResponseEntity.ok(ResponseMessage.valueOfCode("Ok").getMessage());
    }

    @Getter
    public static class data<T> {
        private final T data;
        public data(T data) {
            this.data = data;
        }
    }
}
