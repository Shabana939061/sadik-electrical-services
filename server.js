/**
 * Sadik Electrical Services - Fast Zero-Dependency Web Server
 * Runs out-of-the-box in VS Code with: node server.js
 * Location: Chilakaluripet, Andhra Pradesh, India
 * Owner: Sadik | Phone: 9390619229
 */

const http = require('http');
const fs = require('fs');
const path = require('path');
const url = require('url');
const querystring = require('querystring');

const PORT = process.env.PORT || 8080;
const STATIC_DIR = path.join(__dirname, 'src', 'main', 'resources', 'static');
const TEMPLATE_DIR = path.join(__dirname, 'src', 'main', 'resources', 'templates');

// In-Memory Data Store (Mirrors MySQL Database: sadik_electrical_db)
const services = [
  {
    id: 1,
    name: 'Home Wiring',
    slug: 'home-wiring',
    shortDescription: 'New house wiring, electrical connections and wiring replacement.',
    description: 'Complete electrical wiring solutions for newly constructed residential homes and commercial buildings. Concealed conduit piping, load calculation, distribution board setup, and fire-resistant ISI copper cabling.',
    icon: 'bi-plug-fill',
    image: '/images/services/home-wiring.svg',
    priceStarting: 1499.00,
    active: true,
    displayOrder: 1
  },
  {
    id: 2,
    name: 'Electrical Repairs',
    slug: 'electrical-repairs',
    shortDescription: 'Troubleshooting and repairing electrical problems.',
    description: 'Rapid troubleshooting for short circuits, frequent MCB trips, burnt wires, loose contacts, sparking sockets, sudden blackouts, and electrical surges.',
    icon: 'bi-wrench-adjustable-circle-fill',
    image: '/images/services/electrical-repairs.svg',
    priceStarting: 299.00,
    active: true,
    displayOrder: 2
  },
  {
    id: 3,
    name: 'Fan and Light Installation',
    slug: 'fan-and-light-installation',
    shortDescription: 'Installing ceiling fans, LED lights and lighting fixtures.',
    description: 'Expert mounting, assembly, and connection for all types of ceiling fans, BLDC energy-saving fans, exhaust fans, chandelier lights, LED panel lights, flood lights, and wall sconces.',
    icon: 'bi-lightbulb-fill',
    image: '/images/services/fan-light.svg',
    priceStarting: 199.00,
    active: true,
    displayOrder: 3
  },
  {
    id: 4,
    name: 'Switchboard Repairs',
    slug: 'switchboard-repairs',
    shortDescription: 'Switch, socket and electrical board repairs.',
    description: 'Comprehensive repair and replacement of modular switchboards, worn-out 6A/16A switches, 3-pin universal sockets, fan speed regulators, power indicators, fuse units, and miniature circuit breakers (MCBs).',
    icon: 'bi-toggles',
    image: '/images/services/switchboard.svg',
    priceStarting: 249.00,
    active: true,
    displayOrder: 4
  },
  {
    id: 5,
    name: 'Inverter Installation',
    slug: 'inverter-installation',
    shortDescription: 'Inverter and battery installation and connections.',
    description: 'End-to-end inverter and tubular battery setup to provide seamless backup power during load shedding. Dedicated inverter wiring loops, changeover switches, terminal protection, and load balancing.',
    icon: 'bi-battery-charging',
    image: '/images/services/inverter.svg',
    priceStarting: 799.00,
    active: true,
    displayOrder: 5
  },
  {
    id: 6,
    name: 'Electrical Maintenance',
    slug: 'electrical-maintenance',
    shortDescription: 'Electrical inspections and regular maintenance.',
    description: 'Scheduled preventative maintenance and thorough safety audits for apartments, villas, retail shops, and commercial offices. Earth resistance testing, thermal checking, breaker trip calibration, and terminal re-tightening.',
    icon: 'bi-shield-fill-check',
    image: '/images/services/maintenance.svg',
    priceStarting: 499.00,
    active: true,
    displayOrder: 6
  },
  {
    id: 7,
    name: 'Ceiling Top Lighting',
    slug: 'ceiling-top-lighting',
    shortDescription: 'Decorative ceiling lights, LED strip lights and false ceiling lighting installation.',
    description: 'Modern false ceiling POP lighting, warm/cool white COB spotlights, concealed profile lights, magnetic track lights, and ambient RGB LED strip lighting expertly tailored to your interior design.',
    icon: 'bi-brightness-high-fill',
    image: '/images/services/ceiling-lighting.svg',
    priceStarting: 899.00,
    active: true,
    displayOrder: 7
  }
];

