package mungMo.memberService.domain.friend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import mungMo.memberService.domain.friend.dto.FriendDTO;
import mungMo.memberService.domain.member.entity.MemberEntity;

import java.util.Optional;

@Entity
@Table(name = "friends")
public class FriendEntity {
    @Id @GeneratedValue
    @Column(name = "friends_id")
    @Getter
    private Long id;

    @ManyToOne
    @JoinColumn(name = "from_friend_id", nullable = false)
    private MemberEntity memberFrom;

    @ManyToOne
    @JoinColumn(name = "to_friend_id", nullable = false)
    private MemberEntity memberTo;

    @Column(nullable = false)
    private boolean accept;

    public void accept() {
        this.accept = true;
    }

    public FriendEntity() {
    }

    public static FriendEntity Of(MemberEntity memberFrom, MemberEntity memberTo) {
        return new FriendEntity(memberFrom, memberTo);
    }

    public static FriendEntity Of(MemberEntity memberFrom, MemberEntity memberTo, boolean accept) {
        return new FriendEntity(memberFrom, memberTo, accept);
    }

    private FriendEntity(MemberEntity memberFrom, MemberEntity memberTo) {
        this.memberFrom = memberFrom;
        this.memberTo = memberTo;
    }

    private FriendEntity(MemberEntity memberFrom, MemberEntity memberTo, boolean accept) {
        this.memberFrom = memberFrom;
        this.memberTo = memberTo;
        this.accept = accept;
    }

    public static FriendDTO changeToDTO(FriendEntity entity) {
        return FriendDTO.builder()
                .friend_id(entity.memberTo.getId())
                .accept(entity.accept)
                .build();
    }

}
