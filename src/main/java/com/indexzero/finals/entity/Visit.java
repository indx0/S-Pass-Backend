package com.indexzero.finals.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.apache.catalina.User;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "employee_visits")
public class Visit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "visit_time")
    private Date visitTime;

    @Column(name = "type")
    private String type;

}
