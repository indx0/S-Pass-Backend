package com.indexzero.finals.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class EmployeeDTO {
    private long id;
    private String login;
    private String name;
    private String authority;
    private String position;
    private String photoUrl;
    @JsonProperty("qrEnabled")
    private boolean isQREnabled;
}
