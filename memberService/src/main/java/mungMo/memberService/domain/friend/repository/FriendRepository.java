package mungMo.memberService.domain.friend.repository;

import mungMo.memberService.domain.friend.entity.FriendEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendRepository extends JpaRepository<FriendEntity, Long> {
    Optional<FriendEntity> findByMemberFromIdAndMemberToId(Long memberFromId, Long memberToId);

    @Query(value = "select\n" +
            "    fe1_0.`friends_id`,\n" +
            "    fe1_0.`accept`,\n" +
            "    fe1_0.`from_friend_id`,\n" +
            "    fe1_0.`to_friend_id` \n" +
            "from\n" +
            "    `friends` fe1_0\n" +
            "    , `friends` fe1_1\n" +
            "where\n" +
            "    (fe1_0.`from_friend_id`= :id And fe1_0.accept  =true\n" +
            "    and fe1_1.`to_friend_id`= :id And fe1_1.accept  =true)\n" +
            "    or (fe1_0.`from_friend_id`= :id And fe1_0.accept  =false\n" +
            "    and fe1_1.`to_friend_id`= :id And fe1_1.accept  =true)\n" +
            "    or (fe1_0.`from_friend_id`= :id And fe1_0.accept  =true\n" +
            "    and fe1_1.`to_friend_id`= :id And fe1_1.accept  =false)", nativeQuery = true)
    List<FriendEntity> findFriendList(@Param("id") Long id);

}
