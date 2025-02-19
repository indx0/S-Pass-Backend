package com.indexzero.finals.service.impl;

import com.indexzero.finals.dto.EntranceDTO;
import com.indexzero.finals.entity.Employee;
import com.indexzero.finals.entity.Entrance;
import com.indexzero.finals.repository.EmployeeRepository;
import com.indexzero.finals.repository.EntranceRepository;
import com.indexzero.finals.service.EntranceService;
import com.indexzero.finals.util.EntranceMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EntranceServiceImpl implements EntranceService {
    private final EmployeeRepository employeeRepository;
    private final EntranceRepository entranceRepository;

    public EntranceServiceImpl(EmployeeRepository employeeRepository, EntranceRepository entranceRepository) {
        this.employeeRepository = employeeRepository;
        this.entranceRepository = entranceRepository;
    }

    @Override
    public ResponseEntity<Page<EntranceDTO>> getEmployeeEntrances(Pageable pageable, Authentication auth) {
        Employee employee = employeeRepository.findByLogin(auth.getName());
        System.out.println(employee.getEntrances().stream().map(EntranceMapper::convertToDTO).collect(Collectors.toList()));
        List<EntranceDTO> entrances = employee.getEntrances().stream().map(EntranceMapper::convertToDTO).collect(Collectors.toList()).reversed();
        Page<EntranceDTO> page = new PageImpl<>(entrances, pageable, entrances.size());
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<EntranceDTO> getLastEntrance(Authentication auth) {
        Employee employee = employeeRepository.findByLogin(auth.getName());
        List<EntranceDTO> entrances = employee.getEntrances().stream().map(EntranceMapper::convertToDTO).toList();
        System.out.println(entrances.getLast());
        return new ResponseEntity<>(entrances.getLast(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Page<EntranceDTO>> getAllEntrances(Pageable pageable) {
        List<Entrance> entrances = entranceRepository.findAll();
        List<EntranceDTO> entrancesdto = entrances.stream().map(EntranceMapper::convertToDTO).collect(Collectors.toList()).reversed();

        Page<EntranceDTO> page = new PageImpl<>(entrancesdto, pageable, entrances.size());
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Page<EntranceDTO>> getEntrancesByLogin(Pageable pageable, String login) {
        Employee e = employeeRepository.findByLogin(login);
        if(e != null) {
            List<Entrance> entrances = e.getEntrances();
            List<EntranceDTO> entrancesdto = entrances.stream().map(EntranceMapper::convertToDTO).collect(Collectors.toList()).reversed();

            Page<EntranceDTO> page = new PageImpl<>(entrancesdto, pageable, entrances.size());

            return new ResponseEntity<>(page, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
