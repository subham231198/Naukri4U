package com.example.NAOSys.POJO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Output_token_state
{
    @JsonProperty(value = "token_type")
    private String token_type;

    @JsonProperty(value = "subject_confirmation")
    private String subject_confirmation;
}
