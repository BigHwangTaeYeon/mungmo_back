package mungmo.mungmoChat.domain.massage.domain.increment;

import mungmo.mungmoChat.com.config.mongo.increment.SequenceGeneratorService;
import mungmo.mungmoChat.domain.massage.domain.ChatMessage;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;

@Component
public class ChatMessageListener extends AbstractMongoEventListener<ChatMessage> {

    private final SequenceGeneratorService generatorService;

    public ChatMessageListener(SequenceGeneratorService generatorService) {
        this.generatorService = generatorService;
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<ChatMessage> event) {
        event.getSource().setId(generatorService.generateSequence(ChatMessage.SEQUENCE_NAME));
    }
}