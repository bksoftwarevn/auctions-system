package com.bksoftwarevn.auction.exception.handler;

import com.bksoftwarevn.auction.constant.AucMessage;
import com.bksoftwarevn.auction.constant.Languages;
import com.bksoftwarevn.auction.exception.ApiErrorResponse;
import com.bksoftwarevn.auction.exception.AucException;
import com.bksoftwarevn.auction.exception.MessageService;
import com.bksoftwarevn.auction.util.ResourceMessageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.Locale;

@Slf4j
@RequiredArgsConstructor
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class MessageServiceHandler implements MessageService {
    @Override
    public String mapping(String code, String lang) {
        return ResourceMessageUtil.getMessage(code, lang);
    }

    @Override
    public String mapping(String code, Locale locale) {
        return ResourceMessageUtil.getMessage(code, locale);
    }

    @Override
    public String mappingUTF8(String code, String lang) {
        return ResourceMessageUtil.getUTF8Message(code, ResourceMessageUtil.getLocale(lang));
    }

    @Override
    public String mappingDefault(String lang) {
        return ResourceMessageUtil.getMessageDefault(lang);
    }

    @Override
    public ApiErrorResponse buildApiErrorResponse(AucException ex) {
        return ApiErrorResponse.builder()
                .errorCode(ex.getCode())
                .errorDesc(ex.getMessage())
                .httpStatus(AucMessage.getByCode(ex.getCode()).getStatus())
                .errorMessage(ApiErrorResponse.ErrorMessage.builder()
                        .vi(mapping(ex.getCode(), Languages.VI.getLanguage()))
                        .en(mapping(ex.getCode(), Languages.EN.getLanguage()))
                        .ja(mapping(ex.getCode(), Languages.JA.getLanguage()))
                        .build())
                .timestamp(LocalDateTime.now())
                .cause(ex.isWritableStackTrace() ? ex.getCause() : null)
                .build();
    }

    @Override
    public ApiErrorResponse buildDefaultErrorResponse(Exception ex) {
        return ApiErrorResponse.builder()
                .errorCode(AucMessage.INTERNAL_SERVER_ERROR.getCode())
                .errorDesc(ex.getMessage())
                .httpStatus(AucMessage.INTERNAL_SERVER_ERROR.getStatus())
                .errorMessage(ApiErrorResponse.ErrorMessage.builder()
                        .vi(mapping(AucMessage.INTERNAL_SERVER_ERROR.getCode(), Languages.VI.getLanguage()))
                        .en(mapping(AucMessage.INTERNAL_SERVER_ERROR.getCode(), Languages.EN.getLanguage()))
                        .ja(mapping(AucMessage.INTERNAL_SERVER_ERROR.getCode(), Languages.JA.getLanguage()))
                        .build())
                .timestamp(LocalDateTime.now())
                .build();
    }

}
