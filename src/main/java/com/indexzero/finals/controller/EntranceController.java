package com.indexzero.finals.controller;

import com.indexzero.finals.dto.EntranceDTO;
import com.indexzero.finals.service.EntranceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/entrance")
public class EntranceController {
    @Autowired
    EntranceService entranceService;


    @GetMapping
    @Operation(description = "Get all entries of a user with pagination. Username is taken from Authentication", summary = "Get all entries of a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request Successful."),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
    })
    public ResponseEntity<Page<EntranceDTO>> getEntrances(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return entranceService.getEmployeeEntrances(pageable, SecurityContextHolder.getContext().getAuthentication());
    }

    @GetMapping("/last")
    @Operation(description = "Get user's last entries. Username is taken from Authentication", summary = "Get user's last entry")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request Successful."),
            @ApiResponse(responseCode = "204", description = "No Entrances."),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
    })
    public ResponseEntity<EntranceDTO> getLastEntrance() {
        return entranceService.getLastEntrance(SecurityContextHolder.getContext().getAuthentication());
    }

    @GetMapping("/all")
    @Operation(description = "Get all entries of all users with pagination (ADMIN only)", summary = "Get all entries")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request Successful."),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
    })
    public ResponseEntity<Page<EntranceDTO>> getAllEntrances(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return entranceService.getAllEntrances(pageable);
    }

    @GetMapping("/{login}")
    @Operation(description = "Get all entries of a user by login (ADMIN only)", summary = "Get entries by login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request Successful."),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<Page<EntranceDTO>> getEntrancesByLogin (@PathVariable String login, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return entranceService.getEntrancesByLogin(pageable, login);
    }

}
