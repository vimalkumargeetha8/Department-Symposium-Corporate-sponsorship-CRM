package com.aiht.symposium.crm.service.impl;

import com.aiht.symposium.crm.dto.*;
import com.aiht.symposium.crm.entity.SponsorLead;
import com.aiht.symposium.crm.exception.BadRequestException;
import com.aiht.symposium.crm.exception.ResourceNotFoundException;
import com.aiht.symposium.crm.repository.SponsorLeadRepository;
import com.aiht.symposium.crm.service.SponsorLeadService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class SponsorLeadServiceImpl implements SponsorLeadService {

    private final SponsorLeadRepository repository;
    private static final BigDecimal DEFAULT_TARGET_GOAL = new BigDecimal("500000.00");

    public SponsorLeadServiceImpl(SponsorLeadRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<SponsorLeadResponseDto> getAllSponsors(String keyword, String tier, String status, String edition) {
        String cleanKeyword = (keyword != null && !keyword.trim().isEmpty()) ? keyword.trim() : null;
        String cleanTier = (tier != null && !tier.trim().isEmpty() && !tier.equalsIgnoreCase("ALL")) ? tier.trim() : null;
        String cleanStatus = (status != null && !status.trim().isEmpty() && !status.equalsIgnoreCase("ALL")) ? status.trim() : null;
        String cleanEdition = (edition != null && !edition.trim().isEmpty() && !edition.equalsIgnoreCase("ALL")) ? edition.trim() : null;

        List<SponsorLead> list = repository.searchSponsors(cleanKeyword, cleanTier, cleanStatus, cleanEdition);
        return list.stream().map(this::mapToResponseDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public SponsorLeadResponseDto getSponsorById(Long id) {
        SponsorLead sponsor = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sponsor not found with ID: " + id));
        return mapToResponseDto(sponsor);
    }

    @Override
    public SponsorLeadResponseDto createSponsor(SponsorLeadRequestDto requestDto) {
        validateAmounts(requestDto.getPledgedAmount(), requestDto.getReceivedAmount());

        SponsorLead entity = SponsorLead.builder()
                .companyName(requestDto.getCompanyName().trim())
                .contactPerson(requestDto.getContactPerson().trim())
                .contactEmail(requestDto.getContactEmail().trim())
                .contactPhone(requestDto.getContactPhone().trim())
                .industryDomain(requestDto.getIndustryDomain().trim())
                .sponsorshipTier(requestDto.getSponsorshipTier().trim().toUpperCase())
                .pledgedAmount(requestDto.getPledgedAmount())
                .receivedAmount(requestDto.getReceivedAmount() != null ? requestDto.getReceivedAmount() : BigDecimal.ZERO)
                .sponsorshipStatus(requestDto.getSponsorshipStatus().trim().toUpperCase())
                .paymentStatus(requestDto.getPaymentStatus().trim().toUpperCase())
                .symposiumEdition(requestDto.getSymposiumEdition() != null ? requestDto.getSymposiumEdition().trim() : "2026")
                .studentCoordinator(requestDto.getStudentCoordinator().trim())
                .mouOrBrochureLink(requestDto.getMouOrBrochureLink() != null ? requestDto.getMouOrBrochureLink().trim() : null)
                .deliverablesNotes(requestDto.getDeliverablesNotes() != null ? requestDto.getDeliverablesNotes().trim() : null)
                .build();

        SponsorLead saved = repository.save(entity);
        return mapToResponseDto(saved);
    }

    @Override
    public SponsorLeadResponseDto updateSponsor(Long id, SponsorLeadRequestDto requestDto) {
        validateAmounts(requestDto.getPledgedAmount(), requestDto.getReceivedAmount());

        SponsorLead existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot update non-existent sponsor with ID: " + id));

        existing.setCompanyName(requestDto.getCompanyName().trim());
        existing.setContactPerson(requestDto.getContactPerson().trim());
        existing.setContactEmail(requestDto.getContactEmail().trim());
        existing.setContactPhone(requestDto.getContactPhone().trim());
        existing.setIndustryDomain(requestDto.getIndustryDomain().trim());
        existing.setSponsorshipTier(requestDto.getSponsorshipTier().trim().toUpperCase());
        existing.setPledgedAmount(requestDto.getPledgedAmount());
        existing.setReceivedAmount(requestDto.getReceivedAmount() != null ? requestDto.getReceivedAmount() : BigDecimal.ZERO);
        existing.setSponsorshipStatus(requestDto.getSponsorshipStatus().trim().toUpperCase());
        existing.setPaymentStatus(requestDto.getPaymentStatus().trim().toUpperCase());
        existing.setSymposiumEdition(requestDto.getSymposiumEdition() != null ? requestDto.getSymposiumEdition().trim() : "2026");
        existing.setStudentCoordinator(requestDto.getStudentCoordinator().trim());
        existing.setMouOrBrochureLink(requestDto.getMouOrBrochureLink() != null ? requestDto.getMouOrBrochureLink().trim() : null);
        existing.setDeliverablesNotes(requestDto.getDeliverablesNotes() != null ? requestDto.getDeliverablesNotes().trim() : null);

        SponsorLead updated = repository.save(existing);
        return mapToResponseDto(updated);
    }

    @Override
    public void deleteSponsor(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Cannot delete non-existent sponsor with ID: " + id);
        }
        repository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public DashboardStatsDto getDashboardStats() {
        BigDecimal totalPledged = repository.getTotalPledgedAmount();
        BigDecimal totalReceived = repository.getTotalReceivedAmount();
        if (totalPledged == null) totalPledged = BigDecimal.ZERO;
        if (totalReceived == null) totalReceived = BigDecimal.ZERO;

        BigDecimal balanceRemaining = totalPledged.subtract(totalReceived);
        if (balanceRemaining.compareTo(BigDecimal.ZERO) < 0) {
            balanceRemaining = BigDecimal.ZERO;
        }

        long totalLeads = repository.count();
        long confirmed = repository.countBySponsorshipStatus("CONFIRMED");
        long negotiating = repository.countBySponsorshipStatus("NEGOTIATING");
        long contacted = repository.countBySponsorshipStatus("CONTACTED");

        double progress = 0.0;
        if (DEFAULT_TARGET_GOAL.compareTo(BigDecimal.ZERO) > 0) {
            progress = totalReceived.multiply(new BigDecimal(100))
                    .divide(DEFAULT_TARGET_GOAL, 2, RoundingMode.HALF_UP)
                    .doubleValue();
        }

        Map<String, Long> tierMap = new HashMap<>();
        String[] tiers = {"PLATINUM", "GOLD", "SILVER", "BRONZE", "SUPPORTER"};
        for (String t : tiers) {
            tierMap.put(t, repository.countBySponsorshipTier(t));
        }

        Map<String, Long> statusMap = new HashMap<>();
        String[] statuses = {"LEAD", "CONTACTED", "NEGOTIATING", "CONFIRMED", "DECLINED"};
        for (String s : statuses) {
            statusMap.put(s, repository.countBySponsorshipStatus(s));
        }

        return DashboardStatsDto.builder()
                .totalPledged(totalPledged)
                .totalReceived(totalReceived)
                .totalBalanceRemaining(balanceRemaining)
                .totalLeads(totalLeads)
                .confirmedCount(confirmed)
                .negotiatingCount(negotiating)
                .contactedCount(contacted)
                .targetGoal(DEFAULT_TARGET_GOAL)
                .targetProgressPercentage(progress)
                .tierBreakdown(tierMap)
                .statusBreakdown(statusMap)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public byte[] exportSponsorsCsv() {
        List<SponsorLead> list = repository.searchSponsors(null, null, null, null);
        StringBuilder sb = new StringBuilder();
        // CSV Header
        sb.append("ID,Company Name,Contact Person,Email,Phone,Industry Domain,Tier,Pledged (INR),Received (INR),Balance (INR),Status,Payment Status,Coordinator,Deliverables,Brochure Link,Edition,Created At\r\n");

        for (SponsorLead s : list) {
            BigDecimal received = s.getReceivedAmount() != null ? s.getReceivedAmount() : BigDecimal.ZERO;
            BigDecimal balance = s.getPledgedAmount().subtract(received);
            if (balance.compareTo(BigDecimal.ZERO) < 0) balance = BigDecimal.ZERO;

            sb.append(s.getId()).append(",")
              .append(escapeCsv(s.getCompanyName())).append(",")
              .append(escapeCsv(s.getContactPerson())).append(",")
              .append(escapeCsv(s.getContactEmail())).append(",")
              .append(escapeCsv(s.getContactPhone())).append(",")
              .append(escapeCsv(s.getIndustryDomain())).append(",")
              .append(escapeCsv(s.getSponsorshipTier())).append(",")
              .append(s.getPledgedAmount()).append(",")
              .append(received).append(",")
              .append(balance).append(",")
              .append(escapeCsv(s.getSponsorshipStatus())).append(",")
              .append(escapeCsv(s.getPaymentStatus())).append(",")
              .append(escapeCsv(s.getStudentCoordinator())).append(",")
              .append(escapeCsv(s.getDeliverablesNotes())).append(",")
              .append(escapeCsv(s.getMouOrBrochureLink())).append(",")
              .append(escapeCsv(s.getSymposiumEdition())).append(",")
              .append(s.getCreatedAt() != null ? s.getCreatedAt().toString() : "").append("\r\n");
        }

        return sb.toString().getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public SponsorLeadResponseDto updateQuickPayment(Long id, QuickPaymentRequestDto requestDto) {
        SponsorLead existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot update payment for non-existent sponsor with ID: " + id));

        BigDecimal newReceived = requestDto.getReceivedAmount();
        if (newReceived == null && requestDto.getBalanceAmount() != null) {
            newReceived = existing.getPledgedAmount().subtract(requestDto.getBalanceAmount());
            if (newReceived.compareTo(BigDecimal.ZERO) < 0) {
                newReceived = BigDecimal.ZERO;
            }
        } else if (newReceived == null) {
            newReceived = BigDecimal.ZERO;
        }

        if (newReceived.compareTo(existing.getPledgedAmount()) > 0) {
            throw new BadRequestException("Received amount (" + newReceived + ") cannot exceed pledged amount (" + existing.getPledgedAmount() + ")");
        }

        existing.setReceivedAmount(newReceived);

        if (requestDto.getPaymentStatus() != null && !requestDto.getPaymentStatus().trim().isEmpty()) {
            existing.setPaymentStatus(requestDto.getPaymentStatus().trim().toUpperCase());
        } else {
            if (newReceived.compareTo(BigDecimal.ZERO) == 0) {
                existing.setPaymentStatus("PENDING");
            } else if (newReceived.compareTo(existing.getPledgedAmount()) >= 0) {
                existing.setPaymentStatus("COMPLETED");
            } else {
                existing.setPaymentStatus("PARTIAL");
            }
        }

        SponsorLead updated = repository.save(existing);
        return mapToResponseDto(updated);
    }

    @Override
    public SponsorLeadResponseDto updateDeliverables(Long id, DeliverablesUpdateRequestDto requestDto) {
        SponsorLead existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot update deliverables for non-existent sponsor with ID: " + id));

        existing.setDeliverablesNotes(requestDto.getDeliverablesNotes());
        SponsorLead updated = repository.save(existing);
        return mapToResponseDto(updated);
    }

    @Override
    public SponsorLeadResponseDto updateStatus(Long id, StatusUpdateRequestDto requestDto) {
        SponsorLead existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot update status for non-existent sponsor with ID: " + id));

        existing.setSponsorshipStatus(requestDto.getSponsorshipStatus().trim().toUpperCase());
        SponsorLead updated = repository.save(existing);
        return mapToResponseDto(updated);
    }

    private String escapeCsv(Object val) {
        if (val == null) return "";
        String str = String.valueOf(val);
        if (str.contains(",") || str.contains("\"") || str.contains("\n") || str.contains("\r")) {
            return "\"" + str.replace("\"", "\"\"") + "\"";
        }
        return str;
    }

    private void validateAmounts(BigDecimal pledged, BigDecimal received) {
        if (pledged == null || pledged.compareTo(BigDecimal.ZERO) < 0) {
            throw new BadRequestException("Pledged amount must be greater than or equal to zero");
        }
        if (received != null && received.compareTo(pledged) > 0) {
            throw new BadRequestException("Received amount (" + received + ") cannot exceed pledged amount (" + pledged + ")");
        }
    }

    private SponsorLeadResponseDto mapToResponseDto(SponsorLead entity) {
        BigDecimal balance = entity.getPledgedAmount().subtract(
                entity.getReceivedAmount() != null ? entity.getReceivedAmount() : BigDecimal.ZERO
        );
        if (balance.compareTo(BigDecimal.ZERO) < 0) balance = BigDecimal.ZERO;

        return SponsorLeadResponseDto.builder()
                .id(entity.getId())
                .companyName(entity.getCompanyName())
                .contactPerson(entity.getContactPerson())
                .contactEmail(entity.getContactEmail())
                .contactPhone(entity.getContactPhone())
                .industryDomain(entity.getIndustryDomain())
                .sponsorshipTier(entity.getSponsorshipTier())
                .pledgedAmount(entity.getPledgedAmount())
                .receivedAmount(entity.getReceivedAmount())
                .balanceRemaining(balance)
                .sponsorshipStatus(entity.getSponsorshipStatus())
                .paymentStatus(entity.getPaymentStatus())
                .symposiumEdition(entity.getSymposiumEdition())
                .studentCoordinator(entity.getStudentCoordinator())
                .mouOrBrochureLink(entity.getMouOrBrochureLink())
                .deliverablesNotes(entity.getDeliverablesNotes())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}

