package com.indexzero.finals.service.impl;

import com.indexzero.finals.dto.EmployeeDTO;
import com.indexzero.finals.entity.Employee;
import com.indexzero.finals.entity.Entrance;
import com.indexzero.finals.repository.CodeRepository;
import com.indexzero.finals.repository.EmployeeRepository;
import com.indexzero.finals.repository.EntranceRepository;
import com.indexzero.finals.service.EmployeeService;
import com.indexzero.finals.util.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import java.util.Objects;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    CodeRepository codeRepository;
    @Autowired
    private EntranceRepository entranceRepository;


    @Override
    public ResponseEntity<EmployeeDTO> getUserInfo(Authentication auth) {
        return new ResponseEntity<>(EmployeeMapper.convertToDTO(employeeRepository.findByLogin(auth.getName())), HttpStatus.OK);
    }

    public ResponseEntity<Object> openTheDoor(Long code, Authentication auth) {
        try {
            if (codeRepository.existsByValue(Long.valueOf(code))) {
                Employee employee = employeeRepository.findByLogin(auth.getName());
                Entrance entrance = new Entrance();

                entrance.setVisitTime(LocalDateTime.now());
                entrance.setReader(codeRepository.findByValue(code));
                entrance.setType("smartphone");
                entrance.setEmployee(employee);

                entranceRepository.save(entrance);


                return new ResponseEntity<>(HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<HttpStatusCode> deleteEmployee(String login) {
        Employee e = employeeRepository.findByLogin(login);
        if(e != null) {
            if (Objects.equals(e.getAuthorities().iterator().next().getAuthority(), "ADMIN")) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
            else {
                employeeRepository.delete(e);
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<HttpStatusCode> changeState(String login, String state) {
        Employee e = employeeRepository.findByLogin(login);
        if(e != null) {
            if (Objects.equals(e.getAuthorities().iterator().next().getAuthority(), "ADMIN")) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            else {
                if(state.equals("active")) {
                    e.setIsQREnabled(true);
                    employeeRepository.save(e);
                    return new ResponseEntity<>(HttpStatus.OK);
                }
                else if(state.equals("blocked")) {
                    e.setIsQREnabled(false);
                    employeeRepository.save(e);
                    return new ResponseEntity<>(HttpStatus.OK);
                }
                else {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            }
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<Page<EmployeeDTO>> getAllEmployees(Pageable pageable) {
        return new ResponseEntity<>(employeeRepository.findAll(pageable).map(EmployeeMapper::convertToDTO), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<EmployeeDTO> getEmployeeByLogin(String login) {
        Employee e = employeeRepository.findByLogin(login);
        if(e != null) {
            return new ResponseEntity<>(EmployeeMapper.convertToDTO(e), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
