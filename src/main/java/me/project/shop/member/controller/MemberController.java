package me.project.shop.member.controller;

import lombok.RequiredArgsConstructor;
import me.project.shop.member.controller.dto.MemberCreateDto;
import me.project.shop.member.controller.dto.MemberLoginDto;
import me.project.shop.member.service.LoginService;
import me.project.shop.member.service.MemberService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    private final LoginService loginService;

    @PostMapping("/members")
    public void signUp(@RequestBody @Valid MemberCreateDto dto) {
        memberService.signUp(dto.toEntity());
    }

    @PostMapping("/members/login")
    public void login(@RequestBody @Valid MemberLoginDto dto) {
        loginService.login(dto.toEntity());
    }

    @PostMapping("/members/logout")
    public void logout() {
        loginService.logout();
    }

}
