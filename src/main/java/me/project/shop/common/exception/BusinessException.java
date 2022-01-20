package me.project.shop.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BusinessException extends RuntimeException {

    private HttpStatus httpStatus;

    public BusinessException(BusinessMessage message) {
        super(message.getMessage());
        httpStatus = message.getHttpStatus();
    }
}
