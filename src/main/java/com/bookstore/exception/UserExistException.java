package com.bookstore.exception;

import lombok.Getter;

@Getter
public class UserExistException extends RuntimeException {
    private String messageId;
    private String debugId;

    public UserExistException(String message, String messageId, String debugId) {
        super(message);
        this.messageId = messageId;
        this.debugId = debugId;
    }

}
