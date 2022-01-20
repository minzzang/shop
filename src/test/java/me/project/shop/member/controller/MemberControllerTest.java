package me.project.shop.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.project.shop.common.exception.BusinessMessage;
import me.project.shop.member.controller.dto.MemberCreateDto;
import me.project.shop.member.controller.dto.MemberLoginDto;
import me.project.shop.member.entity.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.transaction.Transactional;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @Transactional
    public void signUp() throws Exception {
        // given
        MemberCreateDto dto = MemberCreateDto.builder()
                .email("mj123@gmail.com")
                .password("mj")
                .city("성남")
                .zipcode("고")
                .street("양지")
                .build();

        String value = objectMapper.writeValueAsString(dto);
        // when

        ResultActions result = mvc.perform(post("/members")
                .contentType(APPLICATION_JSON)
                .content(value));
        // then
        result.andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void signUpWithDuplicateEmail() throws Exception {
        // given
        MemberCreateDto dto = MemberCreateDto.builder()
                .email("mj1117@gmail.com")
                .password("mj")
                .city("성남")
                .zipcode("고")
                .street("양지")
                .build();

        memberRepository.save(dto.toEntity());
        String value = objectMapper.writeValueAsString(dto);
        // when
        ResultActions result = mvc.perform(post("/members")
                .contentType(APPLICATION_JSON)
                .content(value));
        // then
        result.andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value(BusinessMessage.DUPLICATE_EMAILS.getMessage()));
    }

    @Test
    public void login() throws Exception {
        // given
        MemberLoginDto dto = MemberLoginDto.builder()
                .email("mj@gmail.com")
                .password("mj")
                .build();

        String value = objectMapper.writeValueAsString(dto);
        // when
        ResultActions result = mvc.perform(post("/members/login")
                .contentType(APPLICATION_JSON)
                .content(value));
        // then
    }

}