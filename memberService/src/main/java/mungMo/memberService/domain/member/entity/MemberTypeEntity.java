package mungMo.memberService.domain.member.entity;

import jakarta.persistence.*;
import lombok.Getter;
import mungMo.memberService.domain.member.dto.MemberTypeDTO;
import mungMo.memberService.otherDomain.publicCode.entity.PublicCodeEntity;

import java.time.LocalDateTime;

@Entity
@Table(name = "member_type")
public class MemberTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_type_id")
    private Long id;

    private boolean use;
    private LocalDateTime update_date;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private MemberEntity member;

    @Getter
    @OneToOne
    private PublicCodeEntity publicCode;

    public void useY() {
        this.use = true;
    }

    public void useN() {
        this.use = false;
    }

    public MemberTypeEntity() {
    }

    public MemberTypeEntity(PublicCodeEntity publicCode, MemberEntity member) {
        this.publicCode = publicCode;
        this.use = false;
        this.member = member;
        this.update_date = LocalDateTime.now();
    }

    public MemberTypeDTO changeToDTO() {
        return MemberTypeDTO.builder()
                .type(publicCode.getCodeType())
                .code(publicCode.getCode())
                .codeName(publicCode.getCodeName())
                .use(use)
                .build();
    }
}
