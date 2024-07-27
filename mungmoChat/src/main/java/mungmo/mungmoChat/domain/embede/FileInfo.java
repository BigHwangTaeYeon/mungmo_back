package mungmo.mungmoChat.domain.embede;

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

    public FileInfo(String original, String mask, String path, String type) {
        original_name = original;
        mask_name = mask;
        file_path = path;
        file_type = type;
    }

    public FileInfo(Map<String, String> uploadFileInfo, String file_type) {
        this.original_name = uploadFileInfo.get("original");
        this.mask_name = uploadFileInfo.get("masking");
        this.file_path = uploadFileInfo.get("path");
        this.file_type = file_type;
    }

    public FileInfo() {

    }
}
