package com.aiht.symposium.crm.controller;

import com.aiht.symposium.crm.dto.*;
import com.aiht.symposium.crm.service.SponsorLeadService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sponsors")
@CrossOrigin(origins = "*")
public class SponsorLeadController {

    private final SponsorLeadService sponsorLeadService;

    public SponsorLeadController(SponsorLeadService sponsorLeadService) {
        this.sponsorLeadService = sponsorLeadService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<SponsorLeadResponseDto>>> getAllSponsors(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String tier,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String edition
    ) {
        List<SponsorLeadResponseDto> list = sponsorLeadService.getAllSponsors(keyword, tier, status, edition);
        return ResponseEntity.ok(ApiResponse.success("Sponsors fetched successfully", list));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SponsorLeadResponseDto>> getSponsorById(@PathVariable Long id) {
        SponsorLeadResponseDto sponsor = sponsorLeadService.getSponsorById(id);
        return ResponseEntity.ok(ApiResponse.success("Sponsor retrieved successfully", sponsor));
    }

    @GetMapping("/stats")
    public ResponseEntity<ApiResponse<DashboardStatsDto>> getDashboardStats() {
        DashboardStatsDto stats = sponsorLeadService.getDashboardStats();
        return ResponseEntity.ok(ApiResponse.success("Dashboard metrics aggregated successfully", stats));
    }

    @GetMapping("/export/csv")
    public ResponseEntity<byte[]> exportSponsorsCsv() {
        byte[] csvData = sponsorLeadService.exportSponsorsCsv();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"sponsorship_leads_nexus26.csv\"")
                .contentType(MediaType.parseMediaType("text/csv; charset=UTF-8"))
                .body(csvData);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<SponsorLeadResponseDto>> createSponsor(
            @Valid @RequestBody SponsorLeadRequestDto requestDto
    ) {
        SponsorLeadResponseDto created = sponsorLeadService.createSponsor(requestDto);
        return new ResponseEntity<>(
                ApiResponse.success("Corporate sponsor registered successfully", created),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<SponsorLeadResponseDto>> updateSponsor(
            @PathVariable Long id,
            @Valid @RequestBody SponsorLeadRequestDto requestDto
    ) {
        SponsorLeadResponseDto updated = sponsorLeadService.updateSponsor(id, requestDto);
        return ResponseEntity.ok(ApiResponse.success("Corporate sponsor updated successfully", updated));
    }

    @PatchMapping("/{id}/quick-payment")
    public ResponseEntity<ApiResponse<SponsorLeadResponseDto>> updateQuickPayment(
            @PathVariable Long id,
            @Valid @RequestBody QuickPaymentRequestDto requestDto
    ) {
        SponsorLeadResponseDto updated = sponsorLeadService.updateQuickPayment(id, requestDto);
        return ResponseEntity.ok(ApiResponse.success("Payment recorded successfully", updated));
    }

    @PatchMapping("/{id}/deliverables")
    public ResponseEntity<ApiResponse<SponsorLeadResponseDto>> updateDeliverables(
            @PathVariable Long id,
            @RequestBody DeliverablesUpdateRequestDto requestDto
    ) {
        SponsorLeadResponseDto updated = sponsorLeadService.updateDeliverables(id, requestDto);
        return ResponseEntity.ok(ApiResponse.success("Deliverables notes updated successfully", updated));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<SponsorLeadResponseDto>> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody StatusUpdateRequestDto requestDto
    ) {
        SponsorLeadResponseDto updated = sponsorLeadService.updateStatus(id, requestDto);
        return ResponseEntity.ok(ApiResponse.success("Sponsorship status updated successfully", updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteSponsor(@PathVariable Long id) {
        sponsorLeadService.deleteSponsor(id);
        return ResponseEntity.ok(ApiResponse.success("Corporate sponsor deleted successfully with ID: " + id, null));
    }
}

