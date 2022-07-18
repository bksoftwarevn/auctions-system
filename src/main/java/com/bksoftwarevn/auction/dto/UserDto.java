package com.bksoftwarevn.auction.dto;

import com.bksoftwarevn.auction.persistence.entity.CommentEntity;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;
import java.util.Set;

@Data
public class UserDto implements Serializable {
    private final String id;
    private final String username;
    private final String password;
    private final String email;
    private final String name;
    private final String phone;
    private final String avatar;
    private final String address;
    private final String lang;
    private final String activeKey;
    private final Boolean active;
    private final Instant createdDate;
    private final Instant updatedDate;
    private final Boolean lock;
    private final String additional;
    private final String citizenId;
    private final Set<AuditDto> audits;
    private final Set<AuctionDto> auctions;
    private final Set<CommentEntity> comments;
    private final Set<ReplyDto> replies;
    private final Set<RoleDto> roles;
}
