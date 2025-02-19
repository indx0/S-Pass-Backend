package com.indexzero.finals.util;

import com.indexzero.finals.dto.EmployeeDTO;
import com.indexzero.finals.entity.Employee;
import lombok.experimental.UtilityClass;

@UtilityClass
public class EmployeeMapper {
    public static EmployeeDTO convertToDTO(Employee user) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(user.getId());
        employeeDTO.setLogin(user.getLogin());
        employeeDTO.setAuthority(user.getAuthorities().iterator().next().getAuthority());
        employeeDTO.setName(user.getName());
        employeeDTO.setPosition(user.getPosition());
        employeeDTO.setPhotoUrl(user.getPhotoUrl());
        employeeDTO.setQREnabled(user.getIsQREnabled());
        return employeeDTO;
    }
}
