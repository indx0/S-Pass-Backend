package com.indexzero.finals.controller;

import com.indexzero.finals.entity.Code;
import com.indexzero.finals.entity.Employee;
import com.indexzero.finals.entity.Visit;
import com.indexzero.finals.repository.CodeRepository;
import com.indexzero.finals.repository.EmployeeRepository;
import com.indexzero.finals.repository.VisitRepository;
import com.indexzero.finals.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
public class EmployeeController {
    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    CodeRepository codeRepository;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    VisitRepository visitRepository;

    @GetMapping("/{login}/auth")
    public ResponseEntity<Object> Auth(@PathVariable String login) {
        return employeeService.checkIfUserExists(login);
    }

    @GetMapping("/{login}/info")
    public ResponseEntity<Employee> getInfo(@PathVariable String login) {
        return employeeService.getUserInfo(login);
    }

    @PatchMapping("/{login}/open")
    public ResponseEntity<Object> Open(@RequestParam Long code, @PathVariable String login) {
        return employeeService.openTheDoor(login, code);
    }
}
