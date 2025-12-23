package com.example.NAOSys.POJO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class API_StandardResponse<T>
{
    @JsonProperty(value = "code")
    private Integer code;

    @JsonProperty(value = "reason")
    private String reason;

    @JsonProperty(value = "message")
    private String message;
}
