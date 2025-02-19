package com.indexzero.finals.controller;

import com.indexzero.finals.entity.Entrance;
import com.indexzero.finals.repository.EntranceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/entrance")
public class CodeController {
    @Autowired
    EntranceRepository visitRepository;

    @GetMapping
    public List<Entrance> getVisits() {
        return visitRepository.findAll();
    }
}
