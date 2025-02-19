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
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    CodeRepository codeRepository;
    @Autowired
    private EntranceRepository entranceRepository;

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
            if (codeRepository.existsByValue(Long.valueOf(code))) {
                Employee employee = employeeRepository.findByLogin(auth.getName());
                Entrance entrance = new Entrance();

                entrance.setVisitTime(LocalDateTime.now());
                entrance.setReader(codeRepository.findByValue(code));
                entrance.setType("smartphone");
                entrance.setEmployee(employee);

                entranceRepository.save(entrance);

                // employeeRepository.save(employee);

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
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
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
                    e.setIsEmpEnabled(true);
                    employeeRepository.save(e);
                    return new ResponseEntity<>(HttpStatus.OK);
                }
                else if(state.equals("blocked")) {
                    e.setIsEmpEnabled(false);
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
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        return new ResponseEntity<>(employeeRepository.findAll().stream().map(EmployeeMapper::convertToDTO).collect(Collectors.toList()), HttpStatus.OK);
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
