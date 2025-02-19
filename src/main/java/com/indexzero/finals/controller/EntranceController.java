package com.indexzero.finals.controller;

import com.indexzero.finals.dto.EntranceDTO;
import com.indexzero.finals.service.EntranceService;
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
    public ResponseEntity<Page<EntranceDTO>> getEntrances(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return entranceService.getEmployeeEntrances(pageable, SecurityContextHolder.getContext().getAuthentication());
    }

    @GetMapping("/all")
    public ResponseEntity<Page<EntranceDTO>> getAllEntrances(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return entranceService.getAllEntrances(pageable);
    }

}
