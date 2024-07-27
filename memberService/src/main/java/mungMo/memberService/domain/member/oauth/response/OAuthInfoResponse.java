package mungMo.memberService.domain.member.oauth.response;

import mungMo.memberService.domain.member.entity.SocialRoute;

public interface OAuthInfoResponse {
    String getEmail();
    String getGender();
    String getAgeRange();
    SocialRoute getOAuthProvider();
}