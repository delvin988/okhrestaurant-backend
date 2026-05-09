package com.app.okhrestaurant.service;

import com.app.okhrestaurant.entity.Booking;
import com.app.okhrestaurant.repository.BookingRP;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class BookingService {

    private final BookingRP bookingRepository;

    public BookingService(BookingRP bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    // CREATE BOOKING + OTP
//    public Booking createBooking(Booking booking) {
//
//        String otp = String.valueOf(100000 + new Random().nextInt(900000));
//
//        booking.setOtp(otp);
//        booking.setOtpExpiredAt(LocalDateTime.now().plusMinutes(5));
//        booking.setStatus("PENDING");
//
//        Booking saved = bookingRepository.save(booking);
//
//        // sementara kita print dulu (nanti ganti SMS)
//        System.out.println("OTP untuk booking ID " + saved.getId() + ": " + otp);
//
//        return saved;
//    }

    // VERIFY OTP
    public Booking createBooking(Booking booking) {

        booking.setStatus("PENDING");

        booking.setCreatedAt(LocalDateTime.now());

        booking.setUpdatedAt(LocalDateTime.now());

        return bookingRepository.save(booking);
    }

    public List<Booking> getTodayBookings() {

        LocalDate today = LocalDate.now();

        LocalDateTime start =
                today.atStartOfDay();

        LocalDateTime end =
                today.atTime(23, 59, 59);

        return bookingRepository
                .findByBookingTimeBetween(
                        start,
                        end
                );
    }

    public Booking updateStatus(
            Long bookingId,
            String status
    ) {

        Booking booking =
                bookingRepository.findById(
                        bookingId
                ).orElseThrow(
                        () -> new RuntimeException(
                                "Booking not found"
                        )
                );

        booking.setStatus(status);

        booking.setUpdatedAt(
                LocalDateTime.now()
        );

        return bookingRepository.save(booking);
    }
}