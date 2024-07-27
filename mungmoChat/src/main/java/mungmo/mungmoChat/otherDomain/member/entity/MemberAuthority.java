package mungmo.mungmoChat.otherDomain.member.entity;

public enum MemberAuthority {
    // 추후, 회원가입 인증 절차 시작 시 인증 완료 전 게스트, 후 멤버
    ROLE_GUEST, ROLE_MEMBER, ROLE_ADMIN
}