let bookings = [
  {
    id: 1,
    bookingReference: 'SES-2026-1001',
    customerName: 'Ramesh Babu',
    mobile: '9848022334',
    email: 'ramesh.babu@gmail.com',
    serviceId: 2,
    serviceName: 'Electrical Repairs',
    preferredDate: '2026-11-19',
    preferredTime: 'Morning (08:00 AM - 11:00 AM)',
    address: 'Near Clock Tower, Main Road, Chilakaluripet, AP',
    problemDescription: 'Frequent MCB tripping in kitchen and burning smell near refrigerator socket.',
    status: 'PENDING',
    adminNotes: 'Customer called in advance. Needs urgent inspection on kitchen circuit.',
    createdAt: '2026-11-18 10:30'
  },
  {
    id: 2,
    bookingReference: 'SES-2026-1002',
    customerName: 'Suresh Kumar',
    mobile: '9440155678',
    email: 'suresh.k@outlook.com',
    serviceId: 5,
    serviceName: 'Inverter Installation',
    preferredDate: '2026-11-20',
    preferredTime: 'Afternoon (11:00 AM - 02:00 PM)',
    address: 'Venkateswara Colony, Ganapavaram Road, Chilakaluripet, AP',
    problemDescription: 'Luminous 1050VA Inverter and 150Ah battery installation for 2BHK house.',
    status: 'ACCEPTED',
    adminNotes: 'Technician assigned. Scheduled for afternoon slot.',
    createdAt: '2026-11-18 11:15'
  },
  {
    id: 3,
    bookingReference: 'SES-2026-1003',
    customerName: 'Venkat Rao',
    mobile: '9390112233',
    email: 'venkat.rao@yahoo.com',
    serviceId: 7,
    serviceName: 'Ceiling Top Lighting',
    preferredDate: '2026-11-17',
    preferredTime: 'Morning (08:00 AM - 11:00 AM)',
    address: 'Purushothapatnam Bypass, Chilakaluripet, AP',
    problemDescription: 'Living room false ceiling LED profile strip and 8 COB spot lights installation.',
    status: 'COMPLETED',
    adminNotes: 'Completed successfully on time. Customer satisfied with warm white glow.',
    createdAt: '2026-11-17 09:00'
  }
];

let nextBookingId = 4;
let authenticatedAdmin = null;

const MIME_TYPES = {
  '.html': 'text/html; charset=utf-8',
  '.css': 'text/css',
  '.js': 'application/javascript',
  '.json': 'application/json',
  '.svg': 'image/svg+xml',
  '.png': 'image/png',
  '.jpg': 'image/jpeg',
  '.ico': 'image/x-icon'
};

