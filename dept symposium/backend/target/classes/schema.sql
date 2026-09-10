-- ==============================================================================
-- DATABASE CREATION & INITIALIZATION SCRIPT
-- Project: Department Symposium Corporate Sponsorship CRM (Team CSBS G-06)
-- Target DBMS: MySQL 8.0+
-- Database Name: dept_symposium_crm
-- ==============================================================================

CREATE DATABASE IF NOT EXISTS dept_symposium_crm 
    CHARACTER SET utf8mb4 
    COLLATE utf8mb4_unicode_ci;

USE dept_symposium_crm;

-- Drop table if exists for clean re-indexing during setup
DROP TABLE IF EXISTS sponsors;

-- ==============================================================================
-- TABLE: sponsors
-- Description: Stores corporate sponsor leads, pledged amounts, payment tracking,
--              tier packages, contact persons, and deliverable commitments.
-- ==============================================================================
CREATE TABLE IF NOT EXISTS sponsors (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    company_name VARCHAR(150) NOT NULL,
    contact_person VARCHAR(100) NOT NULL,
    contact_email VARCHAR(120) NOT NULL,
    contact_phone VARCHAR(20) NOT NULL,
    industry_domain VARCHAR(80) NOT NULL,
    sponsorship_tier ENUM('PLATINUM', 'GOLD', 'SILVER', 'BRONZE', 'SUPPORTER') NOT NULL DEFAULT 'BRONZE',
    pledged_amount DECIMAL(12, 2) NOT NULL DEFAULT 0.00,
    received_amount DECIMAL(12, 2) NOT NULL DEFAULT 0.00,
    sponsorship_status ENUM('LEAD', 'CONTACTED', 'NEGOTIATING', 'CONFIRMED', 'DECLINED') NOT NULL DEFAULT 'LEAD',
    payment_status ENUM('PENDING', 'PARTIAL', 'COMPLETED') NOT NULL DEFAULT 'PENDING',
    symposium_edition VARCHAR(20) NOT NULL DEFAULT '2026',
    student_coordinator VARCHAR(100) NOT NULL,
    mou_or_brochure_link VARCHAR(500) NULL,
    deliverables_notes TEXT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    -- Indexes for high-speed search and filtering
    INDEX idx_company_name (company_name),
    INDEX idx_sponsorship_tier (sponsorship_tier),
    INDEX idx_sponsorship_status (sponsorship_status),
    INDEX idx_symposium_edition (symposium_edition),
    INDEX idx_student_coordinator (student_coordinator),

    -- Constraints
    CONSTRAINT chk_pledged_positive CHECK (pledged_amount >= 0),
    CONSTRAINT chk_received_positive CHECK (received_amount >= 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ==============================================================================
-- SAMPLE DATA INSERTION (5+ Realistic Corporate Sponsors for Testing)
-- ==============================================================================
INSERT INTO sponsors (
    company_name, contact_person, contact_email, contact_phone, 
    industry_domain, sponsorship_tier, pledged_amount, received_amount, 
    sponsorship_status, payment_status, symposium_edition, student_coordinator, 
    mou_or_brochure_link, deliverables_notes
) VALUES 
(
    'Zoho Corporation', 
    'Mr. Rajendran Dandapani', 
    'sponsorships@zohocorp.com', 
    '+91-9840123456', 
    'Cloud SaaS & Enterprise Tech', 
    'PLATINUM', 
    150000.00, 
    150000.00, 
    'CONFIRMED', 
    'COMPLETED', 
    '2026', 
    'Vimal K (Lead Architect - CSBS G-06)', 
    'https://drive.google.com/file/d/sample-zoho-mou-2026/view', 
    'Keynote address slot, prime logo on all event banners, 2 student hackathon problem statements, stall near auditorium.'
),
(
    'Freshworks Inc', 
    'Ms. Priya Sundaram', 
    'campus.relations@freshworks.com', 
    '+91-9840234567', 
    'Customer Experience & AI', 
    'GOLD', 
    75000.00, 
    50000.00, 
    'CONFIRMED', 
    'PARTIAL', 
    '2026', 
    'Kavitha M (Student 2 - CSBS G-06)', 
    'https://drive.google.com/file/d/sample-freshworks-mou-2026/view', 
    'Sponsor for Best Paper Presentation Award, logo in symposium kit and website, resume access for pre-final years.'
),
(
    'Tata Consultancy Services', 
    'Mr. Anandhan Krishnan', 
    'academic.outreach@tcs.com', 
    '+91-9840345678', 
    'IT Services & Digital Solutions', 
    'GOLD', 
    80000.00, 
    80000.00, 
    'CONFIRMED', 
    'COMPLETED', 
    '2026', 
    'Dinesh Kumar (Student 3 - CSBS G-06)', 
    'https://drive.google.com/file/d/sample-tcs-sponsorship-mou/view', 
    'Exclusive sponsorship for coding challenge arena, jury representation on panel, branded participant badges.'
),
(
    'Infosys Limited', 
    'Mr. Sriram V', 
    'campusconnect@infosys.com', 
    '+91-9840456789', 
    'Software Consulting & Cloud', 
    'SILVER', 
    40000.00, 
    0.00, 
    'NEGOTIATING', 
    'PENDING', 
    '2026', 
    'Vimal K (Lead Architect - CSBS G-06)', 
    'https://drive.google.com/file/d/sample-infosys-proposal/view', 
    'MOU draft under legal review for Springboard student licenses and workshop resource sponsorship.'
),
(
    'Cognizant Technology Solutions', 
    'Ms. Deepa Raman', 
    'genai.partnerships@cognizant.com', 
    '+91-9840567890', 
    'Digital Systems & AI/ML', 
    'BRONZE', 
    25000.00, 
    0.00, 
    'CONTACTED', 
    'PENDING', 
    '2026', 
    'Archana R (Student 4 - CSBS G-06)', 
    'https://drive.google.com/file/d/sample-cts-brochure/view', 
    'Follow-up scheduled with Chennai Talent Acquisition lead regarding technical symposium panel discussion.'
),
(
    'L&T Technology Services', 
    'Mr. Karthik Narayanan', 
    'corporate.symposium@ltts.com', 
    '+91-9840678901', 
    'Engineering R&D & Embedded', 
    'SUPPORTER', 
    15000.00, 
    15000.00, 
    'CONFIRMED', 
    'COMPLETED', 
    '2026', 
    'Dinesh Kumar (Student 3 - CSBS G-06)', 
    'https://drive.google.com/file/d/sample-ltts-receipt/view', 
    'Certificate co-branding and student robotics exhibition booth.'
);
