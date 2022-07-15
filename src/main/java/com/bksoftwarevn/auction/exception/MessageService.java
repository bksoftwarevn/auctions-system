package com.bksoftwarevn.auction.exception;

import java.util.Locale;

public interface MessageService {
    String mapping(String errorCode, String lang);

    String mapping(String errorCode, Locale locale);

    String mappingUTF8(String code, String lang);

    String mappingDefault(String lang);

    ApiErrorResponse buildApiErrorResponse(AucException ex);

    ApiErrorResponse buildDefaultErrorResponse(Exception ex);
}
