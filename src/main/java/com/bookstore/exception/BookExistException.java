package com.bookstore.exception;

public class BookExistException extends RuntimeException{

    private String messageId;
    private String debugId;

    public BookExistException(String message, String messageId, String debugId) {
        super(message);
        this.messageId = messageId;
        this.debugId = debugId;
    }

    public String getMessageId() {
        return messageId;
    }

    public String getDebugId() {
        return debugId;
    }
}
