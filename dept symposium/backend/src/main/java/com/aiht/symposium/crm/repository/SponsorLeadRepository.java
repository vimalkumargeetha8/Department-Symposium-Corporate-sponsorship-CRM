package com.aiht.symposium.crm.repository;

import com.aiht.symposium.crm.entity.SponsorLead;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface SponsorLeadRepository extends JpaRepository<SponsorLead, Long> {

    List<SponsorLead> findByCompanyNameContainingIgnoreCase(String companyName);

    List<SponsorLead> findBySponsorshipTier(String sponsorshipTier);

    List<SponsorLead> findBySponsorshipStatus(String sponsorshipStatus);

    List<SponsorLead> findBySymposiumEdition(String symposiumEdition);

    @Query("SELECT s FROM SponsorLead s WHERE " +
           "(:keyword IS NULL OR LOWER(s.companyName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           " LOWER(s.contactPerson) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           " LOWER(s.industryDomain) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           " LOWER(s.studentCoordinator) LIKE LOWER(CONCAT('%', :keyword, '%'))) AND " +
           "(:tier IS NULL OR s.sponsorshipTier = :tier) AND " +
           "(:status IS NULL OR s.sponsorshipStatus = :status) AND " +
           "(:edition IS NULL OR s.symposiumEdition = :edition) " +
           "ORDER BY s.pledgedAmount DESC, s.createdAt DESC")
    List<SponsorLead> searchSponsors(
            @Param("keyword") String keyword,
            @Param("tier") String tier,
            @Param("status") String status,
            @Param("edition") String edition
    );

    @Query("SELECT COALESCE(SUM(s.pledgedAmount), 0) FROM SponsorLead s")
    BigDecimal getTotalPledgedAmount();

    @Query("SELECT COALESCE(SUM(s.receivedAmount), 0) FROM SponsorLead s")
    BigDecimal getTotalReceivedAmount();

    long countBySponsorshipStatus(String sponsorshipStatus);

    long countBySponsorshipTier(String sponsorshipTier);
}
