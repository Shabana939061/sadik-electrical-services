package com.sadik.electrical;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Sadik Electrical Services - Main Spring Boot Application
 * Serving Residential and Commercial Customers in Chilakaluripet, Andhra Pradesh, India.
 * Owner: Sadik
 * Phone: 9390619229
 */
@SpringBootApplication
public class SadikElectricalApplication {

    public static void main(String[] args) {
        SpringApplication.run(SadikElectricalApplication.class, args);
        System.out.println("=================================================================");
        System.out.println(" Sadik Electrical Services Web Application is Running!");
        System.out.println(" Local Website URL: http://localhost:8080");
        System.out.println(" Admin Dashboard:   http://localhost:8080/admin/dashboard");
        System.out.println(" Location: Chilakaluripet, Andhra Pradesh, India");
        System.out.println(" Owner: Sadik | Phone: 7993777432");
        System.out.println("=================================================================");
    }
}
