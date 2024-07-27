package mungMo.memberService.domain.member.api;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import mungMo.memberService.com.config.ResponseMessage;
import mungMo.memberService.com.exception.PreconditionFailedException;
import mungMo.memberService.com.exception.UnauthorizedException;
import mungMo.memberService.com.exception.ValidationException;
import mungMo.memberService.domain.member.service.MemberApiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/v1")
public class MemberApiController {
    private final MemberApiService memberApiService;

    public MemberApiController(MemberApiService memberApiService) {
        this.memberApiService = memberApiService;
    }

    /*
     * 토큰으로 정보 가져오기
     */
    @GetMapping("/memberInfo")
    public ResponseEntity<?> myInfo(HttpServletRequest request) throws PreconditionFailedException {
        return ResponseEntity.ok(
                new Result<>(memberApiService.infoById(Long.parseLong(request.getHeader("userId"))))
        );
    }

    /*
     * id로 정보 가져오기
     */
    @GetMapping("/feignClient/memberInfo/{id}")
    public ResponseEntity<?> yourInfo(@PathVariable("id") Long id) throws PreconditionFailedException {
        return ResponseEntity.ok(
                memberApiService.infoById(id)
        );
    }

    /*
     * id로 정보 가져오기
     */
    @GetMapping("/dogImg")
    public ResponseEntity<?> dogImg(HttpServletRequest request) throws PreconditionFailedException {
        return ResponseEntity.ok(
                new Result<>(memberApiService.dogImg(Long.parseLong(request.getHeader("userId"))))
        );
    }

    /**
     *
     * @param request
     * @return dogName, id
     * @throws UnauthorizedException
     */
    @GetMapping("/dogName")
    public ResponseEntity<?> dogName(HttpServletRequest request) throws UnauthorizedException {
        return ResponseEntity.ok(
                new Result<>(memberApiService.dogName(Long.parseLong(request.getHeader("userId"))))
        );
    }

    /**
     * 강아지 정보 - 좋아요 등록
     * @param request
     * @param like
     * @return
     */
    @PatchMapping("/dogLike")
    public ResponseEntity<?> dogLike(HttpServletRequest request, @RequestParam("like") String like) {
        memberApiService.dogLike(Long.parseLong(request.getHeader("userId")), like);
        return ResponseEntity.ok(ResponseMessage.valueOfCode("Ok").getMessage());
    }

    /*
     * 닉네임 등록
     */
    @PatchMapping("/registerNickname")
    public ResponseEntity<?> regiNickname(HttpServletRequest request, @RequestParam("nickName") String nickName) throws ValidationException {
        memberApiService.registerNickname(nickName, Long.parseLong(request.getHeader("userId")));
        return ResponseEntity.ok(ResponseMessage.valueOfCode("Ok").getMessage());
    }

    /*
     * 강아지 사진 업로드
     */
    @PostMapping("/updateDogImg")
    public ResponseEntity<?> updateDogImg(HttpServletRequest request, MultipartFile file){
        memberApiService.updateDogImg(Long.parseLong(request.getHeader("userId")), file);
        return ResponseEntity.ok(ResponseMessage.valueOfCode("Ok").getMessage());
    }

    /*
     * Firebase Cloud Messaging
     */
    @PatchMapping("/registerFcm")
    public ResponseEntity<?> registerFcm(HttpServletRequest request, @RequestParam("fcmToken") String fcmToken) {
        memberApiService.registerFcm(fcmToken, Long.parseLong(request.getHeader("userId")));
        return ResponseEntity.ok(ResponseMessage.valueOfCode("Ok").getMessage());
    }

    /*
     * MannerTemperature come from reason
     */
    @PatchMapping("/comeFromReport")
    public ResponseEntity<?> comeFromReport(HttpServletRequest request, @RequestParam("reason") String reason) {
        memberApiService.comeFromReport(reason, Long.parseLong(request.getHeader("userId")));
        return ResponseEntity.ok(ResponseMessage.valueOfCode("Ok").getMessage());
    }

    /*
     * MannerTemperature come from point
     */
    @PatchMapping("/comeFromReview")
    public ResponseEntity<?> comeFromReview(HttpServletRequest request, @RequestParam("point") int point) {
        memberApiService.comeFromReview(point, Long.parseLong(request.getHeader("userId")));
        return ResponseEntity.ok(ResponseMessage.valueOfCode("Ok").getMessage());
    }

    /*
     * MannerTemperature come from no show
     */
    @PatchMapping("/comeFromNoShow")
    public ResponseEntity<?> comeFromNoShow(HttpServletRequest request) {
        memberApiService.comeFromNoShow(Long.parseLong(request.getHeader("userId")));
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