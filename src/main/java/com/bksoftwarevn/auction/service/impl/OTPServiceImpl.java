package com.bksoftwarevn.auction.service.impl;

import com.bksoftwarevn.auction.config.OTPConfiguration;
import com.bksoftwarevn.auction.service.OTPService;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class OTPServiceImpl implements OTPService {

    private final OTPConfiguration otpConfiguration;

    private final LoadingCache<String, Integer> otpCache;

    public OTPServiceImpl(OTPConfiguration otpConfiguration){
        super();
        this.otpConfiguration = otpConfiguration;
        otpCache = CacheBuilder.newBuilder().
                expireAfterWrite(otpConfiguration.getDuration(), TimeUnit.MINUTES).build(new CacheLoader<>() {
                    public Integer load(String key) {
                        return 0;
                    }
                });

    }

    //This method is used to push the opt number against Key. Rewrite the OTP if it exists
    //Using user id  as key
    public int generateOTP(String key){
        int otp = 100000 + new Random().nextInt(900000);
        otpCache.put(key, otp);
        return otp;
    }

    //This method is used to return the OPT number against Key->Key values is username
    public int getOtp(String key){
        try{
            return otpCache.get(key);
        }catch (Exception e){
            return 0;
        }
    }

    //This method is used to clear the OTP catched already
    public void clearOTP(String key){
        otpCache.invalidate(key);
    }
}
