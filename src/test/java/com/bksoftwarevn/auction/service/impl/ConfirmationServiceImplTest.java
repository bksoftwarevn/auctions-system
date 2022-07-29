package com.bksoftwarevn.auction.service.impl;

import com.bksoftwarevn.auction.constant.AucMessage;
import com.bksoftwarevn.auction.dto.ConfirmationDto;
import com.bksoftwarevn.auction.exception.AucException;
import com.bksoftwarevn.auction.mapper.ConfirmationMapper;
import com.bksoftwarevn.auction.persistence.entity.ConfirmationEntity;
import com.bksoftwarevn.auction.persistence.repository.ConfirmationRepository;
import com.bksoftwarevn.auction.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ConfirmationServiceImplTest {
    private final ConfirmationRepository confirmationRepository = mock(ConfirmationRepository.class);
    private final ConfirmationMapper confirmationMapper = mock(ConfirmationMapper.class);
    private final UserService userService = mock(UserService.class);
    private ConfirmationServiceImpl confirmationService;

    @BeforeEach
    public void setup() {
        confirmationService = new ConfirmationServiceImpl(confirmationRepository, confirmationMapper, userService);
    }

    @Test
    void create() {
        ConfirmationDto confirmationDto = mock(ConfirmationDto.class);
        ConfirmationEntity confirmationEntity = mock(ConfirmationEntity.class);

        when(confirmationMapper.mapper(confirmationDto)).thenReturn(confirmationEntity);
        when(confirmationRepository.save(confirmationEntity)).thenReturn(null);

        boolean actualResult = confirmationService.create(confirmationDto);
        Assertions.assertFalse(actualResult);
    }

    @Test
    void create1() {
        ConfirmationDto confirmationDto = mock(ConfirmationDto.class);
        ConfirmationEntity confirmationEntity = mock(ConfirmationEntity.class);

        when(confirmationMapper.mapper(confirmationDto)).thenReturn(confirmationEntity);
        when(confirmationRepository.save(confirmationEntity)).thenReturn(mock(ConfirmationEntity.class));

        boolean actualResult = confirmationService.create(confirmationDto);
        Assertions.assertTrue(actualResult);
    }

    @Test
    void create2() {
        ConfirmationDto confirmationDto = mock(ConfirmationDto.class);
        ConfirmationEntity confirmationEntity = mock(ConfirmationEntity.class);

        when(confirmationMapper.mapper(confirmationDto)).thenReturn(confirmationEntity);
        when(confirmationRepository.save(confirmationEntity)).thenThrow(new AucException(AucMessage.INTERNAL_SERVER_ERROR.getCode(), AucMessage.CONFIRMATION_NOT_FOUND.getMessage()));

        boolean actualResult = confirmationService.create(confirmationDto);
        Assertions.assertFalse(actualResult);
    }

    @Test
    void find() {
    }

    @Test
    void testFind() {
    }

    @Test
    void confirm() {
    }
}