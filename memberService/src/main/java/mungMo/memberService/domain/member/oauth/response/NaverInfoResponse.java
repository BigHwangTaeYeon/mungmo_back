package mungMo.memberService.domain.member.oauth.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import mungMo.memberService.domain.member.entity.SocialRoute;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class NaverInfoResponse implements OAuthInfoResponse {

    @JsonProperty("response")
    private Response response;

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Response {
        private String email;
        private String gender;
        private String age;
    }

    @Override
    public String getEmail() {
        return response.email;
    }

    @Override
    public String getGender() {
        return response.gender;
    }

    @Override
    public String getAgeRange() {
        return response.age;
    }

    @Override
    public SocialRoute getOAuthProvider() {
        return SocialRoute.NAVER;
    }
}