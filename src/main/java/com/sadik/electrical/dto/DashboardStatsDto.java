package com.sadik.electrical.dto;

public class DashboardStatsDto {

    private long totalBookings;
    private long pendingBookings;
    private long acceptedBookings;
    private long completedBookings;
    private long cancelledBookings;
    private long totalServices;
    private long totalCustomers;

    public DashboardStatsDto() {
    }

    public DashboardStatsDto(long totalBookings, long pendingBookings, long acceptedBookings,
                             long completedBookings, long cancelledBookings, long totalServices, long totalCustomers) {
        this.totalBookings = totalBookings;
        this.pendingBookings = pendingBookings;
        this.acceptedBookings = acceptedBookings;
        this.completedBookings = completedBookings;
        this.cancelledBookings = cancelledBookings;
        this.totalServices = totalServices;
        this.totalCustomers = totalCustomers;
    }

    public long getTotalBookings() {
        return totalBookings;
    }

    public void setTotalBookings(long totalBookings) {
        this.totalBookings = totalBookings;
    }

    public long getPendingBookings() {
        return pendingBookings;
    }

    public void setPendingBookings(long pendingBookings) {
        this.pendingBookings = pendingBookings;
    }

    public long getAcceptedBookings() {
        return acceptedBookings;
    }

    public void setAcceptedBookings(long acceptedBookings) {
        this.acceptedBookings = acceptedBookings;
    }

    public long getCompletedBookings() {
        return completedBookings;
    }

    public void setCompletedBookings(long completedBookings) {
        this.completedBookings = completedBookings;
    }

    public long getCancelledBookings() {
        return cancelledBookings;
    }

    public void setCancelledBookings(long cancelledBookings) {
        this.cancelledBookings = cancelledBookings;
    }

    public long getTotalServices() {
        return totalServices;
    }

    public void setTotalServices(long totalServices) {
        this.totalServices = totalServices;
    }

    public long getTotalCustomers() {
        return totalCustomers;
    }

    public void setTotalCustomers(long totalCustomers) {
        this.totalCustomers = totalCustomers;
    }
}
