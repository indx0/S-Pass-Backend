package com.indexzero.finals.controller;

import com.indexzero.finals.dto.EmployeeDTO;
import com.indexzero.finals.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @PostMapping("/login")
    @Operation(description = "User Login", summary = "User login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login Successful"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
    })
    public ResponseEntity<Object> login() {
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/profile")
    @Operation(description = "Get user's profile. Username is taken from Authentication.", summary = "Get user's profile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request Successful"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
    })
    public ResponseEntity<EmployeeDTO> getInfo() {
        return employeeService.getUserInfo(SecurityContextHolder.getContext().getAuthentication());
    }

    @PatchMapping("/open")
    @Operation(description = "Open the door. Code is taken from the code URL param.", summary = "Open the door")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Opening Successful"),
            @ApiResponse(responseCode = "401", description = "Unauthorized / Wrong code"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),

    })
    public ResponseEntity<Object> Open(@RequestParam Long code) {
        return employeeService.openTheDoor(code, SecurityContextHolder.getContext().getAuthentication());
    }

    @DeleteMapping("/{login}/delete")
    @Operation(description = "Delete a user by login. (ADMIN only)", summary = "Delete a user by login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deletion Successful"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "409", description = "User you're trying to delete has ADMIN authority"),
    })
    public ResponseEntity<HttpStatusCode> delete(@PathVariable String login) {
        return employeeService.deleteEmployee(login);
    }

    @PatchMapping("/{login}/{state}")
    @Operation(description = "Enable/Disable user's ability to use QR code entrance. (ADMIN only) States: active / blocked", summary = "Enable/Disable QR")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Modification Successful"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "400", description = "State doesn't exist"),
    })
    public ResponseEntity<HttpStatusCode> changeState(@PathVariable String login, @PathVariable String state) {
        return employeeService.changeState(login, state);
    }

    @GetMapping("/all")
    @Operation(description = "Get all users with pagination. (ADMIN Only)", summary = "Get all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request Successful"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
    })
    public ResponseEntity<Page<EmployeeDTO>> getAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return employeeService.getAllEmployees(pageable);
    }

    @GetMapping("/{login}")
    @Operation(description = "Get user's profile by login (ADMIN only)", summary = "Get user's profile by login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Modification Successful"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "User not found"),
    })
    public ResponseEntity<EmployeeDTO> getEmployeeByLogin(@PathVariable String login) {
        return employeeService.getEmployeeByLogin(login);
    }

    @GetMapping("/coffee")@Operation(description = "I Am A Teapot", summary = "I Am A Teapot")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "418", description = "I Am A Teapot"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
    })

    public ResponseEntity<String> coffee() {
        return new ResponseEntity<>("<center><h1>I Am A Teapot</h1></center>", HttpStatus.I_AM_A_TEAPOT);
    }

}
