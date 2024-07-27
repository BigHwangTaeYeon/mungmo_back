package mungMo.memberService.domain.report.dto;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import mungMo.memberService.domain.embede.FileInfo;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ReportDTO {
    private Long seq;

    private String title;
    private String content;

    private Long fromId;
    private Long toId;

    private useStatus status;

    private LocalDateTime createDate;

    private String original_name;
    private String mask_name;
    private String file_path;
    private String file_type;

    public enum useStatus {
        DID, DIDNOT
    }

    public ReportDTO setFromId(Long id){
        fromId = id;
        return this;
    }

}
