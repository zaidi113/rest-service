package com.hsbc.exercise.restservice.controller;

import com.hsbc.exercise.restservice.model.Book;
import com.hsbc.exercise.restservice.model.Rent;
import com.hsbc.exercise.restservice.service.GenericService;
import com.hsbc.exercise.restservice.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;

@RestController
public class RentalController extends GenericService {

    @Autowired
    private RentalService rentalService;

    @GetMapping("/books")
    @ResponseBody
    public Collection<Book> findAllBooks(){
        return rentalService.findAllBooks();
    }

    @GetMapping("/books/title/{title}")
    @ResponseBody
    public Collection<Book> findBooksByTitle(@PathVariable String title){
        return rentalService.findByTitle(title);
    }

    @GetMapping("/books/isbn/{isbn}")
    @ResponseBody
    public Book findBookByIsbn(@PathVariable String isbn){
        return rentalService.findByIsbn(isbn);
    }

    @PostMapping("/borrow")
    @ResponseBody
    @ResponseStatus( HttpStatus.CREATED )
    public Rent borrowBook(@RequestParam Long userId, @RequestParam String isbn){
        try{
            return rentalService.borrowBook(userId, isbn);
        }catch (Exception e ){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @PostMapping("/return")
    public void returnBook(@RequestParam Long userId, @RequestParam String isbn){
        rentalService.returnBook(userId, isbn);
    }

    @GetMapping("/rents/{userId}")
    public Collection<Book> booksRentedBy(@PathVariable Long userId){
        return rentalService.getAllRentedBooksBy(userId);
    }
}
