package mungMo.boardService.domain.board.service;

import mungMo.boardService.com.response.exception.FileUploadException;
import mungMo.boardService.com.response.exception.NotFoundException;
import mungMo.boardService.com.util.Upload;
import mungMo.boardService.domain.board.dto.BoardDTO;
import mungMo.boardService.domain.board.dto.DogBoardDTO;
import mungMo.boardService.domain.board.dto.PageDTO;
import mungMo.boardService.domain.board.entity.BoardEntity;
import mungMo.boardService.domain.board.repository.BoardRepository;
import mungMo.boardService.otherDomain.member.service.MemberService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.data.domain.Sort.Direction.ASC;

@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private final MemberService memberService;

    @Value("${api.upload.dir.board}")
    private String uploadDir;

    public BoardService(BoardRepository boardRepository, MemberService memberService) {
        this.boardRepository = boardRepository;
        this.memberService = memberService;
    }

    public List<DogBoardDTO> boardList(PageDTO pageDTO) {
        return boardRepository.findAllBySubject(pageDTO.getSubject(), PageRequest.of(pageDTO.getPage(), pageDTO.getSize(), Sort.by(ASC, "createDate")))
                .getContent()
                .stream()
                .map(
                        entity -> DogBoardDTO.builder()
                                .board(entity.changeToDTO())
                                .dogInfo(entity.changToDogInfoDTO())
                                .build()
                )
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public DogBoardDTO boardOne(long id) {
        return boardRepository.findById(id)
                .map(
                        entity -> DogBoardDTO.builder()
                                .board(entity.changeToDTO())
                                .dogInfo(entity.changToDogInfoDTO())
                                .build()
                )
                .orElseThrow(NotFoundException::new);
    }

    public void boardResister(BoardDTO dto, Long userId, MultipartFile fileInfo) throws FileUploadException {

        Optional<MultipartFile> file = Optional.ofNullable(fileInfo);

        if(file.isPresent()) {
            boardRepository.save(BoardEntity.of(
                    dto.setFile(new Upload(uploadDir, file.get()).uploadImage()),
                    memberService.findEntityById(userId)
            ));
        } else {
            boardRepository.save(BoardEntity.of(
                    dto, memberService.findEntityById(userId)
            ));
        }
    }

    public void boardDelete(Long userId, long boardId) {
        boardRepository.findById(boardId)
                .filter(entity -> Objects.equals(entity.getWriter().getId(), userId))
                .ifPresent(boardRepository::delete);
    }

    public void boardModify(Long userId, long boardId, BoardDTO dto) {
        boardRepository.findById(boardId)
                .filter(entity -> Objects.equals(entity.getWriter().getId(), userId))
                .ifPresent(board -> board.modify(dto));
    }
}
