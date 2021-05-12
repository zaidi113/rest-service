package com.hsbc.exercise.restservice.service;

import com.hsbc.exercise.restservice.model.Book;
import com.hsbc.exercise.restservice.model.User;
import com.hsbc.exercise.restservice.model.Rent;
import com.hsbc.exercise.restservice.persistence.UserRepository;
import com.hsbc.exercise.restservice.persistence.RentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RentalService extends GenericService{

    @Autowired
    UserRepository userRepository;
    @Autowired
    RentRepository rentRepository;

    public Rent borrowBook(Long userId, String isbn) {
        Book book = bookRepository.findByIsbn(isbn);
        if(isAvailableForRent(book)){
            Optional<User> user = userRepository.findById(userId);
            if(user.isPresent()){
                Optional<Rent> rent = rentRepository.findByUserAndIsbn(userId, isbn);
                if(rent.isPresent()){
                    throw new IllegalStateException("Book already rented to the user.");
                }
                return rentRepository.save(new Rent(book, user.get(), LocalDate.now(), 14));
            }
        }
        throw new IllegalStateException("All copied are currently rented out.");
    }

    public void returnBook(Long userId, String isbn) {
        Optional<Rent> rent = rentRepository.findByUserAndIsbn(userId, isbn);
        if(!rent.isPresent()){
            throw new IllegalStateException("Book not rented to user.");
        }
        rentRepository.delete(rent.get());
    }

    public Collection<Book> getAllRentedBooksBy(Long userId) {
        return rentRepository.findAllByUserId(userId).stream().map(Rent::getBook).collect(Collectors.toList());
    }

    private boolean isAvailableForRent(Book book){
        Collection<Rent> rentedCopies = rentRepository.findByIsbn(book.getIsbn());
        int copiesInStock = book.getQuantity();
        return copiesInStock - rentedCopies.size() > 0 ;
    }
}
