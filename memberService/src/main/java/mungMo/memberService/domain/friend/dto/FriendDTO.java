package mungMo.memberService.domain.friend.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FriendDTO {
    private Long friend_id;

    private boolean accept;
}
