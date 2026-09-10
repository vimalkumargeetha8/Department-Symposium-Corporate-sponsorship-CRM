# Nexus '26 — Corporate Sponsorship CRM & Pipeline Engine

**Team CSBS G-06 · Department of Computer Science & Business Systems**
**Anand Institute of Higher Technology (AIHT)**

A production-grade 3-tier Corporate Sponsorship CRM and Budget Pipeline Engine built for **Nexus '26**, the department's National Level Technical Symposium. It manages the entire corporate outreach lifecycle — from lead identification to fund clearance — with live analytics, a Kanban pipeline, a smart pitch generator, and a budget forecaster.

---

## 👨‍💻 Team Members — CSBS G-06

| S.No | Roll No | Name | Role |
|---|---|---|---|
| 21 | 310125244021 | Kiruthika K | Frontend Lead (HTML5, CSS Grid, Responsive UI) |
| 22 | 310125244016 | Geetha V | Backend Lead (Spring Boot 3, REST Controllers, DTOs) |
| 23 | 310125244023 | Manikandan A | Database Lead (MySQL 8.0, Schema Design, Spring Data JPA) |
| 24 | 310125244024 | Manoj V | Integration & QA Lead (Fetch API, CORS, Postman API Testing) |

---

## ✨ Features

- **📊 Analytics & KPIs** — Live donut chart of tier allocation, conversion funnel, pledged-vs-cleared bar chart, milestone gauge, and executive tier breakdown table — all rendered with pure HTML5 Canvas/SVG (no chart libraries).
- **🏢 Corporate Directory** — Full CRUD sponsor registration form with dual-synced Pledged / Received / Balance fields, search, and multi-attribute filtering (tier, status).
- **🗂️ Kanban Pipeline** — Drag-free, stage-based board (`LEAD → CONTACTED → NEGOTIATING → CONFIRMED / DECLINED`) with live per-column capital sums.
- **✉️ Smart Pitch Generator** — Auto-fills from registered leads and generates 4 formal communication templates (initial outreach, tier proposal, MOU/NEFT clearance, post-event thanks), with copy/download/mail-client actions.
- **📈 Budget Forecaster** — What-if simulator with sliders for target goal, win-rate, and expense categories (AV, kits, prizes, catering), producing real-time net balance and AI-style strategic recommendations.
- **🧾 Executive Faculty Audit Report** — Print-ready, letterhead-formatted governance report with full sponsorship ledger and signature blocks (HOD/Principal/Faculty/Student Lead).
- **🌗 Light/Dark Theme Toggle**, CSV export, and toast notifications throughout.

---

## 🏗️ Architecture

A 3-tier enterprise architecture:

| Layer | Technology |
|---|---|
| **Presentation** | HTML5, CSS3 (glassmorphism UI), vanilla JavaScript (`app.js`) |
| **Application / API** | Spring Boot 3.3.4 (REST API) |
| **Persistence** | MySQL / H2 |

```
Browser (index.html + styles.css + app.js)
        │  REST calls
        ▼
Spring Boot 3.3.4 Backend
        │  JPA / Hibernate
        ▼
MySQL / H2 Database
```

---

## 📁 Project Structure

```
├── index.html          # Main SPA shell — hero, tabs, forms, modals
├── styles.css           # Design system, glass cards, gauge/donut styling
├── app.js                # Frontend logic — API calls, charts, kanban, pitch, forecaster
└── presentation/
    └── index.html        # Master viva/demo deck
```

---

## 🚀 Getting Started

1. **Backend**: Start the Spring Boot REST API (default expects a local server the frontend polls for sponsor records).
2. **Frontend**: Open `index.html` in a browser, or serve the folder with any static file server.
3. The header's **API status badge** confirms backend connectivity on load.

---

## 🎯 Key Metrics Tracked

- **Total Pledged** vs **Funds Received** vs **Balance Outstanding**
- Progress toward **Target Milestone: ₹5,00,000** (with ₹3.5L contingency breakeven)
- Sponsorship tiers: `PLATINUM (₹1,50,000+)` · `GOLD (₹75,000)` · `SILVER (₹40,000)` · `BRONZE (₹25,000)` · `SUPPORTER (₹15,000)`
- Engagement stages: `LEAD → CONTACTED → NEGOTIATING → CONFIRMED / DECLINED`
- Payment clearance: `PENDING → PARTIAL → COMPLETED`

---
**Vimal K** — Lead Architect, Team CSBS G-06
Faculty Event Coordinator · HOD · Principal (sign-off, per Audit Report)

---

## 📄 License

Academic project — Department of Computer Science & Business Systems, AIHT. For internal symposium use.
