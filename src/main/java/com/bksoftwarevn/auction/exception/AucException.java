package com.bksoftwarevn.auction.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class AucException extends RuntimeException {

    private String message;
    private String code;
    private Throwable cause;
    private boolean writableStackTrace;

    public AucException(String code, String message, Throwable cause, boolean writableStackTrace) {
        super(message);
        this.message = message;
        this.code = code;
        this.cause = cause;
        this.writableStackTrace = writableStackTrace;
    }

    public AucException(String code, Throwable cause, boolean writableStackTrace) {
        this.code = code;
        this.cause = cause;
        this.writableStackTrace = writableStackTrace;
    }

    public AucException(String code, String message, Throwable cause) {
        super(message);
        this.message = message;
        this.code = code;
        this.cause = cause;
    }

    public AucException(String code, String message) {
        super(message);
        this.message = message;
        this.code = code;
    }

}
