package com.aiht.symposium.crm.service;

import com.aiht.symposium.crm.dto.*;
import com.aiht.symposium.crm.entity.SponsorLead;
import com.aiht.symposium.crm.exception.BadRequestException;
import com.aiht.symposium.crm.exception.ResourceNotFoundException;
import com.aiht.symposium.crm.repository.SponsorLeadRepository;
import com.aiht.symposium.crm.service.impl.SponsorLeadServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SponsorLeadServiceTest {

    @Mock
    private SponsorLeadRepository repository;

    @InjectMocks
    private SponsorLeadServiceImpl service;

    private SponsorLead sampleSponsor;

    @BeforeEach
    void setUp() {
        sampleSponsor = SponsorLead.builder()
                .id(1L)
                .companyName("Zoho Corporation")
                .contactPerson("Kavitha Ramesh")
                .contactEmail("kavitha@zoho.com")
                .contactPhone("+91 98401 11223")
                .industryDomain("Enterprise SaaS")
                .sponsorshipTier("PLATINUM")
                .pledgedAmount(new BigDecimal("100000.00"))
                .receivedAmount(new BigDecimal("50000.00"))
                .sponsorshipStatus("CONFIRMED")
                .paymentStatus("PARTIAL")
                .symposiumEdition("2026")
                .studentCoordinator("Vimal CSBS")
                .mouOrBrochureLink("https://drive.google.com/mou-zoho")
                .deliverablesNotes("Keynote slot, 2 premium stalls")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    @Test
    @DisplayName("Should return all sponsors matching search query")
    void testGetAllSponsors() {
        when(repository.searchSponsors("Zoho", "PLATINUM", "CONFIRMED", "2026"))
                .thenReturn(List.of(sampleSponsor));

        List<SponsorLeadResponseDto> result = service.getAllSponsors("Zoho", "PLATINUM", "CONFIRMED", "2026");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Zoho Corporation", result.get(0).getCompanyName());
        assertEquals(new BigDecimal("50000.00"), result.get(0).getBalanceRemaining());
        verify(repository, times(1)).searchSponsors("Zoho", "PLATINUM", "CONFIRMED", "2026");
    }

    @Test
    @DisplayName("Should fetch sponsor by valid ID")
    void testGetSponsorById_Success() {
        when(repository.findById(1L)).thenReturn(Optional.of(sampleSponsor));

        SponsorLeadResponseDto dto = service.getSponsorById(1L);

        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals("Zoho Corporation", dto.getCompanyName());
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when ID is invalid")
    void testGetSponsorById_NotFound() {
        when(repository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.getSponsorById(999L));
    }

    @Test
    @DisplayName("Should create new sponsor lead successfully")
    void testCreateSponsor_Success() {
        SponsorLeadRequestDto request = new SponsorLeadRequestDto(
                "Freshworks Inc", "Arun Kumar", "arun@freshworks.com", "+91 98402 33445",
                "Customer Experience SaaS", "GOLD", new BigDecimal("50000.00"),
                BigDecimal.ZERO, "CONTACTED", "PENDING", "2026", "Vimal CSBS",
                null, "Banner display and website logo"
        );

        when(repository.save(any(SponsorLead.class))).thenAnswer(invocation -> {
            SponsorLead saved = invocation.getArgument(0);
            saved.setId(2L);
            return saved;
        });

        SponsorLeadResponseDto response = service.createSponsor(request);

        assertNotNull(response);
        assertEquals(2L, response.getId());
        assertEquals("Freshworks Inc", response.getCompanyName());
        assertEquals(new BigDecimal("50000.00"), response.getBalanceRemaining());
    }

    @Test
    @DisplayName("Should reject sponsor creation when received exceeds pledged")
    void testCreateSponsor_ValidationFailure() {
        SponsorLeadRequestDto request = new SponsorLeadRequestDto(
                "Invalid Corp", "Tester", "test@corp.com", "+91 99999 99999",
                "Tech", "SILVER", new BigDecimal("10000.00"),
                new BigDecimal("20000.00"), "LEAD", "PENDING", "2026", "Vimal CSBS",
                null, null
        );

        assertThrows(BadRequestException.class, () -> service.createSponsor(request));
    }

    @Test
    @DisplayName("Should update quick payment and auto-update payment status to COMPLETED when fully paid")
    void testUpdateQuickPayment_Completed() {
        when(repository.findById(1L)).thenReturn(Optional.of(sampleSponsor));
        when(repository.save(any(SponsorLead.class))).thenAnswer(invocation -> invocation.getArgument(0));

        QuickPaymentRequestDto paymentDto = new QuickPaymentRequestDto(new BigDecimal("100000.00"), null);
        SponsorLeadResponseDto updated = service.updateQuickPayment(1L, paymentDto);

        assertNotNull(updated);
        assertEquals(new BigDecimal("100000.00"), updated.getReceivedAmount());
        assertEquals(0, updated.getBalanceRemaining().compareTo(BigDecimal.ZERO));
        assertEquals("COMPLETED", updated.getPaymentStatus());
    }

    @Test
    @DisplayName("Should update quick payment via direct balanceAmount and auto-compute receivedAmount")
    void testUpdateQuickPayment_DirectBalanceAmount() {
        when(repository.findById(1L)).thenReturn(Optional.of(sampleSponsor));
        when(repository.save(any(SponsorLead.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Pledged is 100,000. If balance is set to 20,000, received should be 80,000 and status PARTIAL
        QuickPaymentRequestDto paymentDto = new QuickPaymentRequestDto(null, new BigDecimal("20000.00"), null);
        SponsorLeadResponseDto updated = service.updateQuickPayment(1L, paymentDto);

        assertNotNull(updated);
        assertEquals(new BigDecimal("80000.00"), updated.getReceivedAmount());
        assertEquals(new BigDecimal("20000.00"), updated.getBalanceRemaining());
        assertEquals("PARTIAL", updated.getPaymentStatus());
    }

    @Test
    @DisplayName("Should export sponsors list as formatted CSV")
    void testExportSponsorsCsv() {
        when(repository.searchSponsors(null, null, null, null)).thenReturn(List.of(sampleSponsor));

        byte[] csv = service.exportSponsorsCsv();

        assertNotNull(csv);
        String csvText = new String(csv);
        assertTrue(csvText.contains("Company Name"));
        assertTrue(csvText.contains("Zoho Corporation"));
        assertTrue(csvText.contains("100000.00"));
    }

    @Test
    @DisplayName("Should update status transition for Kanban workflow")
    void testUpdateStatus_Success() {
        when(repository.findById(1L)).thenReturn(Optional.of(sampleSponsor));
        when(repository.save(any(SponsorLead.class))).thenAnswer(invocation -> invocation.getArgument(0));

        StatusUpdateRequestDto statusDto = new StatusUpdateRequestDto("CONFIRMED");
        SponsorLeadResponseDto response = service.updateStatus(1L, statusDto);

        assertNotNull(response);
        assertEquals("CONFIRMED", response.getSponsorshipStatus());
    }
}
