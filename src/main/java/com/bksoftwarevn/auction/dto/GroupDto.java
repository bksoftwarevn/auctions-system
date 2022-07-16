package com.bksoftwarevn.auction.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class GroupDto implements Serializable {
    private final String id;
    private final String name;
    private final String type;
    private final String desc;
    private final String additional;

    @Data
    public static class CategoryDto implements Serializable {
        private final String id;
        private final String name;
        private final String image;
        private final String descriptions;
        private final String additional;
        private final GroupDto group;
    }
}
