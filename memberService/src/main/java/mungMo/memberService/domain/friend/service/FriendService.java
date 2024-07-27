package mungMo.memberService.domain.friend.service;

import mungMo.memberService.domain.friend.dto.FriendDTO;
import mungMo.memberService.domain.friend.entity.FriendEntity;
import mungMo.memberService.domain.friend.repository.FriendRepository;
import mungMo.memberService.domain.member.repository.MemberRepository;
import mungMo.memberService.domain.member.service.MemberApiService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static mungMo.memberService.domain.friend.entity.FriendEntity.Of;

@Service
public class FriendService {
    private final MemberApiService memberApiService;
    private final FriendRepository friendRepository;

    public FriendService(MemberApiService memberApiService, FriendRepository friendRepository) {
        this.memberApiService = memberApiService;
        this.friendRepository = friendRepository;
    }

    @Transactional
    public void apply(Long fromFriendId, Long toFriendId) {
        if(friendRepository.findByMemberFromIdAndMemberToId(fromFriendId, toFriendId).isEmpty()) {
            friendRepository.save(
                    Of(
                            memberApiService.findEntityById(fromFriendId)
                            , memberApiService.findEntityById(toFriendId)
                            , true
                    )
            );
            friendRepository.save(
                    Of(
                            memberApiService.findEntityById(toFriendId)
                            , memberApiService.findEntityById(fromFriendId)
                            , false
                    )
            );
        }
    }

    @Transactional
    public void accept(Long fromFriendId, Long toFriendId) {
        friendRepository.findByMemberFromIdAndMemberToId(fromFriendId, toFriendId)
                .ifPresent(FriendEntity::accept);
    }

    @Transactional
    public void delete(Long fromFriendId, Long toFriendId) {
        // 내 친구 기록에서 친구 삭제
        friendRepository.findByMemberFromIdAndMemberToId(fromFriendId, toFriendId)
                .ifPresent(entity -> friendRepository.deleteById(entity.getId()));
        // 친구의 친구 기록에서 나 삭제
        friendRepository.findByMemberFromIdAndMemberToId(toFriendId, fromFriendId)
                .ifPresent(entity -> friendRepository.deleteById(entity.getId()));
    }

    @Transactional(readOnly = true)
    public List<FriendDTO> friendList(Long id) {
        return friendRepository.findFriendList(id)
                .stream()
                .map(FriendEntity::changeToDTO)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
