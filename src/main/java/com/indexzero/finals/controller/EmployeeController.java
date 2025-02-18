package com.indexzero.finals.controller;

import com.indexzero.finals.dto.EmployeeDTO;
import com.indexzero.finals.entity.Code;
import com.indexzero.finals.entity.Employee;
import com.indexzero.finals.entity.Visit;
import com.indexzero.finals.repository.CodeRepository;
import com.indexzero.finals.repository.EmployeeRepository;
import com.indexzero.finals.repository.VisitRepository;
import com.indexzero.finals.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @PostMapping("/login")
    public ResponseEntity<Object> login() {
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/profile")
    public ResponseEntity<EmployeeDTO> getInfo() {
        return employeeService.getUserInfo(SecurityContextHolder.getContext().getAuthentication());
    }

    @PatchMapping("/open")
    public ResponseEntity<Object> Open(@RequestParam Long code) {
        return employeeService.openTheDoor(code, SecurityContextHolder.getContext().getAuthentication());
    }

}
