package com.myticket.MyTicket.service.booking;

import com.myticket.MyTicket.model.Booking;
import com.myticket.MyTicket.repository.BookingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private BookingService bookingService;

    private Booking sampleBooking;

    @BeforeEach
    void setUp() {
        sampleBooking = new Booking();
        sampleBooking.setId(1L);
        sampleBooking.setUserId(10L);
        sampleBooking.setShowtimeId(20L);
        sampleBooking.setNumSeats(2);
        sampleBooking.setTotalAmount(25.0);
        sampleBooking.setStatus("CONFIRMED");
    }

    @Test
    void createBooking_WhenBookingIsValid_ShouldSaveAndReturnBooking() {
        when(bookingRepository.save(sampleBooking)).thenReturn(sampleBooking);

        Booking result = bookingService.createBooking(sampleBooking);

        assertThat(result).isSameAs(sampleBooking);
        assertEquals(1L, result.getId());
        verify(bookingRepository).save(sampleBooking);
    }

    @Test
    void getAllBookings_WhenRepositoryReturnsBookings_ShouldReturnAllBookings() {
        List<Booking> bookings = Arrays.asList(sampleBooking);
        when(bookingRepository.findAll()).thenReturn(bookings);

        List<Booking> result = bookingService.getAllBookings();

        assertThat(result).containsExactlyElementsOf(bookings);
        verify(bookingRepository).findAll();
    }

    @Test
    void getBookingById_WhenBookingExists_ShouldReturnOptionalWithBooking() {
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(sampleBooking));

        Optional<Booking> result = bookingService.getBookingById(1L);

        assertThat(result).isPresent();
        assertThat(result.get()).isSameAs(sampleBooking);
        verify(bookingRepository).findById(1L);
    }

    @Test
    void getBookingById_WhenBookingDoesNotExist_ShouldReturnEmptyOptional() {
        when(bookingRepository.findById(5L)).thenReturn(Optional.empty());

        Optional<Booking> result = bookingService.getBookingById(5L);

        assertThat(result).isNotPresent();
        verify(bookingRepository).findById(5L);
    }

    @Test
    void getBookingsByUserId_WhenBookingsExist_ShouldReturnUserBookings() {
        Booking booking2 = new Booking();
        booking2.setUserId(10L);
        List<Booking> userBookings = Arrays.asList(sampleBooking, booking2);
        when(bookingRepository.findByUserId(10L)).thenReturn(userBookings);

        List<Booking> result = bookingService.getBookingsByUserId(10L);

        assertThat(result).containsExactlyElementsOf(userBookings);
        verify(bookingRepository).findByUserId(10L);
    }

    @Test
    void getBookingsByShowtimeId_WhenBookingsExist_ShouldReturnShowtimeBookings() {
        Booking booking2 = new Booking();
        booking2.setShowtimeId(20L);
        List<Booking> showtimeBookings = Arrays.asList(sampleBooking, booking2);
        when(bookingRepository.findByShowtimeId(20L)).thenReturn(showtimeBookings);

        List<Booking> result = bookingService.getBookingsByShowtimeId(20L);

        assertThat(result).containsExactlyElementsOf(showtimeBookings);
        verify(bookingRepository).findByShowtimeId(20L);
    }

    @Nested
    class FindDuplicateIntsTests {

        @Test
        void findDuplicateInts_WhenListIsNull_ShouldReturnEmptyList() {
            List<Integer> result = bookingService.findDuplicateInts(null);

            assertThat(result).isEmpty();
        }

        @Test
        void findDuplicateInts_WhenListIsEmpty_ShouldReturnEmptyList() {
            List<Integer> result = bookingService.findDuplicateInts(Collections.emptyList());

            assertThat(result).isEmpty();
        }

        @Test
        void findDuplicateInts_WhenNoDuplicates_ShouldReturnEmptyList() {
            List<Integer> input = Arrays.asList(1, 2, 3, 4);

            List<Integer> result = bookingService.findDuplicateInts(input);

            assertThat(result).isEmpty();
        }

        @Test
        void findDuplicateInts_WhenDuplicatesExist_ShouldReturnOnlyDuplicateValuesOnce() {
            List<Integer> input = Arrays.asList(1, 2, 2, 3, 1, 4, 2);

            List<Integer> result = bookingService.findDuplicateInts(input);

            assertThat(result).containsExactlyInAnyOrder(1, 2);
            assertEquals(2, result.size());
        }

        @Test
        void findDuplicateInts_WhenListContainsNulls_ShouldIgnoreNullValues() {
            List<Integer> input = Arrays.asList(1, null, 1, null, 2, 2);

            List<Integer> result = bookingService.findDuplicateInts(input);

            assertThat(result).containsExactlyInAnyOrder(1, 2);
            assertEquals(2, result.size());
        }
    }
}
