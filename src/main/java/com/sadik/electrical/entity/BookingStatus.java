package com.sadik.electrical.entity;

public enum BookingStatus {
    PENDING("Pending", "warning", "bi-clock-history"),
    ACCEPTED("Accepted", "info", "bi-check-circle"),
    COMPLETED("Completed", "success", "bi-check2-all"),
    CANCELLED("Cancelled", "danger", "bi-x-circle");

    private final String displayName;
    private final String badgeClass;
    private final String icon;

    BookingStatus(String displayName, String badgeClass, String icon) {
        this.displayName = displayName;
        this.badgeClass = badgeClass;
        this.icon = icon;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getBadgeClass() {
        return badgeClass;
    }

    public String getIcon() {
        return icon;
    }
}
