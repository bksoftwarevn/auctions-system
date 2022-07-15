package com.bksoftwarevn.auction.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"fileName", "content"})
@Data
@Builder
public class Attachment {
    @JsonProperty("fileName")
    private String fileName;
    @JsonProperty("content")
    @NotNull
    private String content;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String, String> additions;
}
