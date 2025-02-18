package com.indexzero.finals.service;

import com.indexzero.finals.dto.EmployeeDTO;
import com.indexzero.finals.entity.Employee;
import org.apache.catalina.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

public interface EmployeeService {
    ResponseEntity<Object> checkIfUserExists(String login);
    ResponseEntity<EmployeeDTO> getUserInfo(Authentication auth);
    ResponseEntity<Object> openTheDoor(Long code, Authentication auth);
}
