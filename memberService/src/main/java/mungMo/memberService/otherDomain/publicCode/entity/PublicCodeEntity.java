package mungMo.memberService.otherDomain.publicCode.entity;

import jakarta.persistence.*;
import lombok.Getter;
import mungMo.memberService.domain.member.entity.MemberEntity;
import mungMo.memberService.domain.member.entity.MemberTypeEntity;
import mungMo.memberService.otherDomain.publicCode.dto.PublicCodeDTO;

@Entity
@Table(name = "public_code")
public class PublicCodeEntity {
    @Id @GeneratedValue
    @Column(name = "public_code_id")
    private Long id;

    @Getter
    @Column(name = "code_type")
    private String codeType;

    @Getter
    private int code;

    @Getter
    @Column(name = "code_name")
    private String codeName;

    @Column(name = "use_yn")
    private boolean useYN;

    public PublicCodeDTO changeToDTO() {
        return PublicCodeDTO.builder()
                .codeType(codeType)
                .code(code)
                .codeName(codeName)
                .build();
    }

    public MemberTypeEntity changeToMemberType(PublicCodeEntity publicCode, MemberEntity member) {
        return new MemberTypeEntity(publicCode, member);
    }
}
