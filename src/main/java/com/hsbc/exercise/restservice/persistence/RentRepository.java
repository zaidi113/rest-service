package com.hsbc.exercise.restservice.persistence;

import com.hsbc.exercise.restservice.model.Book;
import com.hsbc.exercise.restservice.model.User;
import com.hsbc.exercise.restservice.model.Rent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.Optional;

public interface RentRepository extends JpaRepository<Rent, Long> {


    @Query("select r from Rent r where r.user.id = ?1")
    Collection<Rent> findAllByUserId(Long userId);

    @Query("select r from Rent r where r.user.id = ?1 and r.book.isbn = ?2")
    Optional<Rent> findByUserAndIsbn(Long userId, String isbn);

    @Query("select r from Rent r where r.book.isbn = ?1")
    Collection<Rent> findByIsbn(String isbn);


}
