package com.orange.exceptions;

public class RuntimeCommonException extends RuntimeException {

    public RuntimeCommonException(String message) {
        super(message);
    }

    public RuntimeCommonException(String message, Throwable cause) {
        super(message, cause);
    }
}
