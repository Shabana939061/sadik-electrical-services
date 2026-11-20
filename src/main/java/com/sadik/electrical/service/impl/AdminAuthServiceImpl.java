package com.sadik.electrical.service.impl;

import com.sadik.electrical.entity.Admin;
import com.sadik.electrical.repository.AdminRepository;
import com.sadik.electrical.service.AdminAuthService;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

@Service
@Transactional
public class AdminAuthServiceImpl implements AdminAuthService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminAuthServiceImpl(AdminRepository adminRepository, @Lazy PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Admin not found with username: " + username));

        return new User(
                admin.getUsername(),
                admin.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(admin.getRole()))
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Admin> findByUsername(String username) {
        return adminRepository.findByUsername(username);
    }

    @Override
    public Admin createAdmin(String username, String rawPassword, String fullName, String email) {
        String encodedPassword = passwordEncoder.encode(rawPassword);
        Admin admin = new Admin(username, encodedPassword, fullName, email, "ROLE_ADMIN");
        return adminRepository.save(admin);
    }
}
