-- ===================================================================
-- Seed Data for Sadik Electrical Services
-- ===================================================================

USE `sadik_electrical_db`;

-- Default Admin (Password: admin123 - hashed using BCrypt)
-- Hash for 'admin123' is $2a$10$wT33zC6uUHQE6z1rP29eJu4qT7rR5x3W1N6cEvN.Zq0m8aY1uS3a. (or initialized via DataInitializer)
INSERT INTO `admin` (`username`, `password`, `full_name`, `email`, `role`)
SELECT 'admin', '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMxs.AQubh4a', 'Sadik (Owner & Master Electrician)', 'sadikelectricalservices@gmail.com', 'ROLE_ADMIN'
WHERE NOT EXISTS (SELECT 1 FROM `admin` WHERE `username` = 'admin');

-- 7 Electrical Services
INSERT INTO `services` (`id`, `name`, `slug`, `short_description`, `description`, `icon`, `image`, `price_starting`, `active`, `display_order`) VALUES
(1, 'Home Wiring', 'home-wiring', 'New house wiring, electrical connections and wiring replacement.', 'Complete electrical wiring solutions for newly constructed residential homes and commercial buildings. We handle conduit pipe laying, concealed wiring, distribution board (DB) installation, earth pit grounding, and complete rewiring of old homes using ISI-certified fire-resistant copper cables for ultimate safety.', 'bi-plug-fill', '/images/services/home-wiring.svg', 1499.00, true, 1),
(2, 'Electrical Repairs', 'electrical-repairs', 'Troubleshooting and repairing electrical problems.', 'Fast response and accurate troubleshooting for residential and commercial electrical issues. We fix short circuits, frequent MCB trips, burnt wires, loose contacts, sparking sockets, sudden blackouts, and electrical surges to ensure your premises are safe from fire hazards.', 'bi-wrench-adjustable-circle-fill', '/images/services/electrical-repairs.svg', 299.00, true, 2),
(3, 'Fan and Light Installation', 'fan-and-light-installation', 'Installing ceiling fans, LED lights and lighting fixtures.', 'Expert mounting, assembly, and connection for all types of ceiling fans, BLDC energy-efficient fans, exhaust fans, LED tube lights, downlights, chandeliers, focus lamps, and modern architectural decorative fixtures with sturdy ceiling brackets.', 'bi-lightbulb-fill', '/images/services/fan-light.svg', 199.00, true, 3),
(4, 'Switchboard Repairs', 'switchboard-repairs', 'Switch, socket and electrical board repairs.', 'Comprehensive repair and replacement of modular switchboards, worn-out 6A/16A switches, 3-pin universal sockets, fan speed regulators, power indicators, fuse units, and miniature circuit breakers (MCBs) with brand-certified parts.', 'bi-toggles', '/images/services/switchboard.svg', 249.00, true, 4),
(5, 'Inverter Installation', 'inverter-installation', 'Inverter and battery installation and connections.', 'End-to-end inverter and tubular battery setup to provide seamless backup power during load shedding. We set up dedicated inverter loops, dual battery connections, heavy-duty changeover switches, terminal grease coating, and ensure balanced load distribution.', 'bi-battery-charging', '/images/services/inverter.svg', 799.00, true, 5),
(6, 'Electrical Maintenance', 'electrical-maintenance', 'Electrical inspections and regular maintenance.', 'Scheduled preventative maintenance and thorough safety audits for apartments, villas, retail shops, and commercial offices. Includes earth resistance testing, thermal scanning of high-load boards, breaker tripping calibration, and terminal re-tightening.', 'bi-shield-fill-check', '/images/services/maintenance.svg', 499.00, true, 6),
(7, 'Ceiling Top Lighting', 'ceiling-top-lighting', 'Decorative ceiling lights, LED strip lights and false ceiling lighting installation.', 'Transform your living spaces with premium false ceiling POP lighting, warm/cool white COB spotlights, concealed profile lights, magnetic track light channels, and ambient RGB LED strip lighting expertly tailored to your interior design.', 'bi-brightness-high-fill', '/images/services/ceiling-lighting.svg', 899.00, true, 7)
ON DUPLICATE KEY UPDATE `name`=VALUES(`name`), `short_description`=VALUES(`short_description`);
