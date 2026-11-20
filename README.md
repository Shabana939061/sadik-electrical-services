# ⚡ Sadik Electrical Services - Web Application

> **Reliable Electrical Services at Your Doorstep.**  
> Serving residential and commercial customers in **Chilakaluripet, Andhra Pradesh, India**.  
> **Owner & Master Electrician:** Sadik | **Contact:** 7993777432 | **WhatsApp:** 799377432

---

## 📋 Table of Contents
1. [Overview & Features](#overview--features)
2. [Technology Stack](#technology-stack)
3. [Project Folder Structure](#project-folder-structure)
4. [MySQL Database Setup](#mysql-database-setup)
5. [Application Configuration](#application-configuration)
6. [How to Run the Project](#how-to-run-the-project)
   - [Option A: In Visual Studio Code (Spring Boot)](#option-a-in-visual-studio-code-spring-boot)
   - [Option B: In VS Code (Instant Node.js Live Preview)](#option-b-in-vs-code-instant-nodejs-live-preview)
   - [Option C: In IntelliJ IDEA](#option-c-in-intellij-idea)
   - [Option D: Terminal / Command Prompt (Maven Wrapper)](#option-d-terminal--command-prompt-maven-wrapper)
7. [Admin Portal & Login Credentials](#admin-portal--login-credentials)
8. [Testing Customer Bookings & Management](#testing-customer-bookings--management)
9. [7 Core Electrical Services Included](#7-core-electrical-services-included)

---

## 🌟 Overview & Features

- **Modern Responsive Design:** Crafted with high-contrast Navy Blue (`#0B192C`), Electric Blue (`#0077B6`), Vibrant Amber Yellow (`#FFB703`), and White typography.
- **Mobile First Experience:** Includes a floating pulsating WhatsApp button and sticky mobile "Call Now" (`tel:9390619229`) bottom bar.
- **Complete Architecture:** Clean separation of Controller, Service, Repository, Entity, DTO, and Configuration layers.
- **Booking Engine:** Validates 10-digit Indian mobile numbers (`^[6-9]\d{9}$`), customer addresses, preferred date/time slots, generates formatted references (`SES-2026-XXXX`), and redirects to a confirmation screen.
- **Secure Admin Management:** Spring Security with BCrypt-hashed passwords, role protection for `/admin/**`, statistics dashboard, filterable bookings table, dynamic status updating, and service management.
- **Zero Broken Links / Missing Assets:** High-resolution vector SVGs for all services, logo, and work gallery included directly in the repository.

---

## 💻 Technology Stack

| Layer | Technologies |
| :--- | :--- |
| **Frontend** | HTML5, CSS3, JavaScript (ES6+), Bootstrap 5.3.3, Bootstrap Icons |
| **Backend** | Java 21, Spring Boot 3.3.4 (Spring MVC, Spring Data JPA, Spring Security, Validation) |
| **Template Engine** | Thymeleaf + Thymeleaf Extras Spring Security 6 |
| **Database** | MySQL 8.x (Database: `sadik_electrical_db`) + In-Memory H2 zero-setup fallback profile |
| **Build Tool** | Apache Maven (with `mvnw` & `mvnw.cmd` Maven Wrapper) |
| **IDE Support** | Pre-configured for VS Code (`.vscode/launch.json`, `tasks.json`, `settings.json`) and IntelliJ IDEA |

---

## 📁 Project Folder Structure

```text
Sadik/
├── .mvn/
│   └── wrapper/
│       └── maven-wrapper.properties
├── .vscode/
│   ├── extensions.json
│   ├── launch.json
│   ├── settings.json
│   └── tasks.json
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── sadik/
│   │   │           └── electrical/
│   │   │               ├── SadikElectricalApplication.java
│   │   │               ├── config/
│   │   │               │   ├── DataInitializer.java
│   │   │               │   └── SecurityConfig.java
│   │   │               ├── controller/
│   │   │               │   ├── AdminController.java
│   │   │               │   ├── BookingController.java
│   │   │               │   ├── CustomErrorController.java
│   │   │               │   └── HomeController.java
│   │   │               ├── dto/
│   │   │               │   ├── BookingRequestDto.java
│   │   │               │   ├── DashboardStatsDto.java
│   │   │               │   └── ServiceDto.java
│   │   │               ├── entity/
│   │   │               │   ├── Admin.java
│   │   │               │   ├── Booking.java
│   │   │               │   ├── BookingStatus.java
│   │   │               │   ├── Customer.java
│   │   │               │   └── ServiceEntity.java
│   │   │               ├── repository/
│   │   │               │   ├── AdminRepository.java
│   │   │               │   ├── BookingRepository.java
│   │   │               │   ├── CustomerRepository.java
│   │   │               │   └── ServiceRepository.java
│   │   │               └── service/
│   │   │                   ├── AdminAuthService.java
│   │   │                   ├── BookingService.java
│   │   │                   ├── DashboardService.java
│   │   │                   ├── ServiceCatalogService.java
│   │   │                   └── impl/
│   │   │                       ├── AdminAuthServiceImpl.java
│   │   │                       ├── BookingServiceImpl.java
│   │   │                       ├── DashboardServiceImpl.java
│   │   │                       └── ServiceCatalogServiceImpl.java
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── application-h2.properties
│   │       ├── application-mysql.properties
│   │       ├── data.sql
│   │       ├── schema.sql
│   │       ├── static/
│   │       │   ├── css/
│   │       │   │   ├── admin.css
│   │       │   │   └── style.css
│   │       │   ├── js/
│   │       │   │   ├── admin.js
│   │       │   │   └── main.js
│   │       │   └── images/
│   │       │       ├── logo.svg
│   │       │       ├── gallery/
│   │       │       │   ├── gallery-ceiling.svg
│   │       │       │   ├── gallery-inverter.svg
│   │       │       │   ├── gallery-lights.svg
│   │       │       │   ├── gallery-panel.svg
│   │       │       │   ├── gallery-repairs.svg
│   │       │       │   └── gallery-wiring.svg
│   │       │       └── services/
│   │       │           ├── ceiling-lighting.svg
│   │       │           ├── electrical-repairs.svg
│   │       │           ├── fan-light.svg
│   │       │           ├── home-wiring.svg
│   │       │           ├── inverter.svg
│   │       │           ├── maintenance.svg
│   │       │           └── switchboard.svg
│   │       └── templates/
│   │           ├── about.html
│   │           ├── admin-dashboard.html
│   │           ├── admin-login.html
│   │           ├── admin-services.html
│   │           ├── booking-confirmation.html
│   │           ├── booking.html
│   │           ├── bookings.html
│   │           ├── contact.html
│   │           ├── error.html
│   │           ├── gallery.html
│   │           ├── home.html
│   │           ├── services.html
│   │           └── fragments/
│   │               ├── admin-sidebar.html
│   │               ├── floating-buttons.html
│   │               ├── footer.html
│   │               └── header.html
├── mvnw
├── mvnw.cmd
├── package.json
├── pom.xml
├── README.md
└── server.js
```

---

## 🗄️ MySQL Database Setup

1. Open **MySQL Workbench** or MySQL Command Line Client.
2. Log in with your MySQL root user.
3. Execute the following SQL statement to create the database:
   ```sql
   CREATE DATABASE IF NOT EXISTS sadik_electrical_db
   CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
   ```
4. *Note:* Spring Boot's Hibernate will automatically create all tables (`admin`, `customers`, `services`, `bookings`) upon startup (`spring.jpa.hibernate.ddl-auto=update`), and `DataInitializer.java` will automatically insert the default admin user, the 7 electrical services, and sample bookings!
5. (Optional) You can also directly run `src/main/resources/schema.sql` and `src/main/resources/data.sql` in MySQL Workbench if you prefer manual database initialization.

---

## ⚙️ Application Configuration

Edit `src/main/resources/application-mysql.properties` if your local MySQL username or password differs:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/sadik_electrical_db?useSSL=false&serverTimezone=Asia/Kolkata&allowPublicKeyRetrieval=true&createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
```

> **Tip:** If you do not have MySQL running right now, switch `spring.profiles.active=h2` in `application.properties` to run in zero-setup in-memory database mode!

---

## 🚀 How to Run the Project

### Option A: In Visual Studio Code (Spring Boot)
1. Open the folder `c:\Users\hi\Desktop\Sadik` in **VS Code**.
2. Make sure you have the **Extension Pack for Java** and **Spring Boot Extension Pack** installed (VS Code will prompt you based on `.vscode/extensions.json`).
3. Press **F5** or click the **Run and Debug** icon on the left sidebar.
4. Select **"Spring Boot - SadikElectricalApplication (MySQL)"** (or the H2 profile if MySQL is not started).
5. Open your browser and visit: `http://localhost:8080`.

### Option B: In VS Code (Instant Node.js Live Preview)
For instant one-click testing inside VS Code without waiting for Maven or database connections:
1. Open the integrated terminal in VS Code (`Ctrl + ~`).
2. Run:
   ```powershell
   node server.js
   ```
3. Open `http://localhost:8080`. Everything is fully interactive!

### Option C: In IntelliJ IDEA
1. Open IntelliJ IDEA and choose **File > Open**.
2. Select the `Sadik` directory (`c:\Users\hi\Desktop\Sadik`).
3. IntelliJ will detect `pom.xml` and import dependencies automatically.
4. Navigate to `src/main/java/com/sadik/electrical/SadikElectricalApplication.java`.
5. Right-click and choose **Run 'SadikElectricalApplication'**.
6. Visit `http://localhost:8080`.

### Option D: Terminal / Command Prompt (Maven Wrapper)
In PowerShell or CMD inside `c:\Users\hi\Desktop\Sadik`:
```powershell
.\mvnw.cmd spring-boot:run
```
Or for H2 profile:
```powershell
.\mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=h2
```

---

## 🔐 Admin Portal & Login Credentials

- **Admin Login URL:** `http://localhost:8080/admin/login`
- **Default Username:** `admin`
- **Default Password:** `admin123`
- **Protected Routes:** All routes under `/admin/**` require successful authentication.
- **Dashboard URL:** `http://localhost:8080/admin/dashboard`
- **Bookings Management URL:** `http://localhost:8080/admin/bookings`
- **Services Management URL:** `http://localhost:8080/admin/services`

---

## 🧪 Testing Customer Bookings & Management

1. **Customer Booking:**
   - Go to `http://localhost:8080/booking`.
   - Enter your name (e.g., *Naveen Kumar*), 10-digit mobile (e.g., *9876543210*), address in Chilakaluripet, choose any of the 7 services, preferred date and time, and problem description.
   - Click **Submit Service Booking**.
   - You will be redirected to the **Booking Confirmation** page (`/booking/confirm/SES-2026-XXXX`).
   - You can click **"Send Ref on WhatsApp"** or **"Call Sadik Directly"**.
2. **Admin Management:**
   - Go to `http://localhost:8080/admin/login` and log in with `admin` / `admin123`.
   - You will see the new booking in the **Recent Bookings** table and the counters updated.
   - Click **"Status"** to change the status (Pending ➔ Accepted ➔ Completed ➔ Cancelled) and add technician notes.
   - Go to **Manage Bookings** (`/admin/bookings`) to filter by status or search by customer name/phone.

---

## ⚡ 7 Core Electrical Services Included

1. **Home Wiring** – New house wiring, conduit piping, distribution board setup, and complete rewiring.
2. **Electrical Repairs** – Rapid troubleshooting and fixing short circuits, tripped MCBs, and power surges.
3. **Fan and Light Installation** – Mounting ceiling fans, BLDC fans, LED panel downlights, and chandeliers.
4. **Switchboard Repairs** – Repairing modular switches, 6A/16A sockets, fan regulators, and MCBs.
5. **Inverter Installation** – End-to-end inverter and tubular battery setup with dedicated loop wiring.
6. **Electrical Maintenance** – Earth resistance safety tests, circuit audits, and regular maintenance.
7. **Ceiling Top Lighting** – False ceiling POP lighting, warm COB spot lights, profile channels, and ambient LED strips.

---

**Sadik Electrical Services** • Chilakaluripet, Andhra Pradesh • 📞 +91  799377432
