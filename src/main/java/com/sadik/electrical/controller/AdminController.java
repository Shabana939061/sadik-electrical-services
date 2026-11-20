package com.sadik.electrical.controller;

import com.sadik.electrical.dto.BookingRequestDto;
import com.sadik.electrical.dto.DashboardStatsDto;
import com.sadik.electrical.dto.ServiceDto;
import com.sadik.electrical.entity.Booking;
import com.sadik.electrical.entity.BookingStatus;
import com.sadik.electrical.entity.ServiceEntity;
import com.sadik.electrical.service.BookingService;
import com.sadik.electrical.service.DashboardService;
import com.sadik.electrical.service.ServiceCatalogService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final BookingService bookingService;
    private final ServiceCatalogService serviceCatalogService;
    private final DashboardService dashboardService;

    public AdminController(BookingService bookingService,
                           ServiceCatalogService serviceCatalogService,
                           DashboardService dashboardService) {
        this.bookingService = bookingService;
        this.serviceCatalogService = serviceCatalogService;
        this.dashboardService = dashboardService;
    }

    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "logout", required = false) String logout,
                            Model model,
                            Principal principal) {
        if (principal != null) {
            return "redirect:/admin/dashboard";
        }
        if (error != null) {
            model.addAttribute("loginError", "Invalid username or password. Please try again.");
        }
        if (logout != null) {
            model.addAttribute("logoutMessage", "You have been logged out successfully.");
        }
        return "admin-login";
    }

    @GetMapping({"", "/", "/dashboard"})
    public String dashboard(Model model, Principal principal) {
        DashboardStatsDto stats = dashboardService.getDashboardStats();
        List<Booking> recentBookings = bookingService.getRecentBookings(8);

        model.addAttribute("stats", stats);
        model.addAttribute("recentBookings", recentBookings);
        model.addAttribute("adminUsername", principal != null ? principal.getName() : "Admin");
        model.addAttribute("activeAdminNav", "dashboard");
        return "admin-dashboard";
    }

    @GetMapping("/bookings")
    public String bookingsList(@RequestParam(value = "status", required = false) String statusStr,
                               @RequestParam(value = "q", required = false) String query,
                               Model model) {
        List<Booking> bookings;

        if (query != null && !query.isBlank()) {
            bookings = bookingService.searchBookings(query);
            model.addAttribute("searchQuery", query);
        } else if (statusStr != null && !statusStr.isBlank() && !statusStr.equalsIgnoreCase("ALL")) {
            try {
                BookingStatus status = BookingStatus.valueOf(statusStr.toUpperCase());
                bookings = bookingService.getBookingsByStatus(status);
                model.addAttribute("currentStatusFilter", status.name());
            } catch (IllegalArgumentException e) {
                bookings = bookingService.getAllBookings();
            }
        } else {
            bookings = bookingService.getAllBookings();
            model.addAttribute("currentStatusFilter", "ALL");
        }

        model.addAttribute("bookings", bookings);
        model.addAttribute("allStatuses", BookingStatus.values());
        model.addAttribute("services", serviceCatalogService.getAllActiveServices());
        model.addAttribute("activeAdminNav", "bookings");
        return "bookings";
    }

    @PostMapping("/bookings/{id}/status")
    public String updateBookingStatus(@PathVariable("id") Long id,
                                      @RequestParam("status") String statusStr,
                                      @RequestParam(value = "adminNotes", required = false) String adminNotes,
                                      RedirectAttributes redirectAttributes) {
        try {
            BookingStatus status = BookingStatus.valueOf(statusStr.toUpperCase());
            bookingService.updateBookingStatus(id, status, adminNotes);
            redirectAttributes.addFlashAttribute("successMessage", 
                    "Booking status successfully updated to " + status.getDisplayName() + "!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating status: " + e.getMessage());
        }
        return "redirect:/admin/bookings";
    }

    @PostMapping("/bookings/{id}/edit")
    public String editBooking(@PathVariable("id") Long id,
                              @ModelAttribute BookingRequestDto dto,
                              @RequestParam(value = "adminNotes", required = false) String adminNotes,
                              RedirectAttributes redirectAttributes) {
        try {
            bookingService.updateBookingDetails(id, dto, adminNotes);
            redirectAttributes.addFlashAttribute("successMessage", "Booking details successfully updated!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating booking: " + e.getMessage());
        }
        return "redirect:/admin/bookings";
    }

    @PostMapping("/bookings/{id}/delete")
    public String deleteBooking(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            bookingService.deleteBooking(id);
            redirectAttributes.addFlashAttribute("successMessage", "Booking successfully deleted.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting booking: " + e.getMessage());
        }
        return "redirect:/admin/bookings";
    }

    @GetMapping("/services")
    public String manageServices(Model model) {
        List<ServiceEntity> services = serviceCatalogService.getAllServices();
        model.addAttribute("services", services);
        model.addAttribute("newService", new ServiceDto());
        model.addAttribute("activeAdminNav", "services");
        return "admin-services";
    }

    @PostMapping("/services/new")
    public String createService(@Valid @ModelAttribute("newService") ServiceDto serviceDto,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes,
                                Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("services", serviceCatalogService.getAllServices());
            model.addAttribute("activeAdminNav", "services");
            model.addAttribute("showAddModal", true);
            return "admin-services";
        }

        try {
            serviceCatalogService.saveService(serviceDto);
            redirectAttributes.addFlashAttribute("successMessage", "Service successfully added!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error adding service: " + e.getMessage());
        }
        return "redirect:/admin/services";
    }

    @PostMapping("/services/{id}/edit")
    public String updateService(@PathVariable("id") Long id,
                                @Valid @ModelAttribute ServiceDto serviceDto,
                                RedirectAttributes redirectAttributes) {
        try {
            serviceCatalogService.updateService(id, serviceDto);
            redirectAttributes.addFlashAttribute("successMessage", "Service successfully updated!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating service: " + e.getMessage());
        }
        return "redirect:/admin/services";
    }

    @PostMapping("/services/{id}/toggle")
    public String toggleService(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            serviceCatalogService.toggleServiceStatus(id);
            redirectAttributes.addFlashAttribute("successMessage", "Service visibility toggled.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error toggling service: " + e.getMessage());
        }
        return "redirect:/admin/services";
    }

    @PostMapping("/services/{id}/delete")
    public String deleteService(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            serviceCatalogService.deleteService(id);
            redirectAttributes.addFlashAttribute("successMessage", "Service deleted successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", 
                    "Cannot delete service: it is associated with existing bookings. You can deactivate it instead.");
        }
        return "redirect:/admin/services";
    }
}
