package com.sadik.electrical.dto;

import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;

public class BookingRequestDto {

    @NotBlank(message = "Customer name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String customerName;

    @NotBlank(message = "Mobile number is required")
    @Pattern(regexp = "^[6-9]\\d{9}$", message = "Please enter a valid 10-digit Indian mobile number")
    private String mobileNumber;

    @Email(message = "Please enter a valid email address")
    private String email;

    @NotNull(message = "Please select an electrical service")
    private Long serviceId;

    @NotBlank(message = "Please provide your complete address")
    @Size(min = 5, max = 500, message = "Address must be between 5 and 500 characters")
    private String address;

    @NotNull(message = "Preferred date is required")
    @FutureOrPresent(message = "Preferred date must be today or a future date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate preferredDate;

    @NotBlank(message = "Preferred time slot is required")
    private String preferredTime;

    @NotBlank(message = "Please describe the problem or service requirement")
    @Size(min = 5, max = 1000, message = "Description must be between 5 and 1000 characters")
    private String problemDescription;

    public BookingRequestDto() {
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getPreferredDate() {
        return preferredDate;
    }

    public void setPreferredDate(LocalDate preferredDate) {
        this.preferredDate = preferredDate;
    }

    public String getPreferredTime() {
        return preferredTime;
    }

    public void setPreferredTime(String preferredTime) {
        this.preferredTime = preferredTime;
    }

    public String getProblemDescription() {
        return problemDescription;
    }

    public void setProblemDescription(String problemDescription) {
        this.problemDescription = problemDescription;
    }
}
