package mungMo.memberService.domain.member.entity.function;

import mungMo.memberService.domain.member.entity.MemberEntity;
import org.springframework.util.ObjectUtils;

public class MannerTemperatureFunctionPolicyV1 implements MannerTemperatureFunction{
    private final MemberEntity member;

    public MannerTemperatureFunctionPolicyV1(MemberEntity member) {
        this.member = member;
    }

    @Override
    public void comeFromReport(String problem) {
        switch (problem) {
            case "gender" -> member.setMannerTemperature(member.getMannerTemperature() -4);
            case "dog" -> member.setMannerTemperature(member.getMannerTemperature() -2);
            default -> throw new IllegalArgumentException();
        };
    }

    @Override
    public void comeFromReview(int point) {
        if(point < 1 || point > 5 || ObjectUtils.isEmpty(point)) throw new IllegalArgumentException();
        member.setMannerTemperature(member.getMannerTemperature() +Math.round((float) point / 2));
    }

    @Override
    public void comeFromNoShow() {
        member.setMannerTemperature(member.getMannerTemperature() -5);
    }
}
