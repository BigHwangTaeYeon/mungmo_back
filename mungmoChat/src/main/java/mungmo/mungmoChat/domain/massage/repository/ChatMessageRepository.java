package mungmo.mungmoChat.domain.massage.repository;

import mungmo.mungmoChat.domain.massage.domain.ChatMessage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatMessageRepository extends MongoRepository<ChatMessage, Long> {
}
