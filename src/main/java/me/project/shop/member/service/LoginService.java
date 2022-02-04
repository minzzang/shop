package me.project.shop.member.service;

import me.project.shop.member.entity.Member;

public interface LoginService {

    void login(Member member);

    void logout();
}
