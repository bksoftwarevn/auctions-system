package com.bksoftwarevn.auction.service;

import com.bksoftwarevn.auction.dto.ConfirmationDto;
import com.bksoftwarevn.auction.model.CommonResponse;
import com.bksoftwarevn.auction.model.ConfirmRequest;

import java.util.List;

public interface ConfirmationService {
    boolean create(ConfirmationDto confirmationDto);

    ConfirmationDto find(String confirmId);

    List<ConfirmationDto> find(String username, String action, String status);

    CommonResponse confirm(ConfirmRequest confirmRequest);
}
