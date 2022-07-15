package com.bksoftwarevn.auction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class RoleDto implements Serializable {
    private Integer id;
    private String role;
    private String description;
}
