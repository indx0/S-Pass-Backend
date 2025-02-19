package com.indexzero.finals.util;

import com.indexzero.finals.dto.EntranceDTO;
import com.indexzero.finals.entity.Entrance;
import lombok.experimental.UtilityClass;

@UtilityClass
public class EntranceMapper {
    public static EntranceDTO convertToDTO(Entrance entrance) {
        EntranceDTO entranceDTO = new EntranceDTO();

        entranceDTO.setId(entrance.getId());
        entranceDTO.setEmployeeLogin(entrance.getEmployee().getLogin());
        entranceDTO.setEntryTime(entrance.getVisitTime());
        entranceDTO.setReaderName(entrance.getReader().getName());
        entranceDTO.setType(entrance.getType());

        return entranceDTO;
    }
}
