package com.indexzero.finals.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EntranceDTO {
    private long id;
    private String employeeLogin;
    private LocalDateTime entryTime;
    private String readerName;
    private String type;
    private String entryType;
}
