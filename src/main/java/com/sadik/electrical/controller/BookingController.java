package com.sadik.electrical.controller;

import com.sadik.electrical.dto.BookingRequestDto;
import com.sadik.electrical.entity.Booking;
import com.sadik.electrical.service.BookingService;
import com.sadik.electrical.service.ServiceCatalogService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
@RequestMapping("/booking")
public class BookingController {

    private final BookingService bookingService;
    private final ServiceCatalogService serviceCatalogService;

    public BookingController(BookingService bookingService, ServiceCatalogService serviceCatalogService) {
        this.bookingService = bookingService;
        this.serviceCatalogService = serviceCatalogService;
    }

    @GetMapping
    public String showBookingForm(@RequestParam(value = "serviceId", required = false) Long serviceId,
                                  Model model) {
        if (!model.containsAttribute("bookingRequest")) {
            BookingRequestDto dto = new BookingRequestDto();
            dto.setPreferredDate(LocalDate.now());
            if (serviceId != null) {
                dto.setServiceId(serviceId);
            }
            model.addAttribute("bookingRequest", dto);
        }
        model.addAttribute("services", serviceCatalogService.getAllActiveServices());
        model.addAttribute("selectedServiceId", serviceId);
        model.addAttribute("minDate", LocalDate.now());
        model.addAttribute("activeNav", "booking");
        return "booking";
    }

    @PostMapping
    public String submitBooking(@Valid @ModelAttribute("bookingRequest") BookingRequestDto bookingRequestDto,
                                BindingResult bindingResult,
                                Model model,
                                RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("services", serviceCatalogService.getAllActiveServices());
            model.addAttribute("minDate", LocalDate.now());
            model.addAttribute("activeNav", "booking");
            return "booking";
        }

        try {
            Booking booking = bookingService.createBooking(bookingRequestDto);
            redirectAttributes.addFlashAttribute("successMessage", 
                    "Booking successfully placed! Our electrician will contact you shortly.");
            return "redirect:/booking/confirm/" + booking.getBookingReference();
        } catch (Exception e) {
            model.addAttribute("services", serviceCatalogService.getAllActiveServices());
            model.addAttribute("errorMessage", "Failed to submit booking: " + e.getMessage());
            model.addAttribute("minDate", LocalDate.now());
            model.addAttribute("activeNav", "booking");
            return "booking";
        }
    }

    @GetMapping("/confirm/{reference}")
    public String showConfirmation(@PathVariable("reference") String reference, Model model) {
        Booking booking = bookingService.getBookingByReference(reference)
                .orElse(null);

        if (booking == null) {
            return "redirect:/booking";
        }

        model.addAttribute("booking", booking);
        model.addAttribute("activeNav", "booking");
        return "booking-confirmation";
    }
}
