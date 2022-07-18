package com.bksoftwarevn.auction.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum AucMessage {


    // HTTP REQUEST
    BAD_REQUEST("HTTP-0000", "Bad Request", HttpStatus.BAD_REQUEST),
    FORBIDDEN("HTTP-0001", "Forbidden", HttpStatus.FORBIDDEN),
    INTERNAL_SERVER_ERROR("HTTP-0002", "Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR),
    UNKNOWN_ERROR("HTTP-0003", "An unknown error occurred", HttpStatus.INTERNAL_SERVER_ERROR),
    REQUEST_TYPE_NOT_FOUND("HTTP-0004", "Request type not found", HttpStatus.BAD_REQUEST),
    OPERATION_NOT_SUPPORT("HTTP-0005", "Operation not supported yet", HttpStatus.BAD_REQUEST),


    //USER
    USERNAME_NOT_FOUND("US-1000", "Username not found.", HttpStatus.UNAUTHORIZED),
    USERNAME_DISABLED("US-1001", "User was disabled.", HttpStatus.UNAUTHORIZED),
    USERNAME_ALREADY_USED("US-1002", "Username or email already existed.", HttpStatus.UNAUTHORIZED),
    REGISTER_USER_SUCCESS("US-1003", "Register user successful.", HttpStatus.ACCEPTED),
    REGISTER_USER_FAILED("US-1004", "Register user failed.", HttpStatus.NOT_ACCEPTABLE),
    ACTIVE_USER_FAILED("US-1005", "Active user failed.", HttpStatus.NOT_ACCEPTABLE),
    ACTIVE_USER_SUCCESS("US-1006", "Active user successful.", HttpStatus.ACCEPTED),
    RESET_PASSWORD_FAILED("US-1007", "Reset password user failed.", HttpStatus.NOT_ACCEPTABLE),
    RESET_PASSWORD_SUCCESS("US-1008", "Reset password user successful.", HttpStatus.ACCEPTED),
    GET_USER_SUCCESS("US-1009", "Get user successful.", HttpStatus.ACCEPTED),
    GET_USER_FAILED("US-1010", "Get user failed.", HttpStatus.ACCEPTED),
    UPDATE_USER_FAILED("US-1011", "Update user failed.", HttpStatus.NOT_ACCEPTABLE),
    UPDATE_USER_SUCCESS("US-1012", "Update user successful.", HttpStatus.ACCEPTED),

    //ROLE
    ROLE_NOT_FOUND("RL-2000", "Role not found.", HttpStatus.BAD_REQUEST),
    PULL_ROLE_SUCCESS("RL-2001", "Pull list role successful.", HttpStatus.ACCEPTED),
    PULL_ROLE_FAILED("RL-2002", "Pull list role failed.", HttpStatus.ACCEPTED),
    CREATE_ROLE_SUCCESS("RL-2003", "Create list role successful.", HttpStatus.ACCEPTED),
    CREATE_ROLE_FAILED("RL-2004", "Create list role failed.", HttpStatus.NOT_ACCEPTABLE),
    UPDATE_ROLE_SUCCESS("RL-2005", "Update list role successful.", HttpStatus.ACCEPTED),
    UPDATE_ROLE_FAILED("RL-2006", "Update list role failed.", HttpStatus.NOT_ACCEPTABLE),


    //GROUPS
    GROUP_NOT_FOUND("GR-2000", "GROUP not found.", HttpStatus.BAD_REQUEST),
    PULL_GROUP_SUCCESS("GR-2001", "Pull list GROUP successful.", HttpStatus.ACCEPTED),
    PULL_GROUP_FAILED("GR-2002", "Pull list GROUP failed.", HttpStatus.ACCEPTED),
    CREATE_GROUP_SUCCESS("GR-2003", "Create list GROUP successful.", HttpStatus.ACCEPTED),
    CREATE_GROUP_FAILED("GR-2004", "Create list GROUP failed.", HttpStatus.NOT_ACCEPTABLE),
    UPDATE_GROUP_SUCCESS("GR-2005", "Update list GROUP successful.", HttpStatus.ACCEPTED),
    UPDATE_GROUP_FAILED("GR-2006", "Update list GROUP failed.", HttpStatus.NOT_ACCEPTABLE),


    //CATEGORY
    CATEGORY_NOT_FOUND("CT-2000", "CATEGORY not found.", HttpStatus.BAD_REQUEST),
    PULL_CATEGORY_SUCCESS("CT-2001", "Pull list CATEGORY successful.", HttpStatus.ACCEPTED),
    PULL_CATEGORY_FAILED("CT-2002", "Pull list CATEGORY failed.", HttpStatus.ACCEPTED),
    CREATE_CATEGORY_SUCCESS("CT-2003", "Create list CATEGORY successful.", HttpStatus.ACCEPTED),
    CREATE_CATEGORY_FAILED("CT-2004", "Create list CATEGORY failed.", HttpStatus.NOT_ACCEPTABLE),
    UPDATE_CATEGORY_SUCCESS("CT-2005", "Update list CATEGORY successful.", HttpStatus.ACCEPTED),
    UPDATE_CATEGORY_FAILED("CT-2006", "Update list CATEGORY failed.", HttpStatus.NOT_ACCEPTABLE),

    //AUCTION
    AUCTION_NOT_FOUND("AU-2000", "AUCTION not found.", HttpStatus.BAD_REQUEST),
    PULL_AUCTION_SUCCESS("AU-2001", "Pull list AUCTION successful.", HttpStatus.ACCEPTED),
    PULL_AUCTION_FAILED("AU-2002", "Pull list AUCTION failed.", HttpStatus.ACCEPTED),
    CREATE_AUCTION_SUCCESS("AU-2003", "Create list AUCTION successful.", HttpStatus.ACCEPTED),
    CREATE_AUCTION_FAILED("AU-2004", "Create list AUCTION failed.", HttpStatus.NOT_ACCEPTABLE),
    UPDATE_AUCTION_SUCCESS("AU-2005", "Update list AUCTION successful.", HttpStatus.ACCEPTED),
    UPDATE_AUCTION_FAILED("AU-2006", "Update list AUCTION failed.", HttpStatus.NOT_ACCEPTABLE),
    VALIDATE_AUCTION_FAILED("AU-2007", "Validate auction info failed.", HttpStatus.BAD_REQUEST),
    CANNOT_UPDATE_AUCTION("AU-2008", "Can't update auction after approval.", HttpStatus.BAD_REQUEST),
    DELETE_AUCTION_SUCCESS("AU-2009", "Delete AUCTION successful.", HttpStatus.ACCEPTED),
    DELETE_AUCTION_FAILED("AU-2010", "Delete AUCTION failed.", HttpStatus.NOT_ACCEPTABLE),

    //IMAGE
    IMAGE_NOT_FOUND("IM-2000", "IMAGE not found.", HttpStatus.BAD_REQUEST),
    PULL_IMAGE_SUCCESS("IM-2001", "Pull list IMAGE successful.", HttpStatus.ACCEPTED),
    PULL_IMAGE_FAILED("IM-2002", "Pull list IMAGE failed.", HttpStatus.ACCEPTED),
    CREATE_IMAGE_SUCCESS("IM-2003", "Create list IMAGE successful.", HttpStatus.ACCEPTED),
    CREATE_IMAGE_FAILED("IM-2004", "Create list IMAGE failed.", HttpStatus.NOT_ACCEPTABLE),
   

    //Token
    INVALID_JWT_TOKEN("TK-3000", "Invalid JWT token.", HttpStatus.UNAUTHORIZED),
    SECRET_IS_NULL("TK-3001", "SecretKey byte array cannot be null.", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED("TK-3002", "Error: Unauthorized!", HttpStatus.UNAUTHORIZED),
    INVALID_CREDENTIALS("TK-3003", "Invalid credentials!", HttpStatus.UNAUTHORIZED),
    AUTHEN_SUCCESS("TK-3005", "Authenticate successful!", HttpStatus.UNAUTHORIZED),

    // Confirmation
    CONFIRMATION_NOT_FOUND("CF-4000", "Confirmation not found!", HttpStatus.BAD_REQUEST),
    CONFIRM_FAILED("CF-4001", "Confirm request failed.", HttpStatus.ACCEPTED),
    CONFIRM_SUCCESS("CF-4002", "Confirm request successful.", HttpStatus.ACCEPTED),
    CONFIRM_EXPIRED("CF-4003", "Confirm request expired.", HttpStatus.ACCEPTED),


    //BRAND
    BRAND_NOT_FOUND("BR-2000", "BRAND not found.", HttpStatus.BAD_REQUEST),
    PULL_BRAND_SUCCESS("BR-2001", "Pull list BRAND successful.", HttpStatus.ACCEPTED),
    PULL_BRAND_FAILED("BR-2002", "Pull list BRAND failed.", HttpStatus.ACCEPTED),
    CREATE_BRAND_SUCCESS("BR-2003", "Create list BRAND successful.", HttpStatus.ACCEPTED),
    CREATE_BRAND_FAILED("BR-2004", "Create list BRAND failed.", HttpStatus.NOT_ACCEPTABLE),
    UPDATE_BRAND_SUCCESS("BR-2005", "Update list BRAND successful.", HttpStatus.ACCEPTED),
    UPDATE_BRAND_FAILED("BR-2006", "Update list BRAND failed.", HttpStatus.NOT_ACCEPTABLE),


    //COMMENT
    COMMENT_NOT_FOUND("BR-2000", "COMMENT not found.", HttpStatus.BAD_REQUEST),
    PULL_COMMENT_SUCCESS("BR-2001", "Pull list COMMENT successful.", HttpStatus.ACCEPTED),
    PULL_COMMENT_FAILED("BR-2002", "Pull list COMMENT failed.", HttpStatus.ACCEPTED),
    CREATE_COMMENT_SUCCESS("BR-2003", "Create list COMMENT successful.", HttpStatus.ACCEPTED),
    CREATE_COMMENT_FAILED("BR-2004", "Create list COMMENT failed.", HttpStatus.NOT_ACCEPTABLE),
    UPDATE_COMMENT_SUCCESS("BR-2005", "Update list COMMENT successful.", HttpStatus.ACCEPTED),
    UPDATE_COMMENT_FAILED("BR-2006", "Update list COMMENT failed.", HttpStatus.NOT_ACCEPTABLE),


    //PRODUCT
    PRODUCT_NOT_FOUND("PD-2000", "PRODUCT not found.", HttpStatus.BAD_REQUEST),
    PULL_PRODUCT_SUCCESS("PD-2001", "Pull list PRODUCT successful.", HttpStatus.ACCEPTED),
    PULL_PRODUCT_FAILED("PD-2002", "Pull list PRODUCT failed.", HttpStatus.ACCEPTED),
    CREATE_PRODUCT_SUCCESS("PD-2003", "Create list PRODUCT successful.", HttpStatus.ACCEPTED),
    CREATE_PRODUCT_FAILED("PD-2004", "Create list PRODUCT failed.", HttpStatus.NOT_ACCEPTABLE),
    UPDATE_PRODUCT_SUCCESS("PD-2005", "Update list PRODUCT successful.", HttpStatus.ACCEPTED),
    UPDATE_PRODUCT_FAILED("PD-2006", "Update list PRODUCT failed.", HttpStatus.NOT_ACCEPTABLE),
    CANNOT_CREATE_PRODUCT("AU-2008", "Can't update auction after approval.", HttpStatus.BAD_REQUEST),


    ;
    String code;
    String message;
    HttpStatus status;

    public static AucMessage getByCode(String code) {
        return Arrays.stream(AucMessage.values()).filter(tcbExceptionMessage -> tcbExceptionMessage.code.equalsIgnoreCase(code)).findFirst().orElse(null);
    }
}
