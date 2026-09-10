package com.aiht.symposium.crm.controller;

import com.aiht.symposium.crm.dto.DashboardStatsDto;
import com.aiht.symposium.crm.dto.QuickPaymentRequestDto;
import com.aiht.symposium.crm.dto.SponsorLeadResponseDto;
import com.aiht.symposium.crm.service.SponsorLeadService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SponsorLeadController.class)
class SponsorLeadControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SponsorLeadService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("GET /api/sponsors should return 200 with list of sponsors")
    void testGetAllSponsorsEndpoint() throws Exception {
        SponsorLeadResponseDto dto = SponsorLeadResponseDto.builder()
                .id(1L)
                .companyName("Zoho Corporation")
                .contactPerson("Kavitha Ramesh")
                .contactEmail("kavitha@zoho.com")
                .contactPhone("+91 98401 11223")
                .industryDomain("Enterprise SaaS")
                .sponsorshipTier("PLATINUM")
                .pledgedAmount(new BigDecimal("100000.00"))
                .receivedAmount(new BigDecimal("50000.00"))
                .balanceRemaining(new BigDecimal("50000.00"))
                .sponsorshipStatus("CONFIRMED")
                .paymentStatus("PARTIAL")
                .symposiumEdition("2026")
                .studentCoordinator("Vimal CSBS")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        when(service.getAllSponsors(null, null, null, null)).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/sponsors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data[0].companyName").value("Zoho Corporation"))
                .andExpect(jsonPath("$.data[0].sponsorshipTier").value("PLATINUM"));
    }

    @Test
    @DisplayName("GET /api/sponsors/stats should return aggregated dashboard metrics")
    void testGetDashboardStatsEndpoint() throws Exception {
        DashboardStatsDto stats = DashboardStatsDto.builder()
                .totalPledged(new BigDecimal("350000.00"))
                .totalReceived(new BigDecimal("185000.00"))
                .totalBalanceRemaining(new BigDecimal("165000.00"))
                .totalLeads(8)
                .confirmedCount(3)
                .negotiatingCount(2)
                .contactedCount(2)
                .targetGoal(new BigDecimal("500000.00"))
                .targetProgressPercentage(37.0)
                .tierBreakdown(Map.of("PLATINUM", 2L, "GOLD", 3L))
                .statusBreakdown(Map.of("CONFIRMED", 3L, "LEAD", 1L))
                .build();

        when(service.getDashboardStats()).thenReturn(stats);

        mockMvc.perform(get("/api/sponsors/stats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.totalPledged").value(350000.00))
                .andExpect(jsonPath("$.data.totalReceived").value(185000.00));
    }

    @Test
    @DisplayName("GET /api/sponsors/export/csv should return CSV file with appropriate headers")
    void testExportCsvEndpoint() throws Exception {
        byte[] csv = "ID,Company Name,Tier\n1,Zoho,PLATINUM\n".getBytes();
        when(service.exportSponsorsCsv()).thenReturn(csv);

        mockMvc.perform(get("/api/sponsors/export/csv"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Disposition", "attachment; filename=\"sponsorship_leads_nexus26.csv\""))
                .andExpect(content().contentType("text/csv; charset=UTF-8"))
                .andExpect(content().string("ID,Company Name,Tier\n1,Zoho,PLATINUM\n"));
    }

    @Test
    @DisplayName("PATCH /api/sponsors/{id}/quick-payment should update payment amount")
    void testQuickPaymentEndpoint() throws Exception {
        QuickPaymentRequestDto req = new QuickPaymentRequestDto(new BigDecimal("75000.00"), "PARTIAL");
        SponsorLeadResponseDto resp = SponsorLeadResponseDto.builder()
                .id(1L)
                .companyName("Zoho Corporation")
                .receivedAmount(new BigDecimal("75000.00"))
                .balanceRemaining(new BigDecimal("25000.00"))
                .paymentStatus("PARTIAL")
                .build();

        when(service.updateQuickPayment(eq(1L), any(QuickPaymentRequestDto.class))).thenReturn(resp);

        mockMvc.perform(patch("/api/sponsors/1/quick-payment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.receivedAmount").value(75000.00))
                .andExpect(jsonPath("$.data.paymentStatus").value("PARTIAL"));
    }
}
