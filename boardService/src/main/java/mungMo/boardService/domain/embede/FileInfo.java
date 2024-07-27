package mungMo.boardService.domain.embede;

import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.util.Map;

@Embeddable
@Getter
public class FileInfo {
    private String original_name;
    private String mask_name;
    private String file_path;
    private String file_type;

    public FileInfo() {
    }

    public FileInfo(String original_name, String mask_name, String file_path, String file_type) {
        this.original_name = original_name;
        this.mask_name = mask_name;
        this.file_path = file_path;
        this.file_type = file_type;
    }
}
