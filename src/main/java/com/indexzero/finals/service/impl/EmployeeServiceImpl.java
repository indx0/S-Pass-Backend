package com.indexzero.finals.service.impl;

import com.indexzero.finals.entity.Employee;
import com.indexzero.finals.repository.CodeRepository;
import com.indexzero.finals.repository.EmployeeRepository;
import com.indexzero.finals.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    CodeRepository codeRepository;

    public ResponseEntity<Object> checkIfUserExists(String login) {
        try {
            if(employeeRepository.existsByLogin(login)) {
                return new ResponseEntity<>(HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        }
        catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    public ResponseEntity<Employee> getUserInfo(String login) {
        try {
            if (employeeRepository.existsByLogin(login)) {
                return new ResponseEntity<>(employeeRepository.findByLogin(login), HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        }
        catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    public ResponseEntity<Object> openTheDoor(String login, Long code) {
        try {
            if(employeeRepository.existsByLogin(login)) {
                if (codeRepository.existsByValue(Long.valueOf(code))) {
                    Employee employee = employeeRepository.findByLogin(login);

                    LocalDateTime time = LocalDateTime.now();
                    String formatted = time.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                    formatted = formatted.split("\\.")[0];
                    // employee.setLastVisit(LocalDateTime.parse(formatted));

                    employeeRepository.save(employee);
                    return new ResponseEntity<>(HttpStatus.OK);
                }
                else {
                    return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
                }
            }
            else {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        }
        catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
