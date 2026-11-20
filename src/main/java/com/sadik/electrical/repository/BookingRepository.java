package com.sadik.electrical.repository;

import com.sadik.electrical.entity.Booking;
import com.sadik.electrical.entity.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findAllByOrderByCreatedAtDesc();

    List<Booking> findByStatusOrderByCreatedAtDesc(BookingStatus status);

    List<Booking> findTop10ByOrderByCreatedAtDesc();

    Optional<Booking> findByBookingReference(String bookingReference);

    long countByStatus(BookingStatus status);

    @Query("SELECT b FROM Booking b WHERE " +
           "LOWER(b.bookingReference) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(b.customer.name) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "b.customer.mobile LIKE CONCAT('%', :query, '%') " +
           "ORDER BY b.createdAt DESC")
    List<Booking> searchBookings(@Param("query") String query);
}
