package com.aiht.symposium.crm.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "sponsors", indexes = {
        @Index(name = "idx_company_name", columnList = "company_name"),
        @Index(name = "idx_sponsorship_tier", columnList = "sponsorship_tier"),
        @Index(name = "idx_sponsorship_status", columnList = "sponsorship_status"),
        @Index(name = "idx_symposium_edition", columnList = "symposium_edition"),
        @Index(name = "idx_student_coordinator", columnList = "student_coordinator")
})
public class SponsorLead {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "company_name", nullable = false, length = 150)
    private String companyName;

    @Column(name = "contact_person", nullable = false, length = 100)
    private String contactPerson;

    @Column(name = "contact_email", nullable = false, length = 120)
    private String contactEmail;

    @Column(name = "contact_phone", nullable = false, length = 20)
    private String contactPhone;

    @Column(name = "industry_domain", nullable = false, length = 80)
    private String industryDomain;

    @Column(name = "sponsorship_tier", nullable = false, length = 30)
    private String sponsorshipTier;

    @Column(name = "pledged_amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal pledgedAmount;

    @Column(name = "received_amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal receivedAmount = BigDecimal.ZERO;

    @Column(name = "sponsorship_status", nullable = false, length = 30)
    private String sponsorshipStatus;

    @Column(name = "payment_status", nullable = false, length = 30)
    private String paymentStatus;

    @Column(name = "symposium_edition", nullable = false, length = 20)
    private String symposiumEdition = "2026";

    @Column(name = "student_coordinator", nullable = false, length = 100)
    private String studentCoordinator;

    @Column(name = "mou_or_brochure_link", length = 500)
    private String mouOrBrochureLink;

    @Column(name = "deliverables_notes", columnDefinition = "TEXT")
    private String deliverablesNotes;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public SponsorLead() {
    }

    public SponsorLead(Long id, String companyName, String contactPerson, String contactEmail, String contactPhone,
                       String industryDomain, String sponsorshipTier, BigDecimal pledgedAmount, BigDecimal receivedAmount,
                       String sponsorshipStatus, String paymentStatus, String symposiumEdition, String studentCoordinator,
                       String mouOrBrochureLink, String deliverablesNotes, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.companyName = companyName;
        this.contactPerson = contactPerson;
        this.contactEmail = contactEmail;
        this.contactPhone = contactPhone;
        this.industryDomain = industryDomain;
        this.sponsorshipTier = sponsorshipTier;
        this.pledgedAmount = pledgedAmount;
        this.receivedAmount = receivedAmount != null ? receivedAmount : BigDecimal.ZERO;
        this.sponsorshipStatus = sponsorshipStatus;
        this.paymentStatus = paymentStatus;
        this.symposiumEdition = symposiumEdition != null ? symposiumEdition : "2026";
        this.studentCoordinator = studentCoordinator;
        this.mouOrBrochureLink = mouOrBrochureLink;
        this.deliverablesNotes = deliverablesNotes;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }

    public String getContactPerson() { return contactPerson; }
    public void setContactPerson(String contactPerson) { this.contactPerson = contactPerson; }

    public String getContactEmail() { return contactEmail; }
    public void setContactEmail(String contactEmail) { this.contactEmail = contactEmail; }

    public String getContactPhone() { return contactPhone; }
    public void setContactPhone(String contactPhone) { this.contactPhone = contactPhone; }

    public String getIndustryDomain() { return industryDomain; }
    public void setIndustryDomain(String industryDomain) { this.industryDomain = industryDomain; }

    public String getSponsorshipTier() { return sponsorshipTier; }
    public void setSponsorshipTier(String sponsorshipTier) { this.sponsorshipTier = sponsorshipTier; }

    public BigDecimal getPledgedAmount() { return pledgedAmount; }
    public void setPledgedAmount(BigDecimal pledgedAmount) { this.pledgedAmount = pledgedAmount; }

    public BigDecimal getReceivedAmount() { return receivedAmount; }
    public void setReceivedAmount(BigDecimal receivedAmount) { this.receivedAmount = receivedAmount; }

    public String getSponsorshipStatus() { return sponsorshipStatus; }
    public void setSponsorshipStatus(String sponsorshipStatus) { this.sponsorshipStatus = sponsorshipStatus; }

    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }

    public String getSymposiumEdition() { return symposiumEdition; }
    public void setSymposiumEdition(String symposiumEdition) { this.symposiumEdition = symposiumEdition; }

    public String getStudentCoordinator() { return studentCoordinator; }
    public void setStudentCoordinator(String studentCoordinator) { this.studentCoordinator = studentCoordinator; }

    public String getMouOrBrochureLink() { return mouOrBrochureLink; }
    public void setMouOrBrochureLink(String mouOrBrochureLink) { this.mouOrBrochureLink = mouOrBrochureLink; }

    public String getDeliverablesNotes() { return deliverablesNotes; }
    public void setDeliverablesNotes(String deliverablesNotes) { this.deliverablesNotes = deliverablesNotes; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public static class Builder {
        private Long id;
        private String companyName;
        private String contactPerson;
        private String contactEmail;
        private String contactPhone;
        private String industryDomain;
        private String sponsorshipTier;
        private BigDecimal pledgedAmount;
        private BigDecimal receivedAmount = BigDecimal.ZERO;
        private String sponsorshipStatus;
        private String paymentStatus;
        private String symposiumEdition = "2026";
        private String studentCoordinator;
        private String mouOrBrochureLink;
        private String deliverablesNotes;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder companyName(String companyName) { this.companyName = companyName; return this; }
        public Builder contactPerson(String contactPerson) { this.contactPerson = contactPerson; return this; }
        public Builder contactEmail(String contactEmail) { this.contactEmail = contactEmail; return this; }
        public Builder contactPhone(String contactPhone) { this.contactPhone = contactPhone; return this; }
        public Builder industryDomain(String industryDomain) { this.industryDomain = industryDomain; return this; }
        public Builder sponsorshipTier(String sponsorshipTier) { this.sponsorshipTier = sponsorshipTier; return this; }
        public Builder pledgedAmount(BigDecimal pledgedAmount) { this.pledgedAmount = pledgedAmount; return this; }
        public Builder receivedAmount(BigDecimal receivedAmount) { this.receivedAmount = receivedAmount; return this; }
        public Builder sponsorshipStatus(String sponsorshipStatus) { this.sponsorshipStatus = sponsorshipStatus; return this; }
        public Builder paymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; return this; }
        public Builder symposiumEdition(String symposiumEdition) { this.symposiumEdition = symposiumEdition; return this; }
        public Builder studentCoordinator(String studentCoordinator) { this.studentCoordinator = studentCoordinator; return this; }
        public Builder mouOrBrochureLink(String mouOrBrochureLink) { this.mouOrBrochureLink = mouOrBrochureLink; return this; }
        public Builder deliverablesNotes(String deliverablesNotes) { this.deliverablesNotes = deliverablesNotes; return this; }
        public Builder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public Builder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }

        public SponsorLead build() {
            return new SponsorLead(id, companyName, contactPerson, contactEmail, contactPhone,
                    industryDomain, sponsorshipTier, pledgedAmount, receivedAmount, sponsorshipStatus,
                    paymentStatus, symposiumEdition, studentCoordinator, mouOrBrochureLink,
                    deliverablesNotes, createdAt, updatedAt);
        }
    }
}
