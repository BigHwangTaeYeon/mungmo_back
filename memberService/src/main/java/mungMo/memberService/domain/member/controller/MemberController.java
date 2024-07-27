package mungMo.memberService.domain.member.controller;

import mungMo.memberService.domain.member.oauth.jwt.AuthTokens;
import mungMo.memberService.domain.member.oauth.param.KakaoLoginParams;
import mungMo.memberService.domain.member.oauth.param.NaverLoginParams;
import mungMo.memberService.domain.member.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
public class MemberController {

    private final MemberService oAuthLoginService;

    public MemberController(MemberService oAuthLoginService) {
        this.oAuthLoginService = oAuthLoginService;
    }

    @GetMapping("/kakao")
    public ResponseEntity<AuthTokens> loginKakao(@RequestParam(value = "code", required = false) String code) {
        return ResponseEntity.ok(oAuthLoginService.login(new KakaoLoginParams(code)));
    }

    @GetMapping("/naver")
    public ResponseEntity<AuthTokens> loginNaver(@RequestParam(value = "code", required = false) String code) {
        return ResponseEntity.ok(oAuthLoginService.login(new NaverLoginParams(code)));
    }

}
