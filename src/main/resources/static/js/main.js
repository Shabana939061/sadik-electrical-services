/**
 * Sadik Electrical Services - Main Interactive Scripts
 */

document.addEventListener('DOMContentLoaded', function () {
    // 1. Mobile Phone Input Formatting & Realtime Validation
    const mobileInput = document.getElementById('mobileNumber');
    if (mobileInput) {
        mobileInput.addEventListener('input', function (e) {
            // Strip any non-digit
            let val = this.value.replace(/\D/g, '');
            if (val.length > 10) {
                val = val.substring(0, 10);
            }
            this.value = val;

            // Simple visual validation
            if (val.length === 10 && /^[6-9]/.test(val)) {
                this.classList.remove('is-invalid');
                this.classList.add('is-valid');
            } else if (val.length > 0) {
                this.classList.remove('is-valid');
                this.classList.add('is-invalid');
            } else {
                this.classList.remove('is-valid', 'is-invalid');
            }
        });
    }

    // 2. Minimum Date for Preferred Booking Date (Today)
    const preferredDateInput = document.getElementById('preferredDate');
    if (preferredDateInput) {
        const today = new Date().toISOString().split('T')[0];
        preferredDateInput.setAttribute('min', today);
        if (!preferredDateInput.value) {
            preferredDateInput.value = today;
        }
    }

    // 3. Gallery Category Filter
    const filterButtons = document.querySelectorAll('.gallery-filter-btn');
    const galleryItems = document.querySelectorAll('.gallery-item-wrap');

    if (filterButtons.length > 0 && galleryItems.length > 0) {
        filterButtons.forEach(btn => {
            btn.addEventListener('click', function () {
                filterButtons.forEach(b => b.classList.remove('active', 'btn-electric'));
                filterButtons.forEach(b => b.classList.add('btn-outline-dark'));
                this.classList.remove('btn-outline-dark');
                this.classList.add('active', 'btn-electric');

                const filter = this.getAttribute('data-filter');

                galleryItems.forEach(item => {
                    const category = item.getAttribute('data-category');
                    if (filter === 'all' || category === filter) {
                        item.style.display = 'block';
                    } else {
                        item.style.display = 'none';
                    }
                });
            });
        });
    }

    // 4. Auto-dismiss alerts after 6 seconds
    const alerts = document.querySelectorAll('.alert-dismissible');
    alerts.forEach(alert => {
        setTimeout(() => {
            const bsAlert = new bootstrap.Alert(alert);
            bsAlert.close();
        }, 6000);
    });

    // 5. Booking Form Pre-selection if URL param exists
    const urlParams = new URLSearchParams(window.location.search);
    const serviceParam = urlParams.get('serviceId');
    const serviceSelect = document.getElementById('serviceId');
    if (serviceParam && serviceSelect) {
        serviceSelect.value = serviceParam;
    }
});
