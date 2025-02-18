package com.indexzero.finals.repository;

import com.indexzero.finals.entity.Code;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CodeRepository extends JpaRepository<Code, Long> {
    boolean existsByValue(Long value);
    Code findByValue(Long code);
}
