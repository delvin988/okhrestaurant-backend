package com.app.okhrestaurant.controller;

import com.app.okhrestaurant.entity.Booking;
import com.app.okhrestaurant.service.BookingService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public Map<String, Object> createBooking(
            @RequestBody Booking booking
    ) {

        Booking saved =
                bookingService.createBooking(booking);

        Map<String, Object> response =
                new HashMap<>();

        response.put("bookingId", saved.getId());

        response.put("message", "Booking created");

        return response;
    }

    @GetMapping("/today")
    public List<Booking> getTodayBookings() {

        return bookingService
                .getTodayBookings();
    }

    @PutMapping("/{id}/status")
    public Booking updateStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> req
    ) {
        return bookingService.updateStatus(
                id,
                req.get("status")
        );
    }

    @GetMapping("/reservation")
    public List<Booking> getRangeBookings( @RequestParam String startDate,  @RequestParam String endDate) {
        return bookingService.getRangeBooking(startDate, endDate);
    }
    @GetMapping("/upcoming")
    public List<Booking> getUpcomingBookings() {

        return bookingService
                .getUpcomingBookings();
    }
}