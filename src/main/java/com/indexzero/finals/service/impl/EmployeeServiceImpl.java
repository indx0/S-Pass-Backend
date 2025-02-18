package com.indexzero.finals.service.impl;

import com.indexzero.finals.dto.EmployeeDTO;
import com.indexzero.finals.entity.Employee;
import com.indexzero.finals.entity.Visit;
import com.indexzero.finals.repository.CodeRepository;
import com.indexzero.finals.repository.EmployeeRepository;
import com.indexzero.finals.service.EmployeeService;
import com.indexzero.finals.util.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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

    @Override
    public ResponseEntity<EmployeeDTO> getUserInfo(Authentication auth) {
        return new ResponseEntity<>(EmployeeMapper.convertToDTO(employeeRepository.findByLogin(auth.getName())), HttpStatus.OK);
    }

    public ResponseEntity<Object> openTheDoor(Long code, Authentication auth) {
        try {
            if(codeRepository.findByValue(code).isActive()) {
                if (codeRepository.existsByValue(Long.valueOf(code))) {
                    Employee employee = employeeRepository.findByLogin(auth.getName());

                    Visit visit = new Visit();

                    LocalDateTime time = LocalDateTime.now();
                    visit.setVisitTime(LocalDateTime.now());
                    visit.setType("smartphone");

                    List<Visit> v = employee.getVisits();
                    v.add(visit);

                    employee.setVisits(v);

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