function readTemplate(filename) {
  const filePath = path.join(TEMPLATE_DIR, filename);
  if (!fs.existsSync(filePath)) return '';
  let content = fs.readFileSync(filePath, 'utf8');

  // Strip Thymeleaf fragments & namespace tags cleanly for browser rendering
  content = content.replace(/xmlns:th="[^"]*"/g, '');
  content = content.replace(/xmlns:sec="[^"]*"/g, '');
  content = content.replace(/th:href="@\{([^}]+)\}"/g, 'href="$1"');
  content = content.replace(/th:src="@\{([^}]+)\}"/g, 'src="$1"');
  content = content.replace(/th:action="@\{([^}]+)\}"/g, 'action="$1"');

  // Inline header/footer fragments
  const headFrag = fs.readFileSync(path.join(TEMPLATE_DIR, 'fragments', 'header.html'), 'utf8');
  const footFrag = fs.readFileSync(path.join(TEMPLATE_DIR, 'fragments', 'footer.html'), 'utf8');
  const floatFrag = fs.readFileSync(path.join(TEMPLATE_DIR, 'fragments', 'floating-buttons.html'), 'utf8');
  const sideFrag = fs.readFileSync(path.join(TEMPLATE_DIR, 'fragments', 'admin-sidebar.html'), 'utf8');

  content = content.replace(/<head th:replace="~\{fragments\/header :: head\('[^']+'\)\}">[\s\S]*?<\/head>/i, function() {
    return `<head>
      <meta charset="UTF-8">
      <meta name="viewport" content="width=device-width, initial-scale=1.0">
      <title>Sadik Electrical Services - Chilakaluripet</title>
      <link rel="icon" type="image/svg+xml" href="/images/logo.svg">
      <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
      <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css" rel="stylesheet">
      <link rel="stylesheet" href="/css/style.css">
    </head>`;
  });

  content = content.replace(/<div th:replace="~\{fragments\/header :: navbar\('[^']+'\)\}"><\/div>/i, function() {
    let nav = headFrag.substring(headFrag.indexOf('<div th:fragment="navbar'), headFrag.indexOf('</body>'));
    nav = nav.replace(/th:href="@\{([^}]+)\}"/g, 'href="$1"');
    nav = nav.replace(/th:src="@\{([^}]+)\}"/g, 'src="$1"');
    nav = nav.replace(/th:classappend="[^"]*"/g, '');
    nav = nav.replace(/sec:authorize="[^"]*"/g, '');
    return nav;
  });

  content = content.replace(/<div th:replace="~\{fragments\/footer :: footer\}"><\/div>/i, function() {
    let foot = footFrag.substring(footFrag.indexOf('<footer'), footFrag.indexOf('</body>'));
    foot = foot.replace(/th:href="@\{([^}]+)\}"/g, 'href="$1"');
    foot = foot.replace(/th:src="@\{([^}]+)\}"/g, 'src="$1"');
    return foot;
  });

  content = content.replace(/<div th:replace="~\{fragments\/floating-buttons :: floatingControls\}"><\/div>/i, function() {
    let fl = floatFrag.substring(floatFrag.indexOf('<div th:fragment="floatingControls">'), floatFrag.indexOf('</body>'));
    fl = fl.replace(/th:href="@\{([^}]+)\}"/g, 'href="$1"');
    return fl;
  });

  content = content.replace(/<div th:replace="~\{fragments\/admin-sidebar :: sidebar\('[^']+'\)\}"><\/div>/i, function() {
    let sd = sideFrag.substring(sideFrag.indexOf('<div th:fragment="sidebar'), sideFrag.indexOf('</body>'));
    sd = sd.replace(/th:href="@\{([^}]+)\}"/g, 'href="$1"');
    sd = sd.replace(/th:action="@\{([^}]+)\}"/g, 'action="$1"');
    sd = sd.replace(/th:classappend="[^"]*"/g, '');
    sd = sd.replace(/th:text="[^"]*"/g, '');
    return sd;
  });

  return content;
}

