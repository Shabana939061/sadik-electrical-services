package com.sadik.electrical.service;

import com.sadik.electrical.dto.ServiceDto;
import com.sadik.electrical.entity.ServiceEntity;

import java.util.List;
import java.util.Optional;

public interface ServiceCatalogService {
    List<ServiceEntity> getAllActiveServices();
    List<ServiceEntity> getAllServices();
    Optional<ServiceEntity> getServiceById(Long id);
    Optional<ServiceEntity> getServiceBySlug(String slug);
    ServiceEntity saveService(ServiceDto serviceDto);
    ServiceEntity updateService(Long id, ServiceDto serviceDto);
    void toggleServiceStatus(Long id);
    void deleteService(Long id);
    long countServices();
}
