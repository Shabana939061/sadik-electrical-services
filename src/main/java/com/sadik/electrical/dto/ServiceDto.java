package com.sadik.electrical.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class ServiceDto {

    private Long id;

    @NotBlank(message = "Service name is required")
    @Size(max = 100)
    private String name;

    @NotBlank(message = "Short description is required")
    @Size(max = 255)
    private String shortDescription;

    @NotBlank(message = "Detailed description is required")
    private String description;

    @NotBlank(message = "Icon class is required (e.g. bi-plug-fill)")
    private String icon;

    private String image;

    @NotNull(message = "Starting price is required")
    @DecimalMin(value = "0.00", message = "Price cannot be negative")
    private BigDecimal priceStarting;

    private boolean active = true;

    private int displayOrder;

    public ServiceDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public BigDecimal getPriceStarting() {
        return priceStarting;
    }

    public void setPriceStarting(BigDecimal priceStarting) {
        this.priceStarting = priceStarting;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(int displayOrder) {
        this.displayOrder = displayOrder;
    }
}
