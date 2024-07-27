package mungMo.memberService.domain.member.oauth.client;

import mungMo.memberService.domain.member.entity.SocialRoute;
import mungMo.memberService.domain.member.oauth.param.OAuthLoginParams;
import mungMo.memberService.domain.member.oauth.response.OAuthInfoResponse;
import net.minidev.json.parser.ParseException;

import java.io.IOException;
import java.net.MalformedURLException;

public interface OAuthApiClient {
    SocialRoute oAuthProvider();
    String requestAccessToken(OAuthLoginParams params);
    OAuthInfoResponse requestOauthInfo(String accessToken);
}