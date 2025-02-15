package com.indexzero.finals.controller;

import com.indexzero.finals.entity.Employee;
import com.indexzero.finals.entity.OpenRequestBody;
import com.indexzero.finals.repository.CodeRepository;
import com.indexzero.finals.repository.EmployeeRepository;
import com.indexzero.finals.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class EmployeeController {
    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    CodeRepository codeRepository;

    @Autowired
    EmployeeService employeeService;

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
