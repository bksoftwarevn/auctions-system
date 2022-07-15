package com.bksoftwarevn.auction.service;

public interface OTPService {
    int generateOTP(String key);
    int getOtp(String key);
    void clearOTP(String key);
}
