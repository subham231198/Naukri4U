package com.example.NAOSys.POJO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessionManager
{
    private List<Map<String, Object>> list = new ArrayList<>();
}
