package com.indexzero.finals.service;

import com.indexzero.finals.dto.EntranceDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

public interface EntranceService {
    ResponseEntity<Page<EntranceDTO>> getEmployeeEntrances(Pageable pageable, Authentication auth);
    ResponseEntity<Page<EntranceDTO>> getAllEntrances(Pageable pageable);

}
