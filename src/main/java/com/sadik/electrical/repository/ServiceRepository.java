package com.sadik.electrical.repository;

import com.sadik.electrical.entity.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceRepository extends JpaRepository<ServiceEntity, Long> {
    List<ServiceEntity> findByActiveTrueOrderByDisplayOrderAsc();
    List<ServiceEntity> findAllByOrderByDisplayOrderAsc();
    Optional<ServiceEntity> findBySlug(String slug);
    boolean existsByName(String name);
}
