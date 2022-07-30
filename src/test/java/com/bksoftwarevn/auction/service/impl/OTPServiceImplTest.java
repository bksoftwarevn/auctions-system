package com.bksoftwarevn.auction.service.impl;

import com.bksoftwarevn.auction.config.OTPConfiguration;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.*;

class OTPServiceImplTest {
    private static final String MOCK_ID = "ef529f71-4c00-4acb-869b-a2808ecb23f8";

    private final OTPConfiguration otpConfiguration = mock(OTPConfiguration.class);
    private final LoadingCache<String, Integer> otpCache = mock(LoadingCache.class);
    private OTPServiceImpl otpService;
    private MockedStatic<CacheBuilder> mockedStatic;

    @BeforeEach
    void setup() {
        CacheBuilder<Object, Object> cacheBuilder = mock(CacheBuilder.class);

        long number = 5l;
        mockedStatic = mockStatic(CacheBuilder.class);
        when(otpConfiguration.getDuration()).thenReturn(number);
        mockedStatic.when(() -> CacheBuilder.newBuilder()).thenReturn(cacheBuilder);
        when(cacheBuilder.expireAfterWrite(eq(number), eq(TimeUnit.MINUTES))).thenReturn(cacheBuilder);
        when(cacheBuilder.build(any(CacheLoader.class))).thenReturn(otpCache);

        otpService = new OTPServiceImpl(otpConfiguration);
    }

    @AfterEach
    void clear() {
        mockedStatic.close();
    }

    @Test
    void generateOTP() {
        int actualResult = otpService.generateOTP(MOCK_ID);
        Assertions.assertNotNull(actualResult);
    }

    @Test
    void getOtp() throws ExecutionException {
        when(otpCache.get(MOCK_ID)).thenReturn(1);
        int actualResult = otpService.getOtp(MOCK_ID);
        Assertions.assertNotNull(actualResult);
        Assertions.assertEquals(1, actualResult);
    }

    @Test
    void getOtp1() throws ExecutionException {
        when(otpCache.get(MOCK_ID)).thenThrow(mock(RuntimeException.class));
        int actualResult = otpService.getOtp(MOCK_ID);
        Assertions.assertNotNull(actualResult);
        Assertions.assertEquals(0, actualResult);
    }

    @Test
    void clearOTP() {
        otpService.clearOTP(MOCK_ID);
    }
}