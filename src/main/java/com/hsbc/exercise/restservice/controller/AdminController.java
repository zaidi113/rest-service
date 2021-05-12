package com.hsbc.exercise.restservice.controller;

import com.hsbc.exercise.restservice.model.Book;
import com.hsbc.exercise.restservice.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/book")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public Book addNewBook(@RequestBody Book book){
        try{
            return adminService.save(book);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed To create Book", e);
        }
    }

    @PutMapping("/book")
    @ResponseBody
    public Book update(@RequestBody Book book){
        Optional<Book> existingBook = adminService.findById(book.getId());
        if(existingBook.isPresent()){
            return adminService.save(book);
        }
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "No such book to update");
    }

    @DeleteMapping("/book/{bookId}")
    public void removeBook(@PathVariable Long bookId){
        try{
        adminService.deleteBook(bookId);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed To delete Book", e);
        }
    }

}