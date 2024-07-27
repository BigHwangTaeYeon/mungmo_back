package mungMo.memberService.domain.member.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Builder
public class MemberIdAndDogNameDTO {
    private Long id;
    private String dogName;
}
