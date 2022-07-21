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
    CREATE_ROLE_SUCCESS("RL-2003", "Create  role successful.", HttpStatus.ACCEPTED),
    CREATE_ROLE_FAILED("RL-2004", "Create  role failed.", HttpStatus.NOT_ACCEPTABLE),
    UPDATE_ROLE_SUCCESS("RL-2005", "Update  role successful.", HttpStatus.ACCEPTED),
    UPDATE_ROLE_FAILED("RL-2006", "Update  role failed.", HttpStatus.NOT_ACCEPTABLE),


    //GROUPS
    GROUP_NOT_FOUND("GR-2000", "GROUP not found.", HttpStatus.BAD_REQUEST),
    PULL_GROUP_SUCCESS("GR-2001", "Pull list GROUP successful.", HttpStatus.ACCEPTED),
    PULL_GROUP_FAILED("GR-2002", "Pull list GROUP failed.", HttpStatus.ACCEPTED),
    CREATE_GROUP_SUCCESS("GR-2003", "Create  GROUP successful.", HttpStatus.ACCEPTED),
    CREATE_GROUP_FAILED("GR-2004", "Create  GROUP failed.", HttpStatus.NOT_ACCEPTABLE),
    UPDATE_GROUP_SUCCESS("GR-2005", "Update  GROUP successful.", HttpStatus.ACCEPTED),
    UPDATE_GROUP_FAILED("GR-2006", "Update  GROUP failed.", HttpStatus.NOT_ACCEPTABLE),


    //CATEGORY
    CATEGORY_NOT_FOUND("CT-2000", "CATEGORY not found.", HttpStatus.BAD_REQUEST),
    PULL_CATEGORY_SUCCESS("CT-2001", "Pull list CATEGORY successful.", HttpStatus.ACCEPTED),
    PULL_CATEGORY_FAILED("CT-2002", "Pull list CATEGORY failed.", HttpStatus.ACCEPTED),
    CREATE_CATEGORY_SUCCESS("CT-2003", "Create  CATEGORY successful.", HttpStatus.ACCEPTED),
    CREATE_CATEGORY_FAILED("CT-2004", "Create  CATEGORY failed.", HttpStatus.NOT_ACCEPTABLE),
    UPDATE_CATEGORY_SUCCESS("CT-2005", "Update  CATEGORY successful.", HttpStatus.ACCEPTED),
    UPDATE_CATEGORY_FAILED("CT-2006", "Update  CATEGORY failed.", HttpStatus.NOT_ACCEPTABLE),

    //AUCTION
    AUCTION_NOT_FOUND("AU-2000", "AUCTION not found.", HttpStatus.BAD_REQUEST),
    PULL_AUCTION_SUCCESS("AU-2001", "Pull list AUCTION successful.", HttpStatus.ACCEPTED),
    PULL_AUCTION_FAILED("AU-2002", "Pull list AUCTION failed.", HttpStatus.ACCEPTED),
    CREATE_AUCTION_SUCCESS("AU-2003", "Create  AUCTION successful.", HttpStatus.ACCEPTED),
    CREATE_AUCTION_FAILED("AU-2004", "Create  AUCTION failed.", HttpStatus.NOT_ACCEPTABLE),
    UPDATE_AUCTION_SUCCESS("AU-2005", "Update  AUCTION successful.", HttpStatus.ACCEPTED),
    UPDATE_AUCTION_FAILED("AU-2006", "Update  AUCTION failed.", HttpStatus.NOT_ACCEPTABLE),
    VALIDATE_AUCTION_FAILED("AU-2007", "Validate auction info failed.", HttpStatus.BAD_REQUEST),
    CANNOT_UPDATE_AUCTION("AU-2008", "Can't update auction.", HttpStatus.BAD_REQUEST),
    DELETE_AUCTION_SUCCESS("AU-2009", "Delete AUCTION successful.", HttpStatus.ACCEPTED),
    DELETE_AUCTION_FAILED("AU-2010", "Delete AUCTION failed.", HttpStatus.NOT_ACCEPTABLE),
    CANNOT_UPDATE_AUCTION_PENDING("AU-2011", "Can't update auction pending or waiting.", HttpStatus.BAD_REQUEST),

    //IMAGE
    IMAGE_NOT_FOUND("IM-2000", "IMAGE not found.", HttpStatus.BAD_REQUEST),
    PULL_IMAGE_SUCCESS("IM-2001", "Pull list IMAGE successful.", HttpStatus.ACCEPTED),
    PULL_IMAGE_FAILED("IM-2002", "Pull list IMAGE failed.", HttpStatus.ACCEPTED),
    CREATE_IMAGE_SUCCESS("IM-2003", "Create IMAGE successful.", HttpStatus.ACCEPTED),
    CREATE_IMAGE_FAILED("IM-2004", "Create IMAGE failed.", HttpStatus.NOT_ACCEPTABLE),


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
    CREATE_BRAND_SUCCESS("BR-2003", "Create BRAND successful.", HttpStatus.ACCEPTED),
    CREATE_BRAND_FAILED("BR-2004", "Create BRAND failed.", HttpStatus.NOT_ACCEPTABLE),
    UPDATE_BRAND_SUCCESS("BR-2005", "Update BRAND successful.", HttpStatus.ACCEPTED),
    UPDATE_BRAND_FAILED("BR-2006", "Update  BRAND failed.", HttpStatus.NOT_ACCEPTABLE),


    //NEWS
    NEWS_NOT_FOUND("NW-2000", "NEWS not found.", HttpStatus.BAD_REQUEST),
    PULL_NEWS_SUCCESS("NW-2001", "Pull list NEWS successful.", HttpStatus.ACCEPTED),
    PULL_NEWS_FAILED("NW-2002", "Pull list NEWS failed.", HttpStatus.ACCEPTED),
    CREATE_NEWS_SUCCESS("NW-2003", "Create NEWS successful.", HttpStatus.ACCEPTED),
    CREATE_NEWS_FAILED("NW-2004", "Create NEWS failed.", HttpStatus.NOT_ACCEPTABLE),
    UPDATE_NEWS_SUCCESS("NW-2005", "Update NEWS successful.", HttpStatus.ACCEPTED),
    UPDATE_NEWS_FAILED("NW-2006", "Update NEWS failed.", HttpStatus.NOT_ACCEPTABLE),
    DELETE_NEWS_SUCCESS("NW-2007", "Delete NEWS successful.", HttpStatus.ACCEPTED),
    DELETE_NEWS_FAILED("NW-2008", "Delete NEWS failed.", HttpStatus.NOT_ACCEPTABLE),
    READ_NEWS_SUCCESS("NW-2009", "READ NEWS successful.", HttpStatus.ACCEPTED),
    READ_NEWS_FAILED("NW-2010", "READ NEWS failed.", HttpStatus.NOT_ACCEPTABLE),



    NOTIFICATIONS_NOT_FOUND("NT-2000", "NOTIFICATIONS not found.", HttpStatus.BAD_REQUEST),
    PULL_NOTIFICATIONS_SUCCESS("NT-2001", "Pull list NOTIFICATIONS successful.", HttpStatus.ACCEPTED),
    PULL_NOTIFICATIONS_FAILED("NT-2002", "Pull list NOTIFICATIONS failed.", HttpStatus.ACCEPTED),
    CREATE_NOTIFICATIONS_SUCCESS("NT-2003", "Create NOTIFICATIONS successful.", HttpStatus.ACCEPTED),
    CREATE_NOTIFICATIONS_FAILED("NT-2004", "Create NOTIFICATIONS failed.", HttpStatus.NOT_ACCEPTABLE),
    UPDATE_NOTIFICATIONS_SUCCESS("NT-2005", "Update NOTIFICATIONS successful.", HttpStatus.ACCEPTED),
    UPDATE_NOTIFICATIONS_FAILED("NT-2006", "Update NOTIFICATIONS failed.", HttpStatus.NOT_ACCEPTABLE),
    DELETE_NOTIFICATIONS_SUCCESS("NT-2007", "Delete NOTIFICATIONS successful.", HttpStatus.ACCEPTED),
    DELETE_NOTIFICATIONS_FAILED("NT-2008", "Delete NOTIFICATIONS failed.", HttpStatus.NOT_ACCEPTABLE),
    READ_NOTIFICATIONS_SUCCESS("NT-2009", "READ NOTIFICATIONS successful.", HttpStatus.ACCEPTED),
    READ_NOTIFICATIONS_FAILED("NT-2010", "READ NOTIFICATIONS failed.", HttpStatus.NOT_ACCEPTABLE),


    //COMMENT
    COMMENT_NOT_FOUND("CM-2000", "COMMENT not found.", HttpStatus.BAD_REQUEST),
    PULL_COMMENT_SUCCESS("CM-2001", "Pull list COMMENT successful.", HttpStatus.ACCEPTED),
    PULL_COMMENT_FAILED("CM-2002", "Pull list COMMENT failed.", HttpStatus.ACCEPTED),
    CREATE_COMMENT_SUCCESS("CM-2003", "Create  COMMENT successful.", HttpStatus.ACCEPTED),
    CREATE_COMMENT_FAILED("CM-2004", "Create  COMMENT failed.", HttpStatus.NOT_ACCEPTABLE),
    UPDATE_COMMENT_SUCCESS("CM-2005", "Update  COMMENT successful.", HttpStatus.ACCEPTED),
    UPDATE_COMMENT_FAILED("CM-2006", "Update  COMMENT failed.", HttpStatus.NOT_ACCEPTABLE),
    DELETE_COMMENT_SUCCESS("CM-2007", "Delete  COMMENT successful.", HttpStatus.ACCEPTED),
    DELETE_COMMENT_FAILED("CM-2008", "Delete  COMMENT failed.", HttpStatus.NOT_ACCEPTABLE),
    LIKE_SUCCESS("LK-2008", "LIKE successful.", HttpStatus.ACCEPTED),
    LIKE_FAILED("LK-2009", "LIKE failed.", HttpStatus.NOT_ACCEPTABLE),
    CANNOT_LIKE("LK-2010", "Can not like auctions has not approved yet.", HttpStatus.NOT_ACCEPTABLE),
    LIKE_NOT_FOUND("LK-2011", "Like not found.", HttpStatus.BAD_REQUEST),
    PULL_LIKE_SUCCESS("LK-2001", "Pull list auctions liked successful.", HttpStatus.ACCEPTED),
    PULL_LIKE_FAILED("LK-2002", "Pull list auctions liked failed.", HttpStatus.ACCEPTED),



    //PRODUCT
    PRODUCT_NOT_FOUND("PD-2000", "PRODUCT not found.", HttpStatus.BAD_REQUEST),
    PULL_PRODUCT_SUCCESS("PD-2001", "Pull list PRODUCT successful.", HttpStatus.ACCEPTED),
    PULL_PRODUCT_FAILED("PD-2002", "Pull list PRODUCT failed.", HttpStatus.ACCEPTED),
    CREATE_PRODUCT_SUCCESS("PD-2003", "Create  PRODUCT successful.", HttpStatus.ACCEPTED),
    CREATE_PRODUCT_FAILED("PD-2004", "Create  PRODUCT failed.", HttpStatus.NOT_ACCEPTABLE),
    UPDATE_PRODUCT_SUCCESS("PD-2005", "Update  PRODUCT successful.", HttpStatus.ACCEPTED),
    UPDATE_PRODUCT_FAILED("PD-2006", "Update  PRODUCT failed.", HttpStatus.NOT_ACCEPTABLE),
    CANNOT_CREATE_PRODUCT("PD-2007", "Can't create auction after approval.", HttpStatus.BAD_REQUEST),
    CANNOT_UPDATE_PRODUCT("PD-2008", "Can't update auction after approval.", HttpStatus.BAD_REQUEST),


    //BID
    BID_NOT_FOUND("BID-2000", "BID not found.", HttpStatus.BAD_REQUEST),
    PULL_BID_SUCCESS("BID-2001", "Pull list BID successful.", HttpStatus.ACCEPTED),
    PULL_BID_FAILED("BID-2002", "Pull list BID failed.", HttpStatus.ACCEPTED),
    CREATE_BID_SUCCESS("BID-2003", "Create  BID successful.", HttpStatus.ACCEPTED),
    CREATE_BID_FAILED("BID-2004", "Create  BID failed.", HttpStatus.NOT_ACCEPTABLE),
    CAN_NOT_BID("BID-2005", "Auction hasn't started yet", HttpStatus.NOT_ACCEPTABLE),
    PRODUCT_NOT_IN_AUCTION("BID-2006","This product is not in the auctions", HttpStatus.NOT_ACCEPTABLE),
    ACCEPT_BID_SUCCESS("BID-2007", "Accepted  BID successful.", HttpStatus.ACCEPTED),
    ACCEPT_BID_FAILED("BID-2008", "Accepted BID failed.", HttpStatus.NOT_ACCEPTABLE),
    CAN_NOT_ACCEPT_BID("BID-2009", "Auction hasn't stopped yet", HttpStatus.NOT_ACCEPTABLE),
    PRODUCT_HAS_NO_BID("BID-2010", "The product has no buyers", HttpStatus.NOT_ACCEPTABLE),
    PRODUCT_HAS_ACCEPTED("BID-2011", "The product has accepted", HttpStatus.NOT_ACCEPTABLE),
    BUYER_SAME_SELLER("BID-2012", "Buyer and seller cannot be the same person", HttpStatus.NOT_ACCEPTABLE),


    ;
    String code;
    String message;
    HttpStatus status;

    public static AucMessage getByCode(String code) {
        return Arrays.stream(AucMessage.values()).filter(tcbExceptionMessage -> tcbExceptionMessage.code.equalsIgnoreCase(code)).findFirst().orElse(null);
    }
}
