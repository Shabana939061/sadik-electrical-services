package com.sadik.electrical.service.impl;

import com.sadik.electrical.dto.ServiceDto;
import com.sadik.electrical.entity.ServiceEntity;
import com.sadik.electrical.repository.ServiceRepository;
import com.sadik.electrical.service.ServiceCatalogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ServiceCatalogServiceImpl implements ServiceCatalogService {

    private final ServiceRepository serviceRepository;

    public ServiceCatalogServiceImpl(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ServiceEntity> getAllActiveServices() {
        return serviceRepository.findByActiveTrueOrderByDisplayOrderAsc();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ServiceEntity> getAllServices() {
        return serviceRepository.findAllByOrderByDisplayOrderAsc();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ServiceEntity> getServiceById(Long id) {
        return serviceRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ServiceEntity> getServiceBySlug(String slug) {
        return serviceRepository.findBySlug(slug);
    }

    @Override
    public ServiceEntity saveService(ServiceDto dto) {
        String slug = dto.getName().toLowerCase().replaceAll("[^a-z0-9]+", "-").replaceAll("(^-|-$)", "");
        String imagePath = (dto.getImage() != null && !dto.getImage().isBlank()) 
                ? dto.getImage() 
                : "/images/services/electrical-repairs.svg";

        ServiceEntity entity = new ServiceEntity(
                dto.getName(),
                slug,
                dto.getShortDescription(),
                dto.getDescription(),
                dto.getIcon(),
                imagePath,
                dto.getPriceStarting(),
                dto.getDisplayOrder()
        );
        entity.setActive(dto.isActive());
        return serviceRepository.save(entity);
    }

    @Override
    public ServiceEntity updateService(Long id, ServiceDto dto) {
        ServiceEntity entity = serviceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Service not found with ID: " + id));

        entity.setName(dto.getName());
        entity.setShortDescription(dto.getShortDescription());
        entity.setDescription(dto.getDescription());
        entity.setIcon(dto.getIcon());
        if (dto.getImage() != null && !dto.getImage().isBlank()) {
            entity.setImage(dto.getImage());
        }
        entity.setPriceStarting(dto.getPriceStarting());
        entity.setActive(dto.isActive());
        entity.setDisplayOrder(dto.getDisplayOrder());

        return serviceRepository.save(entity);
    }

    @Override
    public void toggleServiceStatus(Long id) {
        ServiceEntity entity = serviceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Service not found with ID: " + id));
        entity.setActive(!entity.isActive());
        serviceRepository.save(entity);
    }

    @Override
    public void deleteService(Long id) {
        serviceRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public long countServices() {
        return serviceRepository.count();
    }
}
