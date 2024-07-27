package mungMo.memberService.domain.report.controller;


import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import mungMo.memberService.com.config.ResponseMessage;
import mungMo.memberService.domain.report.dto.ReportDTO;
import mungMo.memberService.domain.report.service.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/v1")
public class ReportController {
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/reportList")
    public ResponseEntity<?> reportList(HttpServletRequest request) {
        return ResponseEntity.ok(
                new Result<>(reportService.userReportList(
                        Long.parseLong(request.getHeader("userId"))
                ))
        );
    }

    @GetMapping("/reportDetail/{id}")
    public ResponseEntity<?> reportDetail(@PathVariable("id") long id) {
        return ResponseEntity.ok(
                new Result<>(reportService.reportDetail(id))
        );
    }

    @PostMapping("/resisterReport")
    public ResponseEntity<?> resisterReport(HttpServletRequest request, ReportDTO reportDTO, MultipartFile file) {
        reportService.register(reportDTO.setFromId(Long.parseLong(request.getHeader("userId"))), file);
        return ResponseEntity.ok(ResponseMessage.valueOfCode("Ok").getMessage());
    }

    @Getter
    public static class Result<T> {
        private final T data;

        public Result(T data) {
            this.data = data;
        }
    }

}
