package com.bksoftwarevn.auction.service.impl;

import com.bksoftwarevn.auction.constant.ActionType;
import com.bksoftwarevn.auction.constant.AucMessage;
import com.bksoftwarevn.auction.constant.StatusType;
import com.bksoftwarevn.auction.dto.ConfirmationDto;
import com.bksoftwarevn.auction.exception.AucException;
import com.bksoftwarevn.auction.mapper.ConfirmationMapper;
import com.bksoftwarevn.auction.model.CommonResponse;
import com.bksoftwarevn.auction.model.ConfirmRequest;
import com.bksoftwarevn.auction.persistence.entity.ConfirmationEntity;
import com.bksoftwarevn.auction.persistence.entity.UserEntity;
import com.bksoftwarevn.auction.persistence.repository.ConfirmationRepository;
import com.bksoftwarevn.auction.service.ConfirmationService;
import com.bksoftwarevn.auction.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@Slf4j
public class ConfirmationServiceImpl implements ConfirmationService {

    private final ConfirmationRepository confirmationRepository;
    private final ConfirmationMapper confirmationMapper;
    @Lazy
    private final UserService userService;

    @Override
    public boolean create(ConfirmationDto confirmationDto) {
        boolean rs = false;
        try {
            ConfirmationEntity confirmationEntity = confirmationMapper.mapper(confirmationDto);
            if (ObjectUtils.isNotEmpty(confirmationRepository.save(confirmationEntity))) {
                rs = true;
            }
        } catch (Exception ex) {
            log.error("[ConfirmationServiceImpl.create] Create confirmation exception with detail: ", ex);
        }
        return rs;
    }

    @Override
    public ConfirmationDto find(String confirmId) {
        ConfirmationDto confirmationDto = null;
        try {
            confirmationDto = confirmationMapper.mapper(confirmationRepository.findById(confirmId).orElseThrow(() -> new AucException(AucMessage.INTERNAL_SERVER_ERROR.getCode(), AucMessage.CONFIRMATION_NOT_FOUND.getMessage())));
        } catch (Exception exception) {
            log.error("[ConfirmationServiceImpl.findById] Find confirmation by id exception: ", exception);
        }
        return confirmationDto;
    }

    @Override
    public List<ConfirmationDto> find(String username, String action, String status) {
        List<ConfirmationDto> confirmationDtos = new ArrayList<>();
        try {
            confirmationDtos = confirmationMapper.mappers(confirmationRepository.findAllByUsernameAndActionAndStatus(username, action, status));
        } catch (Exception ex) {
            log.error("[ConfirmationServiceImpl.find] Find confirmation by username: {}, action: {}, status: {}: . Exception: ", username, action, status, ex);
        }
        return confirmationDtos;
    }


    @Override
    public CommonResponse confirm(ConfirmRequest confirmRequest) {
        CommonResponse commonResponse = new CommonResponse().code(AucMessage.CONFIRM_FAILED.getCode()).message(AucMessage.CONFIRM_FAILED.getMessage());
        try {
            ConfirmationEntity confirmationEntity = confirmationRepository.findAllByUsernameAndActionAndStatusAndOtp(confirmRequest.getUsername(), confirmRequest.getAction(), StatusType.PENDING.name(), confirmRequest.getOtp())
                    .stream().findFirst().orElseThrow(() -> new AucException(AucMessage.CONFIRMATION_NOT_FOUND.getCode(), AucMessage.CONFIRMATION_NOT_FOUND.getMessage()));
            if (ActionType.RESET_PASS.name().equalsIgnoreCase(confirmRequest.getAction()) && confirmationEntity.getExpireDate().isAfter(Instant.now())) {
                confirmationEntity.setStatus(StatusType.CONFIRMED.name());
                confirmationRepository.save(confirmationEntity);
                if (userService.updatePassword(confirmRequest.getUsername(), confirmationEntity.getData())) {
                    commonResponse.code(AucMessage.CONFIRM_SUCCESS.getCode()).message(AucMessage.CONFIRM_SUCCESS.getMessage());
                }
            } else {
                commonResponse.message(AucMessage.CONFIRM_EXPIRED.getMessage()).code(AucMessage.CONFIRM_EXPIRED.getCode());
            }
        } catch (Exception exception) {
            log.error("[ConfirmationServiceImpl.findById] Find confirmation by id exception: ", exception);
        }
        return commonResponse;
    }
}
