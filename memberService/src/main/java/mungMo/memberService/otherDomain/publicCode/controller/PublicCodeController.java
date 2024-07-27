package mungMo.memberService.otherDomain.publicCode.controller;

import lombok.Getter;
import mungMo.memberService.otherDomain.publicCode.service.PublicCodeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class PublicCodeController {

    private final PublicCodeService publicCodeService;

    public PublicCodeController(PublicCodeService publicCodeService) {
        this.publicCodeService = publicCodeService;
    }

    /**
     * 신고 종류
     * @return
     */
    @GetMapping("reportType")
    public ResponseEntity<?> reportType() {
        return ResponseEntity.ok(publicCodeService.reportType());
    }

    @Getter
    public static class data<T> {
        private final T data;
        public data(T data) {
            this.data = data;
        }
    }
}
