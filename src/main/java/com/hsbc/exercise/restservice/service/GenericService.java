package com.hsbc.exercise.restservice.service;

import com.hsbc.exercise.restservice.model.Book;
import com.hsbc.exercise.restservice.persistence.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class GenericService {

    @Autowired
    protected BookRepository bookRepository;

    /**
     * @param partialTitle - title to match partially or fully
     * @return collection of books matching the title.
     */
    public Collection<Book> findByTitle(String partialTitle) {
        return bookRepository.findByTitle(partialTitle);
    }

    public Collection<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> findById(Long bookId) {
        return bookRepository.findById(bookId);
    }

    public Book findByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn);
    }
}
