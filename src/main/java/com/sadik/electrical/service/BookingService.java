package com.sadik.electrical.service;

import com.sadik.electrical.dto.BookingRequestDto;
import com.sadik.electrical.entity.Booking;
import com.sadik.electrical.entity.BookingStatus;

import java.util.List;
import java.util.Optional;

public interface BookingService {
    Booking createBooking(BookingRequestDto bookingRequestDto);
    List<Booking> getAllBookings();
    List<Booking> getBookingsByStatus(BookingStatus status);
    List<Booking> getRecentBookings(int limit);
    List<Booking> searchBookings(String query);
    Optional<Booking> getBookingById(Long id);
    Optional<Booking> getBookingByReference(String bookingReference);
    Booking updateBookingStatus(Long id, BookingStatus status, String adminNotes);
    Booking updateBookingDetails(Long id, BookingRequestDto dto, String adminNotes);
    void deleteBooking(Long id);
    long countBookings();
    long countBookingsByStatus(BookingStatus status);
}
