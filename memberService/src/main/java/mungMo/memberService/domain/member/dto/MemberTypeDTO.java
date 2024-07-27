package mungMo.memberService.domain.member.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MemberTypeDTO {
    private String type;
    private int code;
    private String codeName;
    private boolean use;
}
