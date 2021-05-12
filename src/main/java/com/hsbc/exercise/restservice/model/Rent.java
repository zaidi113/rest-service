package com.hsbc.exercise.restservice.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.time.LocalDate;

@Entity
public class Rent extends Identifiable {

    @OneToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private LocalDate rentDate;
    private int period;

    public Rent() {
    }

    public Rent(Book book, User user, LocalDate rentDate, int period) {
        this.book = book;
        this.user = user;
        this.rentDate = rentDate;
        this.period = period;
    }

    public Book getBook() {
        return book;
    }

    public User getUser() {
        return user;
    }

    public LocalDate rentDate() {
        return rentDate;
    }

    public int getPeriod() {
        return period;
    }
}
