package mungmo.mungmoChat.domain.room.entity.increment;

import mungmo.mungmoChat.com.config.mongo.increment.SequenceGeneratorService;
import mungmo.mungmoChat.domain.room.entity.MeetupRoomParticipant;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

@Component
public class ParticipantMessageListener extends AbstractMongoEventListener<MeetupRoomParticipant> {

    private final SequenceGeneratorService generatorService;

    public ParticipantMessageListener(SequenceGeneratorService generatorService) {
        this.generatorService = generatorService;
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<MeetupRoomParticipant> event) {
        event.getSource().setId(generatorService.generateSequence(MeetupRoomParticipant.SEQUENCE_NAME));
    }
}