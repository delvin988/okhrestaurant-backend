package com.app.okhrestaurant.service.customer.impl;

import com.app.okhrestaurant.dto.CustomerDetailDTO;
import com.app.okhrestaurant.dto.CustomerSummaryDTO;
import com.app.okhrestaurant.entity.Booking;
import com.app.okhrestaurant.repository.BookingRepository;
import com.app.okhrestaurant.service.customer.CustomerSC;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class CustomerSCImpl
        implements CustomerSC {

    private final BookingRepository bookingRepository;

    public CustomerSCImpl(
            BookingRepository bookingRepository
    ) {
        this.bookingRepository =
                bookingRepository;
    }

    @Override
    public List<CustomerSummaryDTO>
    getCustomers() {

        List<Booking> bookings =
                bookingRepository.findAll();

        Map<String, List<Booking>>
                grouped =
                new HashMap<>();

        for (Booking booking : bookings) {

            grouped.computeIfAbsent(
                    booking.getPhone(),
                    k -> new ArrayList<>()
            ).add(
                    booking
            );
        }

        List<CustomerSummaryDTO>
                result =
                new ArrayList<>();

        for (
                List<Booking> customerBookings
                : grouped.values()
        ) {

            Booking latest =
                    customerBookings
                            .stream()
                            .max(
                                    Comparator.comparing(
                                            Booking::getBookingTime
                                    )
                            )
                            .orElse(null);

            result.add(
                    new CustomerSummaryDTO(
                            latest.getCustomerName(),
                            latest.getPhone(),
                            latest.getEmail(),
                            (long) customerBookings.size(),
                            latest.getBookingTime()
                                    .format(
                                            DateTimeFormatter.ofPattern(
                                                    "dd-MMM-yyyy HH:mm"
                                            )
                                    )
                    )
            );
        }

        result.sort(
                Comparator.comparing(
                        CustomerSummaryDTO::getTotalReservations
                ).reversed()
        );

        return result;
    }

    @Override
    public CustomerDetailDTO
    getCustomerDetail(
            String phone
    ) {

        List<Booking> bookings =
                bookingRepository
                        .findByPhoneOrderByBookingTimeDesc(
                                phone
                        );

        if (bookings.isEmpty()) {

            throw new RuntimeException(
                    "Customer not found"
            );
        }

        Booking latest =
                bookings.get(0);

        Set<String> names =
                new LinkedHashSet<>();

        Set<String> emails =
                new LinkedHashSet<>();

        for (Booking booking : bookings) {

            if (
                    booking.getCustomerName()
                            != null
                            &&
                            !booking.getCustomerName()
                                    .trim()
                                    .isEmpty()
            ) {

                names.add(
                        booking.getCustomerName()
                );
            }

            if (
                    booking.getEmail()
                            != null
                            &&
                            !booking.getEmail()
                                    .trim()
                                    .isEmpty()
            ) {

                emails.add(
                        booking.getEmail()
                );
            }
        }

        CustomerDetailDTO dto =
                new CustomerDetailDTO();

        dto.setPhone(
                latest.getPhone()
        );

        dto.setEmail(
                latest.getEmail()
        );

        dto.setNames(
                new ArrayList<>(
                        names
                )
        );

        dto.setEmails(
                new ArrayList<>(
                        emails
                )
        );

        dto.setReservations(
                bookings
        );

        return dto;
    }

    @Override
    public byte[] exportCustomers() {

        List<CustomerSummaryDTO>
                customers =
                getCustomers();

        StringBuilder csv =
                new StringBuilder();

        csv.append(
                "Name,Phone,Email,Total Reservations,Last Reservation\n"
        );

        for (
                CustomerSummaryDTO customer
                : customers
        ) {

            csv.append(
                    "\"" +
                            customer.getCustomerName() +
                            "\","
            );

            csv.append(
                    "\"" +
                            customer.getPhone() +
                            "\","
            );

            csv.append(
                    "\"" +
                            customer.getEmail() +
                            "\","
            );

            csv.append(
                    customer.getTotalReservations()
            );

            csv.append(
                    ","
            );

            csv.append(
                    "\"" +
                            customer.getLastReservation() +
                            "\"\n"
            );
        }

        return csv
                .toString()
                .getBytes();
    }

    @Override
    public byte[] exportCustomerHistory(
            String phone
    ) {

        List<Booking> bookings =
                bookingRepository
                        .findByPhoneOrderByBookingTimeDesc(
                                phone
                        );

        StringBuilder csv =
                new StringBuilder();

        csv.append(
                "Date,Guests,Status\n"
        );

        for (
                Booking booking
                : bookings
        ) {

            csv.append(
                    "\"" +
                            booking.getBookingTime() +
                            "\","
            );

            csv.append(
                    booking.getNumberOfPeople()
            );

            csv.append(
                    ","
            );

            csv.append(
                    "\"" +
                            booking.getStatus() +
                            "\"\n"
            );
        }

        return csv
                .toString()
                .getBytes();
    }
}