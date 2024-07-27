package mungMo.boardService.domain.board.entity;

import jakarta.persistence.*;
import lombok.Getter;
import mungMo.boardService.domain.board.dto.BoardDTO;
import mungMo.boardService.domain.embede.FileInfo;
import mungMo.boardService.domain.board.dto.DogInfoDTO;
import mungMo.boardService.otherDomain.member.entity.MemberEntity;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "board")
public class BoardEntity {
    @Id @GeneratedValue
    @Column(name = "board_id")
    private Long id;

    private String title;
    private String content;

    @Column(name = "create_date")
    private LocalDateTime createDate;
    @Column(name = "update_date")
    private LocalDateTime updateDate;

    /**
     *  FREE, FUN, DOG, REVIEW
     *  자유, 유머, 강아지자랑, 번개후기
     */
    private Subject subject;

//    @OneToMany(mappedBy = "board")
    @OneToMany
    @JoinColumn(name = "comment_id")
    private List<CommentEntity> boardComments;

    @OneToOne
    @Getter
    private MemberEntity writer;

    @Embedded
    private FileInfo fileInfo;

    public BoardEntity() {
    }

    private BoardEntity(BoardDTO boardDTO, MemberEntity member) {
        this.title = boardDTO.getTitle();
        this.content = boardDTO.getContent();
        this.subject = boardDTO.getSubject();
        this.createDate = LocalDateTime.now();
        if(Optional.ofNullable(boardDTO.getFile_path()).isPresent()) {
            fileInfo = new FileInfo(boardDTO.getOriginal_name(), boardDTO.getMask_name(), boardDTO.getFile_path(), boardDTO.getFile_type());
        } else {
            fileInfo = new FileInfo();
        }
        this.writer = member;
    }

    public static BoardEntity of(BoardDTO boardDTO, MemberEntity member) {
        return new BoardEntity(boardDTO, member);
    }

    public void modify(BoardDTO dto) {
        if(StringUtils.hasText(dto.getTitle())) this.title = dto.getTitle();
        if(StringUtils.hasText(dto.getContent())) this.content = dto.getContent();
        if(dto.getSubject() != null) this.subject = dto.getSubject();
        this.updateDate = LocalDateTime.now();
    }

    public DogInfoDTO changToDogInfoDTO() {
        return DogInfoDTO.builder()
                .id(writer.getId())
                .dogName(writer.getDogName())
                .file_path(writer.getFileInfo().getFile_path())
                .original_name(writer.getFileInfo().getOriginal_name())
                .mask_name(writer.getFileInfo().getMask_name())
                .file_type(writer.getFileInfo().getFile_type())
                .build();
    }

    public BoardDTO changeToDTO() {
        return Optional.ofNullable(fileInfo).isPresent()
                ? BoardDTO.builder()
                        .id(id)
                        .title(title)
                        .content(content)
                        .create_date(createDate)
                        .update_date(updateDate)
                        .subject(subject)
                        .original_name(fileInfo.getOriginal_name())
                        .mask_name(fileInfo.getMask_name())
                        .file_path(fileInfo.getFile_path())
                        .file_type(fileInfo.getFile_type())
                        .build()
                : BoardDTO.builder()
                        .id(id)
                        .title(title)
                        .content(content)
                        .create_date(createDate)
                        .update_date(updateDate)
                        .subject(subject)
                        .build();
    }
}
