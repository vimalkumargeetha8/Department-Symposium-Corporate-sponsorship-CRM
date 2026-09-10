package com.aiht.symposium.crm.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class SponsorLeadRequestDto {

    @NotBlank(message = "Company name is required and cannot be blank")
    @Size(max = 150, message = "Company name cannot exceed 150 characters")
    private String companyName;

    @NotBlank(message = "Contact person name is required")
    @Size(max = 100, message = "Contact person name cannot exceed 100 characters")
    private String contactPerson;

    @NotBlank(message = "Contact email is required")
    @Email(message = "Please provide a valid corporate email address")
    private String contactEmail;

    @NotBlank(message = "Contact phone number is required")
    @Pattern(regexp = "^[+0-9\\-\\s()]{7,20}$", message = "Phone number must be a valid format with 7 to 20 digits")
    private String contactPhone;

    @NotBlank(message = "Industry domain is required")
    private String industryDomain;

    @NotBlank(message = "Sponsorship tier is required")
    @Pattern(regexp = "^(PLATINUM|GOLD|SILVER|BRONZE|SUPPORTER)$", 
             message = "Tier must be one of: PLATINUM, GOLD, SILVER, BRONZE, SUPPORTER")
    private String sponsorshipTier;

    @NotNull(message = "Pledged amount is required")
    @PositiveOrZero(message = "Pledged amount must be greater than or equal to 0")
    private BigDecimal pledgedAmount;

    @NotNull(message = "Received amount is required")
    @PositiveOrZero(message = "Received amount must be greater than or equal to 0")
    private BigDecimal receivedAmount;

    @NotBlank(message = "Sponsorship status is required")
    @Pattern(regexp = "^(LEAD|CONTACTED|NEGOTIATING|CONFIRMED|DECLINED)$", 
             message = "Status must be one of: LEAD, CONTACTED, NEGOTIATING, CONFIRMED, DECLINED")
    private String sponsorshipStatus;

    @NotBlank(message = "Payment status is required")
    @Pattern(regexp = "^(PENDING|PARTIAL|COMPLETED)$", 
             message = "Payment status must be one of: PENDING, PARTIAL, COMPLETED")
    private String paymentStatus;

    @NotBlank(message = "Symposium edition is required (e.g., 2026)")
    private String symposiumEdition;

    @NotBlank(message = "Student coordinator name is required")
    private String studentCoordinator;

    private String mouOrBrochureLink;

    private String deliverablesNotes;

    public SponsorLeadRequestDto() {
    }

    public SponsorLeadRequestDto(String companyName, String contactPerson, String contactEmail, String contactPhone,
                                 String industryDomain, String sponsorshipTier, BigDecimal pledgedAmount,
                                 BigDecimal receivedAmount, String sponsorshipStatus, String paymentStatus,
                                 String symposiumEdition, String studentCoordinator, String mouOrBrochureLink,
                                 String deliverablesNotes) {
        this.companyName = companyName;
        this.contactPerson = contactPerson;
        this.contactEmail = contactEmail;
        this.contactPhone = contactPhone;
        this.industryDomain = industryDomain;
        this.sponsorshipTier = sponsorshipTier;
        this.pledgedAmount = pledgedAmount;
        this.receivedAmount = receivedAmount;
        this.sponsorshipStatus = sponsorshipStatus;
        this.paymentStatus = paymentStatus;
        this.symposiumEdition = symposiumEdition;
        this.studentCoordinator = studentCoordinator;
        this.mouOrBrochureLink = mouOrBrochureLink;
        this.deliverablesNotes = deliverablesNotes;
    }

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
}
