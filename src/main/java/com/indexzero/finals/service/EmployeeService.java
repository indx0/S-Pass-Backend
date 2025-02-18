package com.indexzero.finals.service;

import com.indexzero.finals.entity.Employee;
import org.springframework.http.ResponseEntity;

public interface EmployeeService {
    ResponseEntity<Object> checkIfUserExists(String login);
    ResponseEntity<Employee> getUserInfo(String login);
    ResponseEntity<Object> openTheDoor(String login, Long code);
}
