package com.indexzero.finals.service;

import com.indexzero.finals.dto.EmployeeDTO;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface EmployeeService {
    ResponseEntity<Object> checkIfUserExists(String login);
    ResponseEntity<EmployeeDTO> getUserInfo(Authentication auth);
    ResponseEntity<Object> openTheDoor(Long code, Authentication auth);
    ResponseEntity<HttpStatusCode> deleteEmployee(String login);
    ResponseEntity<HttpStatusCode> changeState(String login, String state);
    ResponseEntity<List<EmployeeDTO>> getAllEmployees();
    ResponseEntity<EmployeeDTO> getEmployeeByLogin(String login);
}
