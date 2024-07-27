package mungMo.memberService.domain.town.service;

import mungMo.memberService.domain.member.entity.MemberEntity;
import mungMo.memberService.domain.member.service.MemberApiService;
import mungMo.memberService.domain.town.entity.TownEntity;
import mungMo.memberService.domain.town.repository.TownRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TownService {
    private final MemberApiService memberService;
    private final TownRepository townRepository;

    public TownService(MemberApiService memberService, TownRepository townRepository) {
        this.memberService = memberService;
        this.townRepository = townRepository;
    }

    @Transactional
    public void register(String area, Long id) {
        memberService.findEntityById(id).getTown().certified(area);
    }

    @Transactional(readOnly = true)
    public void cancelCertification(Long id) {
        townRepository.findById(id).ifPresent(TownEntity::expired);
    }

    @Transactional
    public void saveMemberEntity(MemberEntity member) {
        townRepository.save(new TownEntity(member));
    }
}
