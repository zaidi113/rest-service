package com.hsbc.exercise.restservice.model;

import javax.persistence.*;

@MappedSuperclass
public class Identifiable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
