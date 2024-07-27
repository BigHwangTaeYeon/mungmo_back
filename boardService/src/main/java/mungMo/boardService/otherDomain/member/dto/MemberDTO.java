package mungMo.boardService.otherDomain.member.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MemberDTO {
    private Long memberId;
    private String email;
    private String dogName;
    private String dogLike;
    private String gender;
    private String ageRange;
    private int mannerTemperature;
    private boolean townCertificated;
}
