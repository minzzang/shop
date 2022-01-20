package me.project.shop.member.service;

import lombok.RequiredArgsConstructor;
import me.project.shop.common.exception.BusinessException;
import me.project.shop.common.exception.BusinessMessage;
import me.project.shop.member.entity.Member;
import me.project.shop.member.entity.MemberRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public void signUp(Member member) {
        try {
            member.encryptPassword();
            memberRepository.save(member);
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException(BusinessMessage.DUPLICATE_EMAILS);
        }
    }

    public void login(Member loginRequestMember) {
        Member findMember = memberRepository.findByEmail(loginRequestMember.getEmail())
                .orElseThrow(() -> new BusinessException(BusinessMessage.EMAIL_MISMATCH));

        findMember.checkPassword(loginRequestMember.getPassword());
    }
}
