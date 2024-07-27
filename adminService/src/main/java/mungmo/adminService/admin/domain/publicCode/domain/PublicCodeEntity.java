package mungmo.adminService.admin.domain.publicCode.domain;

import jakarta.persistence.*;
import lombok.Getter;
import mungmo.adminService.admin.domain.publicCode.dto.PublicCodeDTO;

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
}
