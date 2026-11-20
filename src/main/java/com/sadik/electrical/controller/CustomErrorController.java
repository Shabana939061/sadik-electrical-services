package com.sadik.electrical.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        int statusCode = 500;
        String errorMessage = "An unexpected error occurred. Please try again later.";

        if (status != null) {
            statusCode = Integer.parseInt(status.toString());
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                errorMessage = "The page you are looking for does not exist or has been moved.";
            } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
                errorMessage = "You do not have permission to access this resource.";
            }
        }

        model.addAttribute("statusCode", statusCode);
        model.addAttribute("errorMessage", errorMessage);
        return "error";
    }
}
