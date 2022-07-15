package com.bksoftwarevn.auction.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"to", "cc", "bcc", "from", "body", "subject", "important", "attachments"})
@JsonIgnoreProperties(
        ignoreUnknown = true
)
@Data
@Builder
public class EmailBody {
    @JsonProperty("to")
    @Valid
    @NotNull
    private List<String> to;
    @JsonProperty("cc")
    @Valid
    private List<String> cc;
    @JsonProperty("bcc")
    @Valid
    private List<String> bcc;
    @JsonProperty("from")
    private String from;
    @JsonProperty("body")
    @NotNull
    private String body;
    @JsonProperty("body")
    private boolean html;
    @JsonProperty("subject")
    private String subject;
    @JsonProperty("important")
    private boolean important;
    @JsonProperty("attachments")
    @Valid
    private List<Attachment> attachments;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String, String> additions;

}
