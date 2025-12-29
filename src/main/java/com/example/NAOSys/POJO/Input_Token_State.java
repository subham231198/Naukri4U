package com.example.NAOSys.POJO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.kafka.common.protocol.types.Field;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Input_Token_State
{
    @JsonProperty(value = "token_type")
    private String token_type;

    @JsonProperty(value = "tokenId")
    private String tokenId;
}
