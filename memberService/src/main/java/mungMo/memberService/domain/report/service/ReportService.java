package mungMo.memberService.domain.report.service;

import jakarta.ws.rs.NotFoundException;
import mungMo.memberService.com.exception.FileUploadException;
import mungMo.memberService.com.util.Upload;
import mungMo.memberService.domain.embede.FileInfo;
import mungMo.memberService.domain.report.dto.ReportDTO;
import mungMo.memberService.domain.report.entity.ReportEntity;
import mungMo.memberService.domain.report.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReportService {

    private final ReportRepository reportRepository;

    @Value("${api.upload.dir.report}")
    private String uploadDir;

    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Transactional(readOnly = true)
    public List<ReportDTO> userReportList(long userId) {
        return reportRepository.findByFromMemberId(userId)
                .stream()
                .map(ReportEntity::changeToDTO)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Transactional(readOnly = true)
    public ReportDTO reportDetail(long id) {
        return reportRepository.findById(id)
                .map(ReportEntity::changeToDTO)
                .orElseThrow(()-> new NotFoundException("존재하지 않는 데이터입니다."));
    }

    @Transactional
    public void register(ReportDTO reportDTO, MultipartFile file) {
        Optional.of(file).ifPresent(img -> {
            try {
                reportRepository.save(
                        new ReportEntity(
                                new FileInfo(new Upload(uploadDir, img).uploadImage(), "img")
                                , reportDTO
                        )
                );
            } catch (FileUploadException e) {
                throw new RuntimeException(e);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
    }

}
