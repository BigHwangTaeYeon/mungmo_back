package mungMo.memberService.domain.member.service;

import mungMo.memberService.com.exception.ValidationException;
import mungMo.memberService.domain.member.entity.MemberEntity;
import mungMo.memberService.domain.member.oauth.client.RequestOAuthInfoService;
import mungMo.memberService.domain.member.oauth.jwt.AuthTokens;
import mungMo.memberService.domain.member.oauth.jwt.AuthTokensGenerator;
import mungMo.memberService.domain.member.oauth.param.OAuthLoginParams;
import mungMo.memberService.domain.member.oauth.response.OAuthInfoResponse;
import mungMo.memberService.domain.member.repository.MemberRepository;
import mungMo.memberService.domain.town.service.TownService;
import mungMo.memberService.otherDomain.publicCode.entity.PublicCodeEntity;
import mungMo.memberService.otherDomain.publicCode.repository.PublicCodeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class MemberService {
    private final PublicCodeRepository publicCodeRepository;
    private final AuthTokensGenerator authTokensGenerator;
    private final RequestOAuthInfoService requestOAuthInfoService;
    private final MemberRepository memberRepository;

    private final TownService townService;
    private final MemberTypeService memberTypeService;

    public MemberService(PublicCodeRepository publicCodeRepository, AuthTokensGenerator authTokensGenerator, RequestOAuthInfoService requestOAuthInfoService, MemberRepository memberRepository, TownService townService, MemberTypeService memberTypeService) {
        this.publicCodeRepository = publicCodeRepository;
        this.authTokensGenerator = authTokensGenerator;
        this.requestOAuthInfoService = requestOAuthInfoService;
        this.memberRepository = memberRepository;
        this.townService = townService;
        this.memberTypeService = memberTypeService;
    }

    @Transactional
    public AuthTokens login(OAuthLoginParams params) {
        OAuthInfoResponse oAuthInfoResponse = requestOAuthInfoService.request(params);
        Long memberId = findOrCreateMember(oAuthInfoResponse);

        // 최근 접속 기록 남기기
        memberRepository.findById(memberId).ifPresent(MemberEntity::updateRecentDate);

        return authTokensGenerator.generate(memberId);
    }

    @Transactional(readOnly = true)
    private Long findOrCreateMember(OAuthInfoResponse oAuthInfoResponse) {
        return Optional.ofNullable(memberRepository.findByEmail(oAuthInfoResponse.getEmail()))
                        .map(MemberEntity::getId)
                        .orElseGet(()-> {
                            try {
                                return newMember(oAuthInfoResponse);
                            } catch (ValidationException e) {
                                throw new RuntimeException(e);
                            }
                        });
    }

    @Transactional
    private Long newMember(OAuthInfoResponse oAuthInfoResponse) throws ValidationException {
        MemberEntity member = MemberEntity.builder()
                .email(oAuthInfoResponse.getEmail())
                .gender(oAuthInfoResponse.getGender())
                .ageRange(oAuthInfoResponse.getAgeRange())
                .oAuthProvider(oAuthInfoResponse.getOAuthProvider())
                .build();
        memberRepository.save(member);

        townService.saveMemberEntity(member);

        for (PublicCodeEntity pcEntity : publicCodeRepository.findByCodeTypeAndUseYN("MBTP", true)) {
            memberTypeService.save(pcEntity, member);
        }
        for (PublicCodeEntity pcEntity : publicCodeRepository.findByCodeTypeAndUseYN("DGTP", true)) {
            memberTypeService.save(pcEntity, member);
        }

        return memberRepository.findByEmail(member.getEmail()).getId();
    }

}
