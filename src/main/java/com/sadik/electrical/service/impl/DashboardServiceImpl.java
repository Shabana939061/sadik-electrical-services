package com.sadik.electrical.service.impl;

import com.sadik.electrical.dto.DashboardStatsDto;
import com.sadik.electrical.entity.BookingStatus;
import com.sadik.electrical.repository.BookingRepository;
import com.sadik.electrical.repository.CustomerRepository;
import com.sadik.electrical.repository.ServiceRepository;
import com.sadik.electrical.service.DashboardService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class DashboardServiceImpl implements DashboardService {

    private final BookingRepository bookingRepository;
    private final ServiceRepository serviceRepository;
    private final CustomerRepository customerRepository;

    public DashboardServiceImpl(BookingRepository bookingRepository,
                                ServiceRepository serviceRepository,
                                CustomerRepository customerRepository) {
        this.bookingRepository = bookingRepository;
        this.serviceRepository = serviceRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public DashboardStatsDto getDashboardStats() {
        long total = bookingRepository.count();
        long pending = bookingRepository.countByStatus(BookingStatus.PENDING);
        long accepted = bookingRepository.countByStatus(BookingStatus.ACCEPTED);
        long completed = bookingRepository.countByStatus(BookingStatus.COMPLETED);
        long cancelled = bookingRepository.countByStatus(BookingStatus.CANCELLED);
        long totalServices = serviceRepository.count();
        long totalCustomers = customerRepository.count();

        return new DashboardStatsDto(total, pending, accepted, completed, cancelled, totalServices, totalCustomers);
    }
}
