package mungMo.memberService.domain.member.oauth.param;

import mungMo.memberService.domain.member.entity.SocialRoute;
import org.springframework.util.MultiValueMap;

public interface OAuthLoginParams {
    SocialRoute oAuthProvider();
    MultiValueMap<String, String> makeBody();
}
