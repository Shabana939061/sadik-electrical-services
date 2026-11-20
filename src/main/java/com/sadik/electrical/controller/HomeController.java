package com.sadik.electrical.controller;

import com.sadik.electrical.service.BookingService;
import com.sadik.electrical.service.ServiceCatalogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final ServiceCatalogService serviceCatalogService;
    private final BookingService bookingService;

    public HomeController(ServiceCatalogService serviceCatalogService, BookingService bookingService) {
        this.serviceCatalogService = serviceCatalogService;
        this.bookingService = bookingService;
    }

    @GetMapping({"/", "/home"})
    public String home(Model model) {
        model.addAttribute("services", serviceCatalogService.getAllActiveServices());
        model.addAttribute("activeNav", "home");
        return "home";
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("activeNav", "about");
        return "about";
    }

    @GetMapping("/services")
    public String services(Model model) {
        model.addAttribute("services", serviceCatalogService.getAllActiveServices());
        model.addAttribute("activeNav", "services");
        return "services";
    }

    @GetMapping("/gallery")
    public String gallery(Model model) {
        model.addAttribute("activeNav", "gallery");
        return "gallery";
    }

    @GetMapping("/contact")
    public String contact(Model model) {
        model.addAttribute("activeNav", "contact");
        return "contact";
    }
}
