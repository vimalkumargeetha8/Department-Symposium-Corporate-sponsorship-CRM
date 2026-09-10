package com.aiht.symposium.crm.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class StatusUpdateRequestDto {

    @NotBlank(message = "Sponsorship status is required")
    @Pattern(regexp = "^(LEAD|CONTACTED|NEGOTIATING|CONFIRMED|DECLINED)$",
            message = "Status must be LEAD, CONTACTED, NEGOTIATING, CONFIRMED, or DECLINED")
    private String sponsorshipStatus;

    public StatusUpdateRequestDto() {}

    public StatusUpdateRequestDto(String sponsorshipStatus) {
        this.sponsorshipStatus = sponsorshipStatus;
    }

    public String getSponsorshipStatus() { return sponsorshipStatus; }
    public void setSponsorshipStatus(String sponsorshipStatus) { this.sponsorshipStatus = sponsorshipStatus; }
}
