package mungMo.boardService.otherDomain.member.service;

import mungMo.boardService.otherDomain.member.entity.MemberEntity;
import mungMo.boardService.otherDomain.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public MemberEntity findEntityById(Long id) {
        return memberRepository.findById(id).orElseThrow();
    }
}
