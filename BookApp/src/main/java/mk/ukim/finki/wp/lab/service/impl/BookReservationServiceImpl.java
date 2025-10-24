package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.model.BookReservation;
import mk.ukim.finki.wp.lab.repository.BookReservationRepository;
import mk.ukim.finki.wp.lab.service.BookReservationService;
import org.springframework.stereotype.Service;

@Service
public class BookReservationServiceImpl implements BookReservationService {

    private final BookReservationRepository bookReservationRepository;

    public BookReservationServiceImpl(BookReservationRepository bookReservationRepository) {
        this.bookReservationRepository = bookReservationRepository;
    }

    @Override
    public BookReservation placeReservation(String bookTitle, String readerName, String readerAddress, int numberOfCopies) {
        if (bookTitle == null || readerName == null || readerAddress == null || numberOfCopies <= 0) {
            throw new IllegalArgumentException();
        }
        BookReservation reservation = new BookReservation(bookTitle, readerName, readerAddress, (long) numberOfCopies);

        return this.bookReservationRepository.save(reservation);
    }
}
