package com.aiht.symposium.crm;

import com.aiht.symposium.crm.entity.SponsorLead;
import com.aiht.symposium.crm.repository.SponsorLeadRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.util.Arrays;

@SpringBootApplication
public class DepartmentSymposiumCrmApplication {

    public static void main(String[] args) {
        SpringApplication.run(DepartmentSymposiumCrmApplication.class, args);
    }

    @Bean
    @org.springframework.boot.autoconfigure.condition.ConditionalOnBean(SponsorLeadRepository.class)
    public CommandLineRunner seedInitialData(SponsorLeadRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                SponsorLead s1 = SponsorLead.builder()
                        .companyName("Zoho Corporation")
                        .contactPerson("Mr. Rajendran Dandapani")
                        .contactEmail("sponsorships@zohocorp.com")
                        .contactPhone("+91-9840123456")
                        .industryDomain("Cloud SaaS & Enterprise Tech")
                        .sponsorshipTier("PLATINUM")
                        .pledgedAmount(new BigDecimal("150000.00"))
                        .receivedAmount(new BigDecimal("150000.00"))
                        .sponsorshipStatus("CONFIRMED")
                        .paymentStatus("COMPLETED")
                        .symposiumEdition("2026")
                        .studentCoordinator("Vimal K (Lead Architect - CSBS G-06)")
                        .mouOrBrochureLink("https://drive.google.com/file/d/sample-zoho-mou-2026/view")
                        .deliverablesNotes("Keynote address slot, prime logo on all event banners, 2 student hackathon problem statements, stall near auditorium.")
                        .build();

                SponsorLead s2 = SponsorLead.builder()
                        .companyName("Freshworks Inc")
                        .contactPerson("Ms. Priya Sundaram")
                        .contactEmail("campus.relations@freshworks.com")
                        .contactPhone("+91-9840234567")
                        .industryDomain("Customer Experience & AI")
                        .sponsorshipTier("GOLD")
                        .pledgedAmount(new BigDecimal("75000.00"))
                        .receivedAmount(new BigDecimal("50000.00"))
                        .sponsorshipStatus("CONFIRMED")
                        .paymentStatus("PARTIAL")
                        .symposiumEdition("2026")
                        .studentCoordinator("Kavitha M (Student 2 - CSBS G-06)")
                        .mouOrBrochureLink("https://drive.google.com/file/d/sample-freshworks-mou-2026/view")
                        .deliverablesNotes("Sponsor for Best Paper Presentation Award, logo in symposium kit and website, resume access for pre-final years.")
                        .build();

                SponsorLead s3 = SponsorLead.builder()
                        .companyName("Tata Consultancy Services")
                        .contactPerson("Mr. Anandhan Krishnan")
                        .contactEmail("academic.outreach@tcs.com")
                        .contactPhone("+91-9840345678")
                        .industryDomain("IT Services & Digital Solutions")
                        .sponsorshipTier("GOLD")
                        .pledgedAmount(new BigDecimal("80000.00"))
                        .receivedAmount(new BigDecimal("80000.00"))
                        .sponsorshipStatus("CONFIRMED")
                        .paymentStatus("COMPLETED")
                        .symposiumEdition("2026")
                        .studentCoordinator("Dinesh Kumar (Student 3 - CSBS G-06)")
                        .mouOrBrochureLink("https://drive.google.com/file/d/sample-tcs-sponsorship-mou/view")
                        .deliverablesNotes("Exclusive sponsorship for coding challenge arena, jury representation on panel, branded participant badges.")
                        .build();

                SponsorLead s4 = SponsorLead.builder()
                        .companyName("Infosys Limited")
                        .contactPerson("Mr. Sriram V")
                        .contactEmail("campusconnect@infosys.com")
                        .contactPhone("+91-9840456789")
                        .industryDomain("Software Consulting & Cloud")
                        .sponsorshipTier("SILVER")
                        .pledgedAmount(new BigDecimal("40000.00"))
                        .receivedAmount(BigDecimal.ZERO)
                        .sponsorshipStatus("NEGOTIATING")
                        .paymentStatus("PENDING")
                        .symposiumEdition("2026")
                        .studentCoordinator("Vimal K (Lead Architect - CSBS G-06)")
                        .mouOrBrochureLink("https://drive.google.com/file/d/sample-infosys-proposal/view")
                        .deliverablesNotes("MOU draft under legal review for Springboard student licenses and workshop resource sponsorship.")
                        .build();

                SponsorLead s5 = SponsorLead.builder()
                        .companyName("Cognizant Technology Solutions")
                        .contactPerson("Ms. Deepa Raman")
                        .contactEmail("genai.partnerships@cognizant.com")
                        .contactPhone("+91-9840567890")
                        .industryDomain("Digital Systems & AI/ML")
                        .sponsorshipTier("BRONZE")
                        .pledgedAmount(new BigDecimal("25000.00"))
                        .receivedAmount(BigDecimal.ZERO)
                        .sponsorshipStatus("CONTACTED")
                        .paymentStatus("PENDING")
                        .symposiumEdition("2026")
                        .studentCoordinator("Archana R (Student 4 - CSBS G-06)")
                        .mouOrBrochureLink("https://drive.google.com/file/d/sample-cts-brochure/view")
                        .deliverablesNotes("Follow-up scheduled with Chennai Talent Acquisition lead regarding technical symposium panel discussion.")
                        .build();

                SponsorLead s6 = SponsorLead.builder()
                        .companyName("L&T Technology Services")
                        .contactPerson("Mr. Karthik Narayanan")
                        .contactEmail("corporate.symposium@ltts.com")
                        .contactPhone("+91-9840678901")
                        .industryDomain("Engineering R&D & Embedded")
                        .sponsorshipTier("SUPPORTER")
                        .pledgedAmount(new BigDecimal("15000.00"))
                        .receivedAmount(new BigDecimal("15000.00"))
                        .sponsorshipStatus("CONFIRMED")
                        .paymentStatus("COMPLETED")
                        .symposiumEdition("2026")
                        .studentCoordinator("Dinesh Kumar (Student 3 - CSBS G-06)")
                        .mouOrBrochureLink("https://drive.google.com/file/d/sample-ltts-receipt/view")
                        .deliverablesNotes("Certificate co-branding and student robotics exhibition booth.")
                        .build();

                repository.saveAll(Arrays.asList(s1, s2, s3, s4, s5, s6));
                System.out.println(">>> Seeded 6 realistic symposium sponsorship corporate leads into database.");
            }
        };
    }
}
