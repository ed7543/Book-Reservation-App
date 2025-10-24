package mk.ukim.finki.wp.lab.bootstrap;

import jakarta.annotation.PostConstruct;
import mk.ukim.finki.wp.lab.model.Book;
import mk.ukim.finki.wp.lab.model.BookReservation;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataHolder {
    public static List<Book>  books = null;
    public static List<BookReservation> reservations = new ArrayList<>();

    @PostConstruct
    public void init() {
        books = new ArrayList<>();

        books.add(new Book("Harry Potter", "Fantasy", 9.5));
        books.add(new Book("The Hobbit", "Fantasy", 9.2));
        books.add(new Book("1984", "Dystopian", 9.0));
        books.add(new Book("To Kill a Mockingbird", "Classic", 8.9));
        books.add(new Book("The Great Gatsby", "Classic", 8.7));
        books.add(new Book("The Da Vinci Code", "Thriller", 8.3));
        books.add(new Book("Pride and Prejudice", "Romance", 9.1));
        books.add(new Book("The Catcher in the Rye", "Fiction", 8.0));
        books.add(new Book("The Lord of the Rings", "Fantasy", 9.7));
        books.add(new Book("The Alchemist", "Philosophical", 8.5));
        books.add(new Book("Bad Literature", "Fiction", 2.1));
        books.add(new Book("The Worst Cookbook", "Cooking", 0.9));

        reservations = new ArrayList<>();

    }
}
