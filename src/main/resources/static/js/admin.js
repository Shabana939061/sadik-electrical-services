/**
 * Sadik Electrical Services - Admin Dashboard Scripts
 */

document.addEventListener('DOMContentLoaded', function () {
    // 1. Sidebar Toggle on Mobile
    const sidebarToggle = document.getElementById('sidebarToggle');
    const adminSidebar = document.querySelector('.admin-sidebar');
    if (sidebarToggle && adminSidebar) {
        sidebarToggle.addEventListener('click', function () {
            adminSidebar.classList.toggle('show');
        });
    }

    // 2. Populate Status Update Modal Dynamically
    const statusModal = document.getElementById('updateStatusModal');
    if (statusModal) {
        statusModal.addEventListener('show.bs.modal', function (event) {
            const button = event.relatedTarget;
            const bookingId = button.getAttribute('data-booking-id');
            const bookingRef = button.getAttribute('data-booking-ref');
            const currentStatus = button.getAttribute('data-current-status');
            const currentNotes = button.getAttribute('data-admin-notes') || '';

            const form = document.getElementById('updateStatusForm');
            form.action = '/admin/bookings/' + bookingId + '/status';

            document.getElementById('modalBookingRef').textContent = bookingRef;
            document.getElementById('modalStatusSelect').value = currentStatus;
            document.getElementById('modalAdminNotes').value = currentNotes;
        });
    }

    // 3. Populate Edit Booking Modal Dynamically
    const editBookingModal = document.getElementById('editBookingModal');
    if (editBookingModal) {
        editBookingModal.addEventListener('show.bs.modal', function (event) {
            const button = event.relatedTarget;
            const bookingId = button.getAttribute('data-booking-id');
            const ref = button.getAttribute('data-booking-ref');
            const name = button.getAttribute('data-customer-name');
            const mobile = button.getAttribute('data-customer-mobile');
            const email = button.getAttribute('data-customer-email') || '';
            const serviceId = button.getAttribute('data-service-id');
            const date = button.getAttribute('data-booking-date');
            const time = button.getAttribute('data-booking-time');
            const address = button.getAttribute('data-customer-address');
            const desc = button.getAttribute('data-booking-desc');
            const notes = button.getAttribute('data-admin-notes') || '';

            const form = document.getElementById('editBookingForm');
            form.action = '/admin/bookings/' + bookingId + '/edit';

            document.getElementById('editModalBookingRef').textContent = ref;
            document.getElementById('editCustomerName').value = name;
            document.getElementById('editMobileNumber').value = mobile;
            document.getElementById('editEmail').value = email;
            document.getElementById('editServiceId').value = serviceId;
            document.getElementById('editPreferredDate').value = date;
            document.getElementById('editPreferredTime').value = time;
            document.getElementById('editAddress').value = address;
            document.getElementById('editProblemDescription').value = desc;
            document.getElementById('editAdminNotes').value = notes;
        });
    }

    // 4. Populate Edit Service Modal Dynamically
    const editServiceModal = document.getElementById('editServiceModal');
    if (editServiceModal) {
        editServiceModal.addEventListener('show.bs.modal', function (event) {
            const button = event.relatedTarget;
            const id = button.getAttribute('data-service-id');
            const name = button.getAttribute('data-service-name');
            const shortDesc = button.getAttribute('data-service-short');
            const desc = button.getAttribute('data-service-desc');
            const icon = button.getAttribute('data-service-icon');
            const image = button.getAttribute('data-service-image');
            const price = button.getAttribute('data-service-price');
            const order = button.getAttribute('data-service-order');
            const active = button.getAttribute('data-service-active') === 'true';

            const form = document.getElementById('editServiceForm');
            form.action = '/admin/services/' + id + '/edit';

            document.getElementById('editServiceName').value = name;
            document.getElementById('editServiceShort').value = shortDesc;
            document.getElementById('editServiceDesc').value = desc;
            document.getElementById('editServiceIcon').value = icon;
            document.getElementById('editServiceImage').value = image;
            document.getElementById('editServicePrice').value = price;
            document.getElementById('editServiceOrder').value = order;
            document.getElementById('editServiceActive').checked = active;
        });
    }
});
