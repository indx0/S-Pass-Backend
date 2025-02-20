package com.indexzero.finals.service;

import com.indexzero.finals.dto.EmployeeDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;


public interface EmployeeService {
    ResponseEntity<EmployeeDTO> getUserInfo(Authentication auth);
    ResponseEntity<Object> openTheDoor(Long code, Authentication auth);
    ResponseEntity<HttpStatusCode> deleteEmployee(String login);
    ResponseEntity<HttpStatusCode> changeState(String login, String state);
    ResponseEntity<Page<EmployeeDTO>> getAllEmployees(Pageable pageable);
    ResponseEntity<EmployeeDTO> getEmployeeByLogin(String login);
    ResponseEntity<EmployeeDTO> updateEmployee(EmployeeDTO updateDTO, String login);
}
