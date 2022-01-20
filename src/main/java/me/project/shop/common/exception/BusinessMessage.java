package me.project.shop.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RequiredArgsConstructor
@Getter
public enum BusinessMessage {

      NOT_LOGGED_IN(BAD_REQUEST, "로그인을 하지 않았습니다. 로그인을 먼저 해주세요.")
    , DUPLICATE_EMAILS(BAD_REQUEST, "이메일이 중복되었습니다. 다른 이메일을 입력 해주세요.")
    , EMAIL_MISMATCH(BAD_REQUEST, "이메일이 일치하지 않습니다. 올바른 이메일을 입력 해주세요.")
    , PASSWORD_MISMATCH(BAD_REQUEST, "비밀번호가 일치하지 않습니다. 올바른 비밀번호를 입력 해주세요.");

    private final HttpStatus httpStatus;
    private final String message;
}
