package me.project.shop.member.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.project.shop.member.entity.Address;
import me.project.shop.member.entity.Member;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Collections;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberCreateDto {

    @Email(message = "올바른 이메일을 입력해주세요")
    @NotBlank(message = "이메일을 입력해주세요")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요")
    private String password;

    private String city;

    private String zipcode;

    private String street;

    public Member toEntity() {
        Address address = new Address(city, zipcode, street);
        return new Member(email, Collections.singletonList(address), password);
    }

}
