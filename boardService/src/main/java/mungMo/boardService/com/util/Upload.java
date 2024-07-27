package mungMo.boardService.com.util;

import lombok.extern.slf4j.Slf4j;
import mungMo.boardService.com.response.exception.FileUploadException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Slf4j
public class Upload {

    private final String uploadDir;
    private final MultipartFile file;

    public Upload(String uploadDir, MultipartFile file) {
        this.uploadDir = uploadDir;
        this.file = file;
    }

    public Map<String,String> uploadImage() throws FileUploadException {
        Map<String,String> imgFile = new HashMap<>();
        String originalFileName = Objects.requireNonNull(file.getOriginalFilename());

        try{
            String makeDir = dir().get("makeDir");
            String dirPath = dir().get("dirPath");
            String maskingFileName = maskingFileName();
            String extension = originalFileName.substring(originalFileName.indexOf("."));

            File folder = new File(makeDir);

            if (!folder.exists()) {
                folder.mkdirs();
            }

            File destination = new File(makeDir + maskingFileName + extension);
            file.transferTo(destination);

            imgFile.put("path",dirPath);
            imgFile.put("original",originalFileName);
            imgFile.put("masking",maskingFileName + extension);
        }
        catch (Exception e){
            log.error("Exception [Err_Location] : {}", e.getStackTrace()[0]);
            throw new FileUploadException("파일 누락, 또는 다른 형식으로 요청하였습니다.");
        }
        return imgFile;
    }

    private Map<String, String> dir() {
        Map<String, String> dir = new HashMap<>();
        String makeDir = uploadDir + System.currentTimeMillis() + File.separator;
//         uploadDir을 사용하면 static처럼 뒤에 계속 날짜가 붙는다.
        dir.put("makeDir", makeDir);

//        dir.put("dirPath", makeDir.substring(makeDir.indexOf("/src")));   // prod 전용
        dir.put("dirPath", makeDir.substring(makeDir.indexOf("/MungMo")));  // local 전용
        return dir;
    }

    private String maskingFileName() {
        // 파일명 난수화 + 확장자
        return UUID.randomUUID().toString().substring(0, 5) + System.currentTimeMillis();
    }
}