const server = http.createServer((req, res) => {
  const parsedUrl = url.parse(req.url, true);
  let pathname = parsedUrl.pathname;

  // 1. Static Assets handler
  if (pathname.startsWith('/css/') || pathname.startsWith('/js/') || pathname.startsWith('/images/')) {
    const filePath = path.join(STATIC_DIR, pathname);
    if (fs.existsSync(filePath)) {
      const ext = path.extname(filePath).toLowerCase();
      res.writeHead(200, { 'Content-Type': MIME_TYPES[ext] || 'text/plain' });
      return fs.createReadStream(filePath).pipe(res);
    }
  }

  // Favicon handler
  if (pathname === '/favicon.ico') {
    const iconPath = path.join(STATIC_DIR, 'images', 'logo.svg');
    res.writeHead(200, { 'Content-Type': 'image/svg+xml' });
    return fs.createReadStream(iconPath).pipe(res);
  }

  // 2. Public Pages
  if (pathname === '/' || pathname === '/home') {
    let html = readTemplate('home.html');
    // Inject dynamic services into homepage
    let serviceCards = services.map(s => `
      <div class="col-lg-4 col-md-6">
        <div class="service-card">
          <div class="service-card-img-wrap">
            <img src="${s.image}" alt="${s.name}">
            <span class="service-card-badge">Chilakaluripet</span>
          </div>
          <div class="service-card-body">
            <div class="service-card-icon">
              <i class="bi ${s.icon}"></i>
            </div>
            <h3 class="service-card-title">${s.name}</h3>
            <p class="service-card-desc">${s.shortDescription}</p>
            <div class="service-card-footer">
              <div class="service-price">Starts at <strong>₹${s.priceStarting.toLocaleString('en-IN')}</strong></div>
              <a href="/booking?serviceId=${s.id}" class="btn btn-sm btn-yellow fw-bold">
                <i class="bi bi-calendar-plus me-1"></i> Book Now
              </a>
            </div>
          </div>
        </div>
      </div>
    `).join('');

    html = html.replace(/<div class="col-lg-4 col-md-6" th:each="service : \$\{services\}">[\s\S]*?<\/div>\s*<\/div>/i, serviceCards + '</div>');
    res.writeHead(200, { 'Content-Type': 'text/html; charset=utf-8' });
    return res.end(html);
  }

  if (pathname === '/about') {
    let html = readTemplate('about.html');
    res.writeHead(200, { 'Content-Type': 'text/html; charset=utf-8' });
    return res.end(html);
  }

  if (pathname === '/services') {
    let html = readTemplate('services.html');
    let serviceItems = services.map((s, idx) => `
      <div class="col-12">
        <div class="card border-0 rounded-4 shadow-sm overflow-hidden p-3 p-md-4 mb-4" style="background-color: #F8FAFC; border: 1px solid #E2E8F0 !important;">
          <div class="row align-items-center g-4">
            <div class="col-lg-5 ${idx % 2 === 1 ? 'order-lg-2' : ''}">
              <div class="rounded-4 overflow-hidden shadow-sm" style="height: 250px; background: #0B192C;">
                <img src="${s.image}" alt="${s.name}" class="w-100 h-100 object-fit-cover">
              </div>
            </div>
            <div class="col-lg-7 ${idx % 2 === 1 ? 'order-lg-1' : ''}">
              <div class="d-flex align-items-center gap-3 mb-2">
                <div class="service-card-icon mb-0" style="width: 42px; height: 42px; font-size: 1.3rem;">
                  <i class="bi ${s.icon}"></i>
                </div>
                <span class="badge bg-primary bg-opacity-10 text-primary fw-bold text-uppercase px-2 py-1">Service #${idx + 1}</span>
              </div>
              <h2 class="h3 fw-bold mb-2" style="color: #0B192C;">${s.name}</h2>
              <p class="text-primary fw-semibold mb-2">${s.shortDescription}</p>
              <p class="text-muted small mb-4">${s.description}</p>
              <div class="d-flex flex-wrap align-items-center justify-content-between gap-3 pt-3 border-top">
                <div>
                  <span class="text-muted small d-block">Starting Price</span>
                  <span class="fs-4 fw-bold text-dark font-monospace">₹${s.priceStarting.toLocaleString('en-IN')}</span>
                </div>
                <div class="d-flex gap-2">
                  <a href="/booking?serviceId=${s.id}" class="btn btn-yellow fw-bold px-3 py-2">
                    <i class="bi bi-calendar-check-fill me-1"></i> Book This Service
                  </a>
                  <a href="tel:9390619229" class="btn btn-outline-dark fw-bold px-3 py-2">
                    <i class="bi bi-telephone-fill me-1"></i> Call
                  </a>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    `).join('');

    html = html.replace(/<div class="col-12" th:each="service, iterStat : \$\{services\}">[\s\S]*?<\/div>\s*<\/div>\s*<\/div>/i, serviceItems + '</div></div>');
    res.writeHead(200, { 'Content-Type': 'text/html; charset=utf-8' });
    return res.end(html);
  }

  if (pathname === '/gallery') {
    let html = readTemplate('gallery.html');
    res.writeHead(200, { 'Content-Type': 'text/html; charset=utf-8' });
    return res.end(html);
  }

  if (pathname === '/contact') {
    let html = readTemplate('contact.html');
    res.writeHead(200, { 'Content-Type': 'text/html; charset=utf-8' });
    return res.end(html);
  }

  // 3. Booking Form & Processing
  if (pathname === '/booking' && req.method === 'GET') {
    let html = readTemplate('booking.html');
    const selectedServiceId = parsedUrl.query.serviceId || '';
    
    // Populate service options
    let options = services.map(s => `
      <option value="${s.id}" ${s.id == selectedServiceId ? 'selected' : ''}>
        ${s.name} (Starts ₹${s.priceStarting.toLocaleString('en-IN')})
      </option>
    `).join('');

    html = html.replace(/<option th:each="service : \$\{services\}"[\s\S]*?<\/option>/i, options);
    html = html.replace(/th:value="\*\{customerName\}"/g, '');
    html = html.replace(/th:value="\*\{mobileNumber\}"/g, '');
    html = html.replace(/th:value="\*\{email\}"/g, '');
    html = html.replace(/th:value="\*\{address\}"/g, '');
    html = html.replace(/th:value="\*\{problemDescription\}"/g, '');
    html = html.replace(/th:value="\*\{preferredDate\}"/g, '');
    html = html.replace(/th:field="[^"]*"/g, '');
    html = html.replace(/th:classappend="[^"]*"/g, '');
    html = html.replace(/<div class="invalid-feedback[^>]*>[\s\S]*?<\/div>/g, '');
    html = html.replace(/<input type="hidden" th:name="\$\{_csrf\.parameterName\}"[^>]*\/>/g, '');

    res.writeHead(200, { 'Content-Type': 'text/html; charset=utf-8' });
    return res.end(html);
  }

  if (pathname === '/booking' && req.method === 'POST') {
    let body = '';
    req.on('data', chunk => { body += chunk.toString(); });
    req.on('end', () => {
      const data = querystring.parse(body);
      const name = (data.customerName || '').trim();
      const mobile = (data.mobileNumber || '').trim();
      const email = (data.email || '').trim();
      const serviceId = parseInt(data.serviceId, 10);
      const address = (data.address || '').trim();
      const preferredDate = data.preferredDate || new Date().toISOString().split('T')[0];
      const preferredTime = data.preferredTime || 'Morning Slot';
      const problemDesc = (data.problemDescription || '').trim();

      const matchedService = services.find(s => s.id === serviceId) || services[0];
      const randomNum = Math.floor(1000 + Math.random() * 9000);
      const bookingRef = `SES-2026-${randomNum}`;

      const newBooking = {
        id: nextBookingId++,
        bookingReference: bookingRef,
        customerName: name,
        mobile: mobile,
        email: email,
        serviceId: matchedService.id,
        serviceName: matchedService.name,
        preferredDate: preferredDate,
        preferredTime: preferredTime,
        address: address,
        problemDescription: problemDesc,
        status: 'PENDING',
        adminNotes: '',
        createdAt: new Date().toISOString().replace('T', ' ').substring(0, 16)
      };

      bookings.unshift(newBooking);

      res.writeHead(302, { 'Location': `/booking/confirm/${bookingRef}` });
      return res.end();
    });
    return;
  }

  if (pathname.startsWith('/booking/confirm/')) {
    const ref = pathname.split('/')[3];
    const booking = bookings.find(b => b.bookingReference === ref) || bookings[0];

    let html = readTemplate('booking-confirmation.html');
    html = html.replace(/th:text="\$\{booking\.bookingReference\}"/g, booking.bookingReference);
    html = html.replace(/th:text="\$\{booking\.status\.displayName\}"/g, booking.status);
    html = html.replace(/th:text="\$\{booking\.customer\.name\}"/g, booking.customerName);
    html = html.replace(/th:text="\$\{'\+91 ' \+ booking\.customer\.mobile\}"/g, '+91 ' + booking.mobile);
    html = html.replace(/th:text="\$\{booking\.service\.name\}"/g, booking.serviceName);
    html = html.replace(/<span th:text="\$\{#temporals\.format\(booking\.preferredDate, 'dd MMMM yyyy'\)\}"><\/span>/g, booking.preferredDate);
    html = html.replace(/th:text="\$\{booking\.preferredTime\}"/g, booking.preferredTime);
    html = html.replace(/th:text="\$\{booking\.customer\.address\}"/g, booking.address);
    html = html.replace(/th:text="\$\{booking\.problemDescription\}"/g, booking.problemDescription);
    html = html.replace(/SES-2026-1234/g, booking.bookingReference);

    res.writeHead(200, { 'Content-Type': 'text/html; charset=utf-8' });
    return res.end(html);
  }

  // 4. Admin Authentication
  if (pathname === '/admin/login' && req.method === 'GET') {
    let html = readTemplate('admin-login.html');
    html = html.replace(/<input type="hidden" th:name="\$\{_csrf\.parameterName\}"[^>]*\/>/g, '');
    html = html.replace(/<div th:if="\$\{param\.error\}"[^>]*>[\s\S]*?<\/div>/g, '');
    html = html.replace(/<div th:if="\$\{param\.logout\}"[^>]*>[\s\S]*?<\/div>/g, '');
    res.writeHead(200, { 'Content-Type': 'text/html; charset=utf-8' });
    return res.end(html);
  }

  if (pathname === '/admin/login' && req.method === 'POST') {
    let body = '';
    req.on('data', chunk => { body += chunk.toString(); });
    req.on('end', () => {
      const data = querystring.parse(body);
      if (data.username === 'admin' && data.password === 'admin123') {
        authenticatedAdmin = 'Sadik';
        res.writeHead(302, { 'Location': '/admin/dashboard' });
        return res.end();
      } else {
        res.writeHead(302, { 'Location': '/admin/login?error=true' });
        return res.end();
      }
    });
    return;
  }

  if (pathname === '/admin/logout') {
    authenticatedAdmin = null;
    res.writeHead(302, { 'Location': '/admin/login?logout=true' });
    return res.end();
  }

  // 5. Admin Dashboard
  if (pathname === '/admin' || pathname === '/admin/dashboard') {
    let html = readTemplate('admin-dashboard.html');
    const total = bookings.length;
    const pending = bookings.filter(b => b.status === 'PENDING').length;
    const accepted = bookings.filter(b => b.status === 'ACCEPTED').length;
    const completed = bookings.filter(b => b.status === 'COMPLETED').length;

    html = html.replace(/th:text="\$\{stats\.totalBookings\}"/g, total);
    html = html.replace(/th:text="\$\{stats\.pendingBookings\}"/g, pending);
    html = html.replace(/th:text="\$\{stats\.acceptedBookings\}"/g, accepted);
    html = html.replace(/th:text="\$\{stats\.completedBookings\}"/g, completed);
    html = html.replace(/th:text="\$\{stats\.totalServices\}"/g, services.length);
    html = html.replace(/th:text="\$\{stats\.totalCustomers\}"/g, total);

    // Recent table rows
    let rows = bookings.slice(0, 8).map(b => `
      <tr>
        <td><span class="fw-bold font-monospace text-primary">${b.bookingReference}</span></td>
        <td>
          <div class="fw-bold text-dark">${b.customerName}</div>
          <div class="small text-muted"><a href="tel:${b.mobile}" class="text-secondary text-decoration-none">${b.mobile}</a></div>
        </td>
        <td><span class="badge bg-light text-dark border fw-semibold">${b.serviceName}</span></td>
        <td>
          <div class="text-dark fw-semibold">${b.preferredDate}</div>
          <div class="small text-muted">${b.preferredTime}</div>
        </td>
        <td>
          <span class="badge-status ${b.status === 'PENDING' ? 'badge-pending' : (b.status === 'ACCEPTED' ? 'badge-accepted' : (b.status === 'COMPLETED' ? 'badge-completed' : 'badge-cancelled'))}">
            ${b.status}
          </span>
        </td>
        <td class="text-end">
          <button type="button" class="btn btn-outline-primary btn-sm py-1 px-2"
                  data-bs-toggle="modal" data-bs-target="#updateStatusModal"
                  data-booking-id="${b.id}"
                  data-booking-ref="${b.bookingReference}"
                  data-current-status="${b.status}"
                  data-admin-notes="${b.adminNotes || ''}">
            <i class="bi bi-pencil-square me-1"></i> Status
          </button>
        </td>
      </tr>
    `).join('');

    html = html.replace(/<tr th:each="booking : \$\{recentBookings\}">[\s\S]*?<\/tr>/i, rows);
    res.writeHead(200, { 'Content-Type': 'text/html; charset=utf-8' });
    return res.end(html);
  }

  // 6. Admin Bookings Full Management
  if (pathname === '/admin/bookings' && req.method === 'GET') {
    let html = readTemplate('bookings.html');
    const filterStatus = parsedUrl.query.status || 'ALL';
    let filtered = (filterStatus === 'ALL') ? bookings : bookings.filter(b => b.status === filterStatus);

    let rows = filtered.map(b => `
      <tr>
        <td>
          <span class="fw-bold font-monospace text-primary">${b.bookingReference}</span>
          <div class="small text-muted">${b.createdAt}</div>
        </td>
        <td>
          <div class="fw-bold text-dark">${b.customerName}</div>
          <div class="small">
            <a href="tel:${b.mobile}" class="text-secondary fw-semibold text-decoration-none">${b.mobile}</a>
            <a href="https://wa.me/91${b.mobile}" target="_blank" class="text-success ms-1"><i class="bi bi-whatsapp"></i></a>
          </div>
          <div class="small text-muted text-truncate" style="max-width: 180px;">${b.address}</div>
        </td>
        <td>
          <span class="badge bg-light text-dark border fw-semibold mb-1">${b.serviceName}</span>
          <div class="small text-muted text-truncate" style="max-width: 180px;">${b.problemDescription}</div>
        </td>
        <td>
          <div class="fw-bold text-dark">${b.preferredDate}</div>
          <div class="small text-muted">${b.preferredTime}</div>
        </td>
        <td>
          <span class="badge-status ${b.status === 'PENDING' ? 'badge-pending' : (b.status === 'ACCEPTED' ? 'badge-accepted' : (b.status === 'COMPLETED' ? 'badge-completed' : 'badge-cancelled'))}">
            ${b.status}
          </span>
        </td>
        <td><span class="small text-secondary">${b.adminNotes || 'None'}</span></td>
        <td class="text-end">
          <div class="btn-group btn-group-sm">
            <button type="button" class="btn btn-outline-primary"
                    data-bs-toggle="modal" data-bs-target="#updateStatusModal"
                    data-booking-id="${b.id}"
                    data-booking-ref="${b.bookingReference}"
                    data-current-status="${b.status}"
                    data-admin-notes="${b.adminNotes || ''}">
              <i class="bi bi-arrow-repeat"></i> Status
            </button>
            <form action="/admin/bookings/${b.id}/delete" method="post" class="d-inline" onsubmit="return confirm('Delete booking ${b.bookingReference}?');">
              <button type="submit" class="btn btn-outline-danger"><i class="bi bi-trash"></i></button>
            </form>
          </div>
        </td>
      </tr>
    `).join('');

    html = html.replace(/<tr th:each="booking : \$\{bookings\}">[\s\S]*?<\/tr>/i, rows);
    res.writeHead(200, { 'Content-Type': 'text/html; charset=utf-8' });
    return res.end(html);
  }

  // Update Status POST
  if (pathname.match(/^\/admin\/bookings\/\d+\/status$/) && req.method === 'POST') {
    const bookingId = parseInt(pathname.split('/')[3], 10);
    let body = '';
    req.on('data', chunk => { body += chunk.toString(); });
    req.on('end', () => {
      const data = querystring.parse(body);
      const b = bookings.find(item => item.id === bookingId);
      if (b) {
        if (data.status) b.status = data.status;
        if (data.adminNotes) b.adminNotes = data.adminNotes;
      }
      res.writeHead(302, { 'Location': '/admin/bookings' });
      return res.end();
    });
    return;
  }

  // Delete Booking POST
  if (pathname.match(/^\/admin\/bookings\/\d+\/delete$/) && req.method === 'POST') {
    const bookingId = parseInt(pathname.split('/')[3], 10);
    bookings = bookings.filter(b => b.id !== bookingId);
    res.writeHead(302, { 'Location': '/admin/bookings' });
    return res.end();
  }

  // 7. Admin Services
  if (pathname === '/admin/services' && req.method === 'GET') {
    let html = readTemplate('admin-services.html');
    let rows = services.map(s => `
      <tr>
        <td>
          <div class="service-card-icon mb-0" style="width: 36px; height: 36px; font-size: 1.1rem;">
            <i class="bi ${s.icon}"></i>
          </div>
        </td>
        <td>
          <div class="fw-bold text-dark">${s.name}</div>
          <small class="text-muted font-monospace">${s.slug}</small>
        </td>
        <td><div class="text-muted small text-truncate" style="max-width: 300px;">${s.shortDescription}</div></td>
        <td><span class="fw-bold text-dark font-monospace">₹${s.priceStarting.toLocaleString('en-IN')}</span></td>
        <td><span class="badge ${s.active ? 'bg-success-subtle text-success' : 'bg-secondary-subtle text-secondary'} border">${s.active ? 'Active' : 'Inactive'}</span></td>
        <td class="text-end">
          <form action="/admin/services/${s.id}/toggle" method="post" class="d-inline">
            <button type="submit" class="btn btn-outline-secondary btn-sm" title="Toggle Active">
              <i class="bi ${s.active ? 'bi-eye-slash' : 'bi-eye'}"></i>
            </button>
          </form>
        </td>
      </tr>
    `).join('');

    html = html.replace(/<tr th:each="srv : \$\{services\}">[\s\S]*?<\/tr>/i, rows);
    res.writeHead(200, { 'Content-Type': 'text/html; charset=utf-8' });
    return res.end(html);
  }

  // Fallback 404
  let errorHtml = readTemplate('error.html');
  res.writeHead(404, { 'Content-Type': 'text/html; charset=utf-8' });
  res.end(errorHtml);
});

server.listen(PORT, () => {
  console.log('=================================================================');
  console.log(` Sadik Electrical Services is RUNNING on http://localhost:${PORT}`);
  console.log(` Admin Dashboard: http://localhost:${PORT}/admin/dashboard`);
  console.log(` Admin Login:     http://localhost:${PORT}/admin/login`);
  console.log(' Credentials:     Username: admin | Password: admin123');
  console.log(' Location:        Chilakaluripet, Andhra Pradesh, India');
  console.log(' Owner:           Sadik | Phone: 7993777432');
  console.log('=================================================================');
});
