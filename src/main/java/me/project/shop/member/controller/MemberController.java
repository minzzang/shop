package me.project.shop.member.controller;

import lombok.RequiredArgsConstructor;
import me.project.shop.common.annotation.LoginMember;
import me.project.shop.common.exception.BusinessException;
import me.project.shop.common.exception.BusinessMessage;
import me.project.shop.member.controller.dto.MemberCreateDto;
import me.project.shop.member.controller.dto.MemberLoginDto;
import me.project.shop.member.service.MemberService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    private final RedisTemplate<String, String> redisTemplate;

    @PostMapping("/members")
    public void signUp(@RequestBody @Valid MemberCreateDto dto) {
        memberService.signUp(dto.toEntity());
    }

    @PostMapping("/members/login")
    public void login(@RequestBody @Valid MemberLoginDto dto
                                        , HttpSession httpSession) {
        memberService.login(dto.toEntity());
        redisTemplate.opsForValue().set(httpSession.getId(), dto.getEmail());
    }

    @PostMapping("/members/logout")
    public void logout(HttpSession httpSession) {
        String deleted = redisTemplate.opsForValue().getAndDelete(httpSession.getId());
        if (deleted == null) throw new BusinessException(BusinessMessage.NOT_LOGGED_IN);
    }

    @PostMapping("/members/test")
    public void test(@LoginMember String email) {
        System.out.println(email);
    }

}
