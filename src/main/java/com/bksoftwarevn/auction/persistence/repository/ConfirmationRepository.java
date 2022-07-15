package com.bksoftwarevn.auction.persistence.repository;

import com.bksoftwarevn.auction.persistence.entity.ConfirmationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConfirmationRepository extends JpaRepository<ConfirmationEntity, String> {
    List<ConfirmationEntity> findAllByUsernameAndActionAndStatusAndOtp(String username, String action, String status, String otp);

    List<ConfirmationEntity> findAllByUsernameAndActionAndStatus(String username, String action, String status);


}