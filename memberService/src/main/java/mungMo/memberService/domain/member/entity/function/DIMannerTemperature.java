package mungMo.memberService.domain.member.entity.function;

import mungMo.memberService.domain.member.entity.MemberEntity;

public class DIMannerTemperature {
    public MannerTemperatureFunction mtfFactory(MemberEntity member) {
        return new MannerTemperatureFunctionPolicyV1(member);
    }
}
