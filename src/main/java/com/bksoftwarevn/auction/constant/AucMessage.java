package com.bksoftwarevn.auction.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum AucMessage {


    // HTTP REQUEST
    BAD_REQUEST("AUC-0000", "Bad Request", HttpStatus.BAD_REQUEST),
    FORBIDDEN("AUC-0001", "Forbidden", HttpStatus.FORBIDDEN),
    INTERNAL_SERVER_ERROR("AUC-0002", "Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR),
    UNKNOWN_ERROR("AUC-0003", "An unknown error occurred", HttpStatus.INTERNAL_SERVER_ERROR),
    REQUEST_TYPE_NOT_FOUND("AUC-0004", "Request type not found", HttpStatus.BAD_REQUEST),


    //USER
    USERNAME_NOT_FOUND("AUC-1000", "Username not found.", HttpStatus.UNAUTHORIZED),
    USERNAME_DISABLED("AUC-1001", "User was disabled.", HttpStatus.UNAUTHORIZED),
    USERNAME_ALREADY_USED("AUC-1002", "Username or email already existed.", HttpStatus.UNAUTHORIZED),
    REGISTER_USER_SUCCESS("AUC-1003", "Register user successful.", HttpStatus.ACCEPTED),
    REGISTER_USER_FAILED("AUC-1004", "Register user failed.", HttpStatus.NOT_ACCEPTABLE),
    ACTIVE_USER_FAILED("AUC-1005", "Active user failed.", HttpStatus.NOT_ACCEPTABLE),
    ACTIVE_USER_SUCCESS("AUC-1006", "Active user successful.", HttpStatus.ACCEPTED),
    RESET_PASSWORD_FAILED("AUC-1007", "Reset password user failed.", HttpStatus.NOT_ACCEPTABLE),
    RESET_PASSWORD_SUCCESS("AUC-1008", "Reset password user successful.", HttpStatus.ACCEPTED),
    GET_USER_SUCCESS("AUC-1009", "Get user successful.", HttpStatus.ACCEPTED),
    GET_USER_FAILED("AUC-1010", "Get user failed.", HttpStatus.ACCEPTED),
    UPDATE_USER_FAILED("AUC-1011", "Update user failed.", HttpStatus.NOT_ACCEPTABLE),
    UPDATE_USER_SUCCESS("AUC-1012", "Update user successful.", HttpStatus.ACCEPTED),

    //ROLE
    ROLE_NOT_FOUND("AUC-2000", "Role not found.", HttpStatus.BAD_REQUEST),
    PULL_ROLE_SUCCESS("AUC-2001", "Pull list role successful.", HttpStatus.ACCEPTED),
    PULL_ROLE_FAILED("AUC-2002", "Pull list role failed.", HttpStatus.ACCEPTED),
    CREATE_ROLE_SUCCESS("AUC-2003", "Create list role successful.", HttpStatus.ACCEPTED),
    CREATE_ROLE_FAILED("AUC-2004", "Create list role failed.", HttpStatus.NOT_ACCEPTABLE),
    UPDATE_ROLE_SUCCESS("AUC-2005", "Update list role successful.", HttpStatus.ACCEPTED),
    UPDATE_ROLE_FAILED("AUC-2006", "Update list role failed.", HttpStatus.NOT_ACCEPTABLE),


    //Token
    INVALID_JWT_TOKEN("AUC-3000", "Invalid JWT token.", HttpStatus.UNAUTHORIZED),
    SECRET_IS_NULL("AUC-3001", "SecretKey byte array cannot be null.", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED("AUC-3002", "Error: Unauthorized!", HttpStatus.UNAUTHORIZED),
    INVALID_CREDENTIALS("AUC-3003", "Invalid credentials!", HttpStatus.UNAUTHORIZED),
    AUTHEN_SUCCESS("AUC-3005", "Authenticate successful!", HttpStatus.UNAUTHORIZED),

    // Confirmation
    CONFIRMATION_NOT_FOUND("CF-4000", "Confirmation not found!", HttpStatus.BAD_REQUEST),
    CONFIRM_FAILED("CF-4001", "Confirm request failed.", HttpStatus.ACCEPTED),
    CONFIRM_SUCCESS("CF-4002", "Confirm request successful.", HttpStatus.ACCEPTED),
    CONFIRM_EXPIRED("CF-4003", "Confirm request expired.", HttpStatus.ACCEPTED),


    ;
    String code;
    String message;
    HttpStatus status;

    public static AucMessage getByCode(String code) {
        return Arrays.stream(AucMessage.values()).filter(tcbExceptionMessage -> tcbExceptionMessage.code.equalsIgnoreCase(code)).findFirst().orElse(null);
    }
}
