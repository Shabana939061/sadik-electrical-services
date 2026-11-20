package com.sadik.electrical.service;

import com.sadik.electrical.entity.Admin;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface AdminAuthService extends UserDetailsService {
    Optional<Admin> findByUsername(String username);
    Admin createAdmin(String username, String rawPassword, String fullName, String email);
}
