package com.indexzero.finals.repository;

import com.indexzero.finals.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findByLogin(String login);
}
