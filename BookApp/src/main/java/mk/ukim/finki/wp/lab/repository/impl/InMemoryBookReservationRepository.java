package mk.ukim.finki.wp.lab.repository.impl;

import lombok.Data;
import mk.ukim.finki.wp.lab.bootstrap.DataHolder;
import mk.ukim.finki.wp.lab.model.BookReservation;
import mk.ukim.finki.wp.lab.repository.BookReservationRepository;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryBookReservationRepository implements BookReservationRepository {
    @Override
    public BookReservation save(BookReservation reservation) {
        DataHolder.reservations.removeIf(b -> b.getBookTitle().equals(reservation.getBookTitle()));
        DataHolder.reservations.add(reservation);
        return reservation;
    }
}
