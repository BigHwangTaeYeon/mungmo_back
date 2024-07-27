package mungMo.memberService.domain.member.service;

import mungMo.memberService.com.config.ResponseMessage;
import mungMo.memberService.com.exception.FileUploadException;
import mungMo.memberService.com.exception.PreconditionFailedException;
import mungMo.memberService.com.exception.UnauthorizedException;
import mungMo.memberService.com.exception.ValidationException;
import mungMo.memberService.com.util.Upload;
import mungMo.memberService.domain.embede.FileInfo;
import mungMo.memberService.domain.member.dto.DogImgDTO;
import mungMo.memberService.domain.member.dto.MemberDTO;
import mungMo.memberService.domain.member.dto.MemberIdAndDogNameDTO;
import mungMo.memberService.domain.member.repository.MemberRepository;
import mungMo.memberService.domain.member.entity.MemberEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
public class MemberApiService {

    private final MemberRepository memberRepository;

    @Value("${api.upload.dir.dog}")
    private String uploadDir;

    public MemberApiService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional(readOnly = true)
    public MemberEntity findEntityById(Long id) {
        return memberRepository.findById(id).orElseThrow();
    }

    @Transactional(readOnly = true)
    public MemberEntity findEntityByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    @Transactional(readOnly = true)
    public MemberDTO infoById(Long id) throws PreconditionFailedException {
        return memberRepository.findById(id)
                .map(MemberEntity::changeToDTO)
                .orElseThrow(() -> new PreconditionFailedException(ResponseMessage.PRECONDITIONFAILED.getMessage()));
    }

    @Transactional(readOnly = true)
    public DogImgDTO dogImg(Long id) throws PreconditionFailedException {
        return memberRepository.findById(id)
                .map(MemberEntity::dogImgDTO)
                .orElseThrow(() -> new PreconditionFailedException(ResponseMessage.PRECONDITIONFAILED.getMessage()));
    }

    @Transactional
    public void registerNickname(String dogName, Long id) throws ValidationException {
        memberRepository.findById(id).ifPresent(entity -> entity.changeDogName(dogName));
    }

    @Transactional
    public void updateDogImg(long id, MultipartFile file) {
        Optional.of(file).ifPresent(img -> memberRepository.findById(id).ifPresent(entity -> {
            try {
                entity.fileInfoInstance(new FileInfo(new Upload(uploadDir, img).uploadImage(), "img"));
            } catch (FileUploadException e) {
                throw new RuntimeException(e);
            }
        }));
    }

    @Transactional
    public MemberIdAndDogNameDTO dogName(long id) throws UnauthorizedException {
        return memberRepository.findById(id).map(MemberEntity::changeToDogDTO).orElseThrow(()->new UnauthorizedException("token error"));
    }

    @Transactional
    public void dogLike(Long id, String like) {
        memberRepository.findById(id).ifPresent(entity -> entity.changeDogLike(like));
    }

    @Transactional
    public void registerFcm(String fcmToken, Long id) {
        memberRepository.findById(id)
                .ifPresent(entity -> entity.getFcmToken().changeToken(fcmToken));
    }

    @Transactional
    public void comeFromReport(String reason, Long id) {
        MemberEntity member = memberRepository.findById(id).orElseThrow();
        member.comeFromReport(reason);
    }

    @Transactional
    public void comeFromReview(int point, Long id) {
        MemberEntity member = memberRepository.findById(id).orElseThrow();
        member.comeFromReview(point);
    }

    @Transactional
    public void comeFromNoShow(Long id) {
        MemberEntity member = memberRepository.findById(id).orElseThrow();
        member.comeFromNoShow();
    }
}
