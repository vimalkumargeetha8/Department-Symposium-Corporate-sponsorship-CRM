package com.aiht.symposium.crm.service;

import com.aiht.symposium.crm.dto.*;

import java.util.List;

public interface SponsorLeadService {

    List<SponsorLeadResponseDto> getAllSponsors(String keyword, String tier, String status, String edition);

    SponsorLeadResponseDto getSponsorById(Long id);

    SponsorLeadResponseDto createSponsor(SponsorLeadRequestDto requestDto);

    SponsorLeadResponseDto updateSponsor(Long id, SponsorLeadRequestDto requestDto);

    void deleteSponsor(Long id);

    DashboardStatsDto getDashboardStats();

    byte[] exportSponsorsCsv();

    SponsorLeadResponseDto updateQuickPayment(Long id, QuickPaymentRequestDto requestDto);

    SponsorLeadResponseDto updateDeliverables(Long id, DeliverablesUpdateRequestDto requestDto);

    SponsorLeadResponseDto updateStatus(Long id, StatusUpdateRequestDto requestDto);
}

