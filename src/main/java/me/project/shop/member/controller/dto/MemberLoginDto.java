package me.project.shop.member.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.project.shop.member.entity.Member;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberLoginDto {

    @Email(message = "올바른 이메일을 입력해주세요")
    @NotBlank(message = "이메일을 입력해주세요")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요")
    private String password;

    public Member toEntity() {
        return new Member(email, password);
    }
}
