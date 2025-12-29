package com.example.NAOSys.POJO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.kafka.common.protocol.types.Field;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogOffProvider
{
    @JsonProperty(value = "input_token_state")
    private Input_Token_State inputTokenState;

    @JsonProperty(value = "output_token_state")
    private Output_token_state outputTokenState;
}
