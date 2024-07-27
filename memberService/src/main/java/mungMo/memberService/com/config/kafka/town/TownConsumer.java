package mungMo.memberService.com.config.kafka.town;

import mungMo.memberService.domain.town.dto.TownDTO;
import mungMo.memberService.domain.town.service.TownService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TownConsumer {
    private final TownService townService;

    public TownConsumer(TownService townService) {
        this.townService = townService;
    }

    @KafkaListener(topics = "${kafka.town.filter.topic}", groupId = "${kafka.town.consumer.id}", containerFactory = "kafkaTownContainerFactory")
    public void consumeMessage(TownDTO town) {
        // 인증 취소
        townService.cancelCertification(town.getTown_id());
    }
}
