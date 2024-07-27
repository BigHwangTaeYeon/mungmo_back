package mungMo.memberService.domain.town.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class TownDTO {
    private Long town_id;
    private Long member_id;
    private String area;
    private boolean certification;
    private LocalDateTime certification_date;
}
