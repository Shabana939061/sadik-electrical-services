package com.sadik.electrical.config;

import com.sadik.electrical.entity.*;
import com.sadik.electrical.repository.AdminRepository;
import com.sadik.electrical.repository.BookingRepository;
import com.sadik.electrical.repository.CustomerRepository;
import com.sadik.electrical.repository.ServiceRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final AdminRepository adminRepository;
    private final ServiceRepository serviceRepository;
    private final CustomerRepository customerRepository;
    private final BookingRepository bookingRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(AdminRepository adminRepository,
                           ServiceRepository serviceRepository,
                           CustomerRepository customerRepository,
                           BookingRepository bookingRepository,
                           PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.serviceRepository = serviceRepository;
        this.customerRepository = customerRepository;
        this.bookingRepository = bookingRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        seedAdmin();
        seedServices();
        seedSampleBookings();
    }

    private void seedAdmin() {
        if (!adminRepository.existsByUsername("admin")) {
            Admin admin = new Admin(
                    "admin",
                    passwordEncoder.encode("admin123"),
                    "Sadik (Master Electrician)",
                    "sadikelectricalservices@gmail.com",
                    "ROLE_ADMIN"
            );
            adminRepository.save(admin);
            System.out.println(">>> Initialized Default Admin Account: Username: admin | Password: admin123");
        }
    }

    private void seedServices() {
        if (serviceRepository.count() == 0) {
            List<ServiceEntity> services = List.of(
                new ServiceEntity(
                    "Home Wiring",
                    "home-wiring",
                    "New house wiring, electrical connections and wiring replacement.",
                    "Complete electrical wiring solutions for newly constructed residential houses and commercial buildings. We specialize in concealed conduit pipe fitting, electrical load balancing, distribution board (DB) wiring, earth pit grounding, and complete rewiring of older properties using top-grade ISI-certified copper cables for maximum fire resistance.",
                    "bi-plug-fill",
                    "/images/services/home-wiring.svg",
                    new BigDecimal("1499.00"),
                    1
                ),
                new ServiceEntity(
                    "Electrical Repairs",
                    "electrical-repairs",
                    "Troubleshooting and repairing electrical problems.",
                    "Fast doorstep emergency troubleshooting for all types of home and shop electrical faults. We quickly identify and fix short circuits, tripped MCBs, voltage surges, burning wire smells, sparking switches, loose connections, and power failures with advanced testing meters.",
                    "bi-wrench-adjustable-circle-fill",
                    "/images/services/electrical-repairs.svg",
                    new BigDecimal("299.00"),
                    2
                ),
                new ServiceEntity(
                    "Fan and Light Installation",
                    "fan-and-light-installation",
                    "Installing ceiling fans, LED lights and lighting fixtures.",
                    "Professional mounting, assembly, and wiring for all models of ceiling fans, BLDC high-efficiency smart fans, exhaust fans, chandelier lights, LED panel spotlights, track lights, tube lights, and outdoor floodlights with sturdy, vibration-free ceiling brackets.",
                    "bi-lightbulb-fill",
                    "/images/services/fan-light.svg",
                    new BigDecimal("199.00"),
                    3
                ),
                new ServiceEntity(
                    "Switchboard Repairs",
                    "switchboard-repairs",
                    "Switch, socket and electrical board repairs.",
                    "Comprehensive repair, installation, and replacement of modular switch plates, 6A/16A power sockets, fan regulators, indicator lamps, bell indicators, fuse units, and main distribution box breakers using certified durable electrical components.",
                    "bi-toggles",
                    "/images/services/switchboard.svg",
                    new BigDecimal("249.00"),
                    4
                ),
                new ServiceEntity(
                    "Inverter Installation",
                    "inverter-installation",
                    "Inverter and battery installation and connections.",
                    "Seamless power backup setup with professional inverter and tubular battery installation. Includes dedicated inverter wiring loops, changeover switch setup, heavy-gauge battery terminal connections, terminal corrosion coating, and load calculation to keep your lights and fans running uninterrupted.",
                    "bi-battery-charging",
                    "/images/services/inverter.svg",
                    new BigDecimal("799.00"),
                    5
                ),
                new ServiceEntity(
                    "Electrical Maintenance",
                    "electrical-maintenance",
                    "Electrical inspections and regular maintenance.",
                    "Preventative electrical checkups and thorough safety audits for residential homes, villas, retail shops, and commercial offices in Chilakaluripet. We test earth leakage, verify breaker trip sensitivity, balance load across phases, and inspect older wiring before problems occur.",
                    "bi-shield-fill-check",
                    "/images/services/maintenance.svg",
                    new BigDecimal("499.00"),
                    6
                ),
                new ServiceEntity(
                    "Ceiling Top Lighting",
                    "ceiling-top-lighting",
                    "Decorative ceiling lights, LED strip lights and false ceiling lighting installation.",
                    "Elevate your home aesthetics with modern false ceiling POP lighting, warm/neutral COB concealed spotlights, profile LED channels, magnetic track lighting, wall accent strips, and RGB remote-controlled mood lighting tailored to your living room and bedrooms.",
                    "bi-brightness-high-fill",
                    "/images/services/ceiling-lighting.svg",
                    new BigDecimal("899.00"),
                    7
                )
            );
            serviceRepository.saveAll(services);
            System.out.println(">>> Initialized 7 Core Electrical Services for Sadik Electrical Services.");
        }
    }

    private void seedSampleBookings() {
        if (bookingRepository.count() == 0) {
            ServiceEntity wiringService = serviceRepository.findBySlug("home-wiring").orElse(null);
            ServiceEntity repairService = serviceRepository.findBySlug("electrical-repairs").orElse(null);
            ServiceEntity inverterService = serviceRepository.findBySlug("inverter-installation").orElse(null);
            ServiceEntity lightingService = serviceRepository.findBySlug("ceiling-top-lighting").orElse(null);

            if (wiringService != null && repairService != null) {
                // Sample 1: Pending Booking
                Customer c1 = new Customer("Ramesh Babu", "9848022334", "ramesh.babu@gmail.com", "Near Clock Tower, Main Road, Chilakaluripet, AP");
                customerRepository.save(c1);
                Booking b1 = new Booking("SES-2026-1001", c1, repairService, LocalDate.now().plusDays(1), "10:00 AM - 01:00 PM", "Frequent MCB tripping in kitchen and burning smell near refrigerator socket.");
                b1.setStatus(BookingStatus.PENDING);
                b1.setAdminNotes("Customer called in advance. Needs urgent inspection on kitchen circuit.");
                bookingRepository.save(b1);

                // Sample 2: Accepted Booking
                Customer c2 = new Customer("Suresh Kumar", "9440155678", "suresh.k@outlook.com", "Venkateswara Colony, Ganapavaram Road, Chilakaluripet, AP");
                customerRepository.save(c2);
                Booking b2 = new Booking("SES-2026-1002", c2, inverterService != null ? inverterService : repairService, LocalDate.now().plusDays(2), "02:00 PM - 05:00 PM", "Luminous 1050VA Inverter and 150Ah battery installation for 2BHK house.");
                b2.setStatus(BookingStatus.ACCEPTED);
                b2.setAdminNotes("Technician assigned. Scheduled for afternoon slot.");
                bookingRepository.save(b2);

                // Sample 3: Completed Booking
                Customer c3 = new Customer("Venkat Rao", "9390112233", "venkat.rao@yahoo.com", "Purushothapatnam Bypass, Chilakaluripet, AP");
                customerRepository.save(c3);
                Booking b3 = new Booking("SES-2026-1003", c3, lightingService != null ? lightingService : wiringService, LocalDate.now().minusDays(1), "11:00 AM - 02:00 PM", "Living room false ceiling LED profile strip and 8 COB spot lights installation.");
                b3.setStatus(BookingStatus.COMPLETED);
                b3.setAdminNotes("Completed successfully on time. Customer satisfied with warm white glow.");
                bookingRepository.save(b3);

                System.out.println(">>> Initialized Sample Bookings for demonstration.");
            }
        }
    }
}
