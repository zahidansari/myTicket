package com.myticket.MyTicket.service.booking;

import com.myticket.MyTicket.model.Booking;
import com.myticket.MyTicket.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    public Booking createBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Optional<Booking> getBookingById(Long id) {
        return bookingRepository.findById(id);
    }

    public List<Booking> getBookingsByUserId(Long userId) {
        return bookingRepository.findByUserId(userId);
    }

    public List<Booking> getBookingsByShowtimeId(Long showtimeId) {
        return bookingRepository.findByShowtimeId(showtimeId);
    }
    public List<Integer> findDuplicateInts(List<Integer> numbers) {
        List<Integer> duplicates = new ArrayList<>();
        if (numbers == null || numbers.isEmpty()) {
            return duplicates;
        }

        Set<Integer> seen = new HashSet<>();
        Set<Integer> added = new HashSet<>();

        for (Integer n : numbers) {
            if (n == null) continue;
            if (!seen.add(n)) {
                if (added.add(n)) {
                    duplicates.add(n);
                }
            }
        }

        return duplicates;
    }

}

