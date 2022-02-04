package me.project.shop.member.service;

import lombok.RequiredArgsConstructor;
import me.project.shop.common.exception.BusinessException;
import me.project.shop.common.exception.BusinessMessage;
import me.project.shop.member.entity.Member;
import me.project.shop.member.entity.MemberRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
@RequiredArgsConstructor
public class SessionLoginService implements LoginService {

    private final RedisTemplate<String, String> redisTemplate;

    private final HttpSession httpSession;

    private final MemberRepository memberRepository;

    @Override
    public void login(Member member) {
        Member findMember = memberRepository.findByEmail(member.getEmail())
                .orElseThrow(() -> new BusinessException(BusinessMessage.EMAIL_MISMATCH));

        findMember.checkPassword(member.getPassword());

        redisTemplate.opsForValue().set(httpSession.getId(), member.getEmail());
    }

    @Override
    public void logout() {
        String deleted = redisTemplate.opsForValue().getAndDelete(httpSession.getId());
        if (deleted == null) throw new BusinessException(BusinessMessage.NOT_LOGGED_IN);
    }
}
