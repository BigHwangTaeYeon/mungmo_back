package mungmo.mungmoChat.otherDomain.member.service;

import mungmo.mungmoChat.otherDomain.member.entity.MemberEntity;
import mungmo.mungmoChat.otherDomain.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional(readOnly = true)
    public MemberEntity findMemberById(Long id) {
        return memberRepository.findById(id).orElseThrow(IllegalAccessError::new);
    }
}
