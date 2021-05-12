package com.hsbc.exercise.restservice.service;

import com.hsbc.exercise.restservice.model.Book;
import com.hsbc.exercise.restservice.model.Rent;
import com.hsbc.exercise.restservice.model.User;
import com.hsbc.exercise.restservice.persistence.BookRepository;
import com.hsbc.exercise.restservice.persistence.RentRepository;
import com.hsbc.exercise.restservice.persistence.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.only;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
class RentalServiceTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RentRepository rentRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private RentalService rentalService;

    @Test
    void canBorrowBook() {

        String isbn = "ABCD";
        Book availableBook = new Book("A", isbn, "abcd", 10);
        User user = new User();
        Rent rent = new Rent(availableBook, user, LocalDate.now(), 10);
        Mockito.when(bookRepository.findByIsbn(Mockito.eq(isbn))).thenReturn(availableBook);
        Mockito.when(userRepository.findById(Mockito.eq(1L))).thenReturn(Optional.of(user));
        Mockito.when(rentRepository.findByUserAndIsbn(Mockito.eq(1L), Mockito.eq(isbn))).thenReturn(Optional.empty());
        Mockito.when(rentRepository.save(Mockito.any(Rent.class))).thenReturn(rent);

        Rent rentedBook = rentalService.borrowBook(1L, isbn);

        Assertions.assertEquals(isbn, rentedBook.getBook().getIsbn());
    }

    @Test
    void borrowBookThrowsExceptionIfAllAreRentedOut() {
        String isbn = "ABCD";
        Book availableBook = new Book("A", isbn, "abcd", -1);
        Mockito.when(bookRepository.findByIsbn(Mockito.eq(isbn))).thenReturn(availableBook);
        Mockito.when(rentRepository.findByUserAndIsbn(Mockito.eq(1L), Mockito.eq(isbn))).thenReturn(Optional.empty());

        Assertions.assertThrows(IllegalStateException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                rentalService.borrowBook(1L, isbn);
            }
        });
    }

    @Test
    void borrowTheSameBookThrowsException() {
        String isbn = "ABCD";
        Book availableBook = new Book("A", isbn, "abcd", -1);
        User user = new User();
        Rent rent = new Rent(availableBook, user, LocalDate.now(), 10);
        Mockito.when(bookRepository.findByIsbn(Mockito.eq(isbn))).thenReturn(availableBook);
        Mockito.when(userRepository.findById(Mockito.eq(1L))).thenReturn(Optional.of(user));
        Mockito.when(rentRepository.findByUserAndIsbn(Mockito.eq(1L), Mockito.eq(isbn))).thenReturn(Optional.empty());
        Mockito.when(rentRepository.save(Mockito.any(Rent.class))).thenReturn(rent);

        Assertions.assertThrows(IllegalStateException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                rentalService.borrowBook(1L, isbn);
                rentalService.borrowBook(1L, isbn);
            }
        });
    }

    @Test
    void canReturnRentedBook() {

        String isbn = "ABCD";
        Book rentedBook = new Book("A", isbn, "abcd", 10);
        User user = new User();
        Rent rent = new Rent(rentedBook, user, LocalDate.now(), 10);

        Mockito.when(rentRepository.findByUserAndIsbn(Mockito.eq(1L), Mockito.eq(isbn))).thenReturn(Optional.of(rent));
        rentalService.returnBook(1L, isbn);
        Mockito.verify(rentRepository, Mockito.atMostOnce()).delete(rent);
    }

    @Test
    void returnUnRentedBookThrowsException() {
        String isbn = "ABCD";
        Mockito.when(rentRepository.findByUserAndIsbn(Mockito.eq(1L), Mockito.eq(isbn))).thenReturn(Optional.empty());
        Assertions.assertThrows(IllegalStateException.class, () -> { rentalService.returnBook(1L, isbn); });
    }

    @Configuration
    static class ContextConfiguration {
        @Bean
        public UserRepository userRepository() {
            return mock(UserRepository.class);
        }

        @Bean
        public RentRepository rentRepository() {
            return mock(RentRepository.class);
        }

        @Bean
        public BookRepository bookRepository() {
            return mock(BookRepository.class);
        }

        @Bean
        public RentalService rentalService() {
            return new RentalService();
        }
    }
}