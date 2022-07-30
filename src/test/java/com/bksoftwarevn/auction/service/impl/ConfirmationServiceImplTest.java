package com.bksoftwarevn.auction.service.impl;

import com.bksoftwarevn.auction.constant.ActionType;
import com.bksoftwarevn.auction.constant.AucMessage;
import com.bksoftwarevn.auction.constant.AuctionStatus;
import com.bksoftwarevn.auction.dto.ConfirmationDto;
import com.bksoftwarevn.auction.exception.AucException;
import com.bksoftwarevn.auction.mapper.ConfirmationMapper;
import com.bksoftwarevn.auction.model.CommonResponse;
import com.bksoftwarevn.auction.model.ConfirmRequest;
import com.bksoftwarevn.auction.persistence.entity.ConfirmationEntity;
import com.bksoftwarevn.auction.persistence.repository.ConfirmationRepository;
import com.bksoftwarevn.auction.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ConfirmationServiceImplTest {
    private final ConfirmationRepository confirmationRepository = mock(ConfirmationRepository.class);
    private final ConfirmationMapper confirmationMapper = mock(ConfirmationMapper.class);
    private final UserService userService = mock(UserService.class);
    private ConfirmationServiceImpl confirmationService;

    private static final String MOCK_ID = "ef529f71-4c00-4acb-869b-a2808ecb23f8";

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
        when(confirmationRepository.findById(MOCK_ID)).thenReturn(Optional.empty());
        ConfirmationDto actualResult = confirmationService.find(MOCK_ID);
        Assertions.assertNull(actualResult);
    }

    @Test
    void find1() {
        ConfirmationEntity confirmationEntity = mock(ConfirmationEntity.class);

        when(confirmationRepository.findById(MOCK_ID)).thenReturn(Optional.of(confirmationEntity));
        when(confirmationMapper.mapper(confirmationEntity)).thenReturn(mock(ConfirmationDto.class));
        ConfirmationDto actualResult = confirmationService.find(MOCK_ID);
        Assertions.assertNotNull(actualResult);
    }

    @Test
    void testFind() {
        when(confirmationRepository.findAllByUsernameAndActionAndStatus(MOCK_ID, MOCK_ID, MOCK_ID)).thenThrow(new AucException(AucMessage.CONFIRMATION_NOT_FOUND.getCode(), AucMessage.CONFIRMATION_NOT_FOUND.getMessage()));
        List<ConfirmationDto> actualResult = confirmationService.find(MOCK_ID, MOCK_ID, MOCK_ID);
        Assertions.assertNotNull(actualResult);
    }

    @Test
    void testFind1() {
        List<ConfirmationEntity> confirmationEntities = mock(List.class);

        when(confirmationRepository.findAllByUsernameAndActionAndStatus(MOCK_ID, MOCK_ID, MOCK_ID)).thenReturn(confirmationEntities);
        when(confirmationMapper.mappers(confirmationEntities)).thenReturn(mock(List.class));
        List<ConfirmationDto> actualResult = confirmationService.find(MOCK_ID, MOCK_ID, MOCK_ID);
        Assertions.assertNotNull(actualResult);
    }

    @Test
    void confirm() {
        ConfirmRequest confirmRequest = mock(ConfirmRequest.class);

        when(confirmRequest.getUsername()).thenReturn(MOCK_ID);
        when(confirmRequest.getAction()).thenReturn(MOCK_ID);
        when(confirmRequest.getOtp()).thenReturn(MOCK_ID);
        when(confirmationRepository.findAllByUsernameAndActionAndStatusAndOtp(MOCK_ID, MOCK_ID, AuctionStatus.PENDING.name(), MOCK_ID)).thenReturn(new ArrayList<>());

        CommonResponse actualResult = confirmationService.confirm(confirmRequest);
        Assertions.assertNotNull(actualResult);
    }

    @Test
    void confirm1() {
        ConfirmationEntity confirmationEntity = new ConfirmationEntity();
        List<ConfirmationEntity> confirmationEntityList = new ArrayList<>();
        confirmationEntityList.add(confirmationEntity);

        ConfirmRequest confirmRequest = mock(ConfirmRequest.class);

        when(confirmRequest.getUsername()).thenReturn(MOCK_ID);
        when(confirmRequest.getAction()).thenReturn(MOCK_ID);
        when(confirmRequest.getOtp()).thenReturn(MOCK_ID);
        when(confirmationRepository.findAllByUsernameAndActionAndStatusAndOtp(MOCK_ID, MOCK_ID, AuctionStatus.PENDING.name(), MOCK_ID)).thenReturn(confirmationEntityList);

        CommonResponse actualResult = confirmationService.confirm(confirmRequest);
        Assertions.assertNotNull(actualResult);
    }

    @Test
    void confirm2() {
        ConfirmationEntity confirmationEntity = new ConfirmationEntity();
        List<ConfirmationEntity> confirmationEntityList = new ArrayList<>();
        confirmationEntityList.add(confirmationEntity);

        ConfirmRequest confirmRequest = mock(ConfirmRequest.class);

        when(confirmRequest.getUsername()).thenReturn(MOCK_ID);
        when(confirmRequest.getAction()).thenReturn(ActionType.RESET_PASS.name());
        when(confirmRequest.getOtp()).thenReturn(MOCK_ID);
        when(confirmationRepository.findAllByUsernameAndActionAndStatusAndOtp(MOCK_ID, ActionType.RESET_PASS.name(), AuctionStatus.PENDING.name(), MOCK_ID)).thenReturn(confirmationEntityList);

        CommonResponse actualResult = confirmationService.confirm(confirmRequest);
        Assertions.assertNotNull(actualResult);
    }

    @Test
    void confirm3() {
        ConfirmationEntity confirmationEntity = new ConfirmationEntity();
        confirmationEntity.setExpireDate(Instant.parse("2022-05-29T10:58:18.792353Z"));
        List<ConfirmationEntity> confirmationEntityList = new ArrayList<>();
        confirmationEntityList.add(confirmationEntity);

        ConfirmRequest confirmRequest = mock(ConfirmRequest.class);

        when(confirmRequest.getUsername()).thenReturn(MOCK_ID);
        when(confirmRequest.getAction()).thenReturn(ActionType.RESET_PASS.name());
        when(confirmRequest.getOtp()).thenReturn(MOCK_ID);
        when(confirmationRepository.findAllByUsernameAndActionAndStatusAndOtp(MOCK_ID, ActionType.RESET_PASS.name(), AuctionStatus.PENDING.name(), MOCK_ID)).thenReturn(confirmationEntityList);

        CommonResponse actualResult = confirmationService.confirm(confirmRequest);
        Assertions.assertNotNull(actualResult);
    }

    @Test
    void confirm4() {
        ConfirmationEntity confirmationEntity = new ConfirmationEntity();
        confirmationEntity.setExpireDate(Instant.parse("2022-09-29T10:58:18.792353Z"));
        confirmationEntity.setData(MOCK_ID);
        List<ConfirmationEntity> confirmationEntityList = new ArrayList<>();
        confirmationEntityList.add(confirmationEntity);

        ConfirmRequest confirmRequest = mock(ConfirmRequest.class);

        when(confirmRequest.getUsername()).thenReturn(MOCK_ID);
        when(confirmRequest.getAction()).thenReturn(ActionType.RESET_PASS.name());
        when(confirmRequest.getOtp()).thenReturn(MOCK_ID);
        when(confirmationRepository.findAllByUsernameAndActionAndStatusAndOtp(MOCK_ID, ActionType.RESET_PASS.name(), AuctionStatus.PENDING.name(), MOCK_ID)).thenReturn(confirmationEntityList);
        when(confirmRequest.getUsername()).thenReturn(MOCK_ID);
        when(userService.updatePassword(MOCK_ID, MOCK_ID)).thenReturn(true);

        CommonResponse actualResult = confirmationService.confirm(confirmRequest);
        Assertions.assertNotNull(actualResult);
    }

    @Test
    void confirm5() {
        ConfirmationEntity confirmationEntity = new ConfirmationEntity();
        confirmationEntity.setExpireDate(Instant.parse("2022-09-29T10:58:18.792353Z"));
        confirmationEntity.setData(MOCK_ID);
        List<ConfirmationEntity> confirmationEntityList = new ArrayList<>();
        confirmationEntityList.add(confirmationEntity);

        ConfirmRequest confirmRequest = mock(ConfirmRequest.class);

        when(confirmRequest.getUsername()).thenReturn(MOCK_ID);
        when(confirmRequest.getAction()).thenReturn(ActionType.RESET_PASS.name());
        when(confirmRequest.getOtp()).thenReturn(MOCK_ID);
        when(confirmationRepository.findAllByUsernameAndActionAndStatusAndOtp(MOCK_ID, ActionType.RESET_PASS.name(), AuctionStatus.PENDING.name(), MOCK_ID)).thenReturn(confirmationEntityList);
        when(confirmRequest.getUsername()).thenReturn(MOCK_ID);
        when(userService.updatePassword(MOCK_ID, MOCK_ID)).thenReturn(false);

        CommonResponse actualResult = confirmationService.confirm(confirmRequest);
        Assertions.assertNotNull(actualResult);
    }
}