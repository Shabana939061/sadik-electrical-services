package com.sadik.electrical.service.impl;

import com.sadik.electrical.dto.BookingRequestDto;
import com.sadik.electrical.entity.Booking;
import com.sadik.electrical.entity.BookingStatus;
import com.sadik.electrical.entity.Customer;
import com.sadik.electrical.entity.ServiceEntity;
import com.sadik.electrical.repository.BookingRepository;
import com.sadik.electrical.repository.CustomerRepository;
import com.sadik.electrical.repository.ServiceRepository;
import com.sadik.electrical.service.BookingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
@Transactional
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final CustomerRepository customerRepository;
    private final ServiceRepository serviceRepository;

    public BookingServiceImpl(BookingRepository bookingRepository,
                              CustomerRepository customerRepository,
                              ServiceRepository serviceRepository) {
        this.bookingRepository = bookingRepository;
        this.customerRepository = customerRepository;
        this.serviceRepository = serviceRepository;
    }

    @Override
    public Booking createBooking(BookingRequestDto dto) {
        // Find existing customer by mobile or create a new one
        Customer customer = customerRepository.findByMobile(dto.getMobileNumber().trim())
                .orElseGet(() -> new Customer(
                        dto.getCustomerName().trim(),
                        dto.getMobileNumber().trim(),
                        dto.getEmail() != null ? dto.getEmail().trim() : null,
                        dto.getAddress().trim()
                ));

        // Update details if already exists
        customer.setName(dto.getCustomerName().trim());
        if (dto.getEmail() != null && !dto.getEmail().isBlank()) {
            customer.setEmail(dto.getEmail().trim());
        }
        customer.setAddress(dto.getAddress().trim());
        Customer savedCustomer = customerRepository.save(customer);

        // Fetch electrical service
        ServiceEntity service = serviceRepository.findById(dto.getServiceId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Service ID: " + dto.getServiceId()));

        // Generate unique booking reference (e.g., SES-2026-8921)
        String bookingRef = generateUniqueReference();

        Booking booking = new Booking(
                bookingRef,
                savedCustomer,
                service,
                dto.getPreferredDate(),
                dto.getPreferredTime(),
                dto.getProblemDescription().trim()
        );

        return bookingRepository.save(booking);
    }

    private String generateUniqueReference() {
        int year = LocalDate.now().getYear();
        String ref;
        do {
            int randomNum = ThreadLocalRandom.current().nextInt(1000, 9999);
            ref = "SES-" + year + "-" + randomNum;
        } while (bookingRepository.findByBookingReference(ref).isPresent());
        return ref;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Booking> getAllBookings() {
        return bookingRepository.findAllByOrderByCreatedAtDesc();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Booking> getBookingsByStatus(BookingStatus status) {
        return bookingRepository.findByStatusOrderByCreatedAtDesc(status);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Booking> getRecentBookings(int limit) {
        return bookingRepository.findTop10ByOrderByCreatedAtDesc();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Booking> searchBookings(String query) {
        if (query == null || query.isBlank()) {
            return getAllBookings();
        }
        return bookingRepository.searchBookings(query.trim());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Booking> getBookingById(Long id) {
        return bookingRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Booking> getBookingByReference(String bookingReference) {
        return bookingRepository.findByBookingReference(bookingReference);
    }

    @Override
    public Booking updateBookingStatus(Long id, BookingStatus status, String adminNotes) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found with ID: " + id));

        booking.setStatus(status);
        if (adminNotes != null) {
            booking.setAdminNotes(adminNotes);
        }
        return bookingRepository.save(booking);
    }

    @Override
    public Booking updateBookingDetails(Long id, BookingRequestDto dto, String adminNotes) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found with ID: " + id));

        Customer customer = booking.getCustomer();
        customer.setName(dto.getCustomerName().trim());
        customer.setMobile(dto.getMobileNumber().trim());
        if (dto.getEmail() != null) {
            customer.setEmail(dto.getEmail().trim());
        }
        customer.setAddress(dto.getAddress().trim());
        customerRepository.save(customer);

        ServiceEntity service = serviceRepository.findById(dto.getServiceId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Service ID: " + dto.getServiceId()));

        booking.setService(service);
        booking.setPreferredDate(dto.getPreferredDate());
        booking.setPreferredTime(dto.getPreferredTime());
        booking.setProblemDescription(dto.getProblemDescription().trim());
        if (adminNotes != null) {
            booking.setAdminNotes(adminNotes);
        }

        return bookingRepository.save(booking);
    }

    @Override
    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public long countBookings() {
        return bookingRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public long countBookingsByStatus(BookingStatus status) {
        return bookingRepository.countByStatus(status);
    }
}
