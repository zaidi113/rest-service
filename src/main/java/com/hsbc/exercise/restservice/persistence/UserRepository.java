package com.hsbc.exercise.restservice.persistence;

import com.hsbc.exercise.restservice.model.Book;
import com.hsbc.exercise.restservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface UserRepository extends JpaRepository<User, Long> {

}
