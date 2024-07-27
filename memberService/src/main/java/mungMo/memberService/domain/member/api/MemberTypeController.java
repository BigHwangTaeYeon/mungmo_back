package mungMo.memberService.domain.member.api;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import mungMo.memberService.com.config.ResponseMessage;
import mungMo.memberService.domain.member.service.MemberTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class MemberTypeController {
    private final MemberTypeService memberTypeService;

    public MemberTypeController(MemberTypeService memberTypeService) {
        this.memberTypeService = memberTypeService;
    }

    /**
     * 유저 모든 타입 가져오기
     * @param request
     * @return
     */
    @GetMapping("/userTypeList")
    public ResponseEntity<?> userTypeList(HttpServletRequest request) {
        return ResponseEntity.ok(
                new Result<>(memberTypeService.userTypeList(Long.parseLong(request.getHeader("userId"))))
        );
    }

    /**
     * 유저 타입 저장
     * @param request type(유저), code(내향, 외향)
     * @param code
     * @return
     */
    @PatchMapping("/userTypeResister")
    public ResponseEntity<?> userTypeResister(HttpServletRequest request, @RequestParam List<Integer> code) {
        memberTypeService.userTypeResister(Long.parseLong(request.getHeader("userId")), code);
        return ResponseEntity.ok(ResponseMessage.OK.getMessage());
    }

    /**
     * 강아지 타입 저장
     * @param request type(강아지), code(내향, 외향)
     * @param code
     * @return
     */
    @PatchMapping("/dogTypeResister")
    public ResponseEntity<?> dogTypeResister(HttpServletRequest request, @RequestParam List<Integer> code) {
        memberTypeService.dogTypeResister(Long.parseLong(request.getHeader("userId")), code);
        return ResponseEntity.ok(ResponseMessage.OK.getMessage());
    }

    @Getter
    public static class Result<T> {
        private final T data;
        public Result(T data) {
            this.data = data;
        }
    }

}