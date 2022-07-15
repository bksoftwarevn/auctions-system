package com.bksoftwarevn.auction.exception.handler;

import com.bksoftwarevn.auction.exception.AucException;
import com.bksoftwarevn.auction.exception.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class ApiExceptionHandler {

    private final MessageService messageService;

    @ExceptionHandler(AucException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleAucException(AucException ex) {
        log.error("AucException detail:", ex);
        return ResponseEntity.ok(messageService.buildApiErrorResponse(ex));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleException(Exception ex) {
        log.error("Exception detail:", ex);
        return ResponseEntity.ok(messageService.buildDefaultErrorResponse(ex));
    }

}
