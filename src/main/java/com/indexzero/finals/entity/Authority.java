package com.indexzero.finals.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "authority")
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    Long id;

    @Column(name = "authority", nullable = false)
    String authority;

}

