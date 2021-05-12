package com.hsbc.exercise.restservice.model;

import javax.persistence.*;

@Entity
public class User extends Identifiable {


    @Column(name = "first_name")
    private String firstName;

    private String lastName;
    private String address;

    public User() {
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }
}
