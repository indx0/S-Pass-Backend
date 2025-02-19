package com.indexzero.finals.controller;

import com.indexzero.finals.dto.EmployeeDTO;
import com.indexzero.finals.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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

    @DeleteMapping("/{login}/delete")
    public ResponseEntity<HttpStatusCode> delete(@PathVariable String login) {
        return employeeService.deleteEmployee(login);
    }

    @PatchMapping("/{login}/{state}")
    public ResponseEntity<HttpStatusCode> changeState(@PathVariable String login, @PathVariable String state) {
        return employeeService.changeState(login, state);
    }

    @GetMapping("/all")
    public ResponseEntity<List<EmployeeDTO>> getAll() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{login}")
    public ResponseEntity<EmployeeDTO> getEmployeeByLogin(@PathVariable String login) {
        return employeeService.getEmployeeByLogin(login);
    }

}
