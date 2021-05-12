package com.hsbc.exercise.restservice.persistence;

import com.hsbc.exercise.restservice.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("select b from Book b where b.title LIKE %:title%")
    Collection<Book> findByTitle(String title);

    Book findByIsbn(String isbn);

}
