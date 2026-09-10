package com.aiht.symposium.crm.dto;

import java.math.BigDecimal;
import java.util.Map;

public class DashboardStatsDto {

    private BigDecimal totalPledged;
    private BigDecimal totalReceived;
    private BigDecimal totalBalanceRemaining;
    private long totalLeads;
    private long confirmedCount;
    private long negotiatingCount;
    private long contactedCount;
    private BigDecimal targetGoal;
    private double targetProgressPercentage;
    private Map<String, Long> tierBreakdown;
    private Map<String, Long> statusBreakdown;

    public DashboardStatsDto() {
    }

    public DashboardStatsDto(BigDecimal totalPledged, BigDecimal totalReceived, BigDecimal totalBalanceRemaining,
                             long totalLeads, long confirmedCount, long negotiatingCount, long contactedCount,
                             BigDecimal targetGoal, double targetProgressPercentage,
                             Map<String, Long> tierBreakdown, Map<String, Long> statusBreakdown) {
        this.totalPledged = totalPledged;
        this.totalReceived = totalReceived;
        this.totalBalanceRemaining = totalBalanceRemaining;
        this.totalLeads = totalLeads;
        this.confirmedCount = confirmedCount;
        this.negotiatingCount = negotiatingCount;
        this.contactedCount = contactedCount;
        this.targetGoal = targetGoal;
        this.targetProgressPercentage = targetProgressPercentage;
        this.tierBreakdown = tierBreakdown;
        this.statusBreakdown = statusBreakdown;
    }

    public static Builder builder() {
        return new Builder();
    }

    public BigDecimal getTotalPledged() { return totalPledged; }
    public void setTotalPledged(BigDecimal totalPledged) { this.totalPledged = totalPledged; }

    public BigDecimal getTotalReceived() { return totalReceived; }
    public void setTotalReceived(BigDecimal totalReceived) { this.totalReceived = totalReceived; }

    public BigDecimal getTotalBalanceRemaining() { return totalBalanceRemaining; }
    public void setTotalBalanceRemaining(BigDecimal totalBalanceRemaining) { this.totalBalanceRemaining = totalBalanceRemaining; }

    public long getTotalLeads() { return totalLeads; }
    public void setTotalLeads(long totalLeads) { this.totalLeads = totalLeads; }

    public long getConfirmedCount() { return confirmedCount; }
    public void setConfirmedCount(long confirmedCount) { this.confirmedCount = confirmedCount; }

    public long getNegotiatingCount() { return negotiatingCount; }
    public void setNegotiatingCount(long negotiatingCount) { this.negotiatingCount = negotiatingCount; }

    public long getContactedCount() { return contactedCount; }
    public void setContactedCount(long contactedCount) { this.contactedCount = contactedCount; }

    public BigDecimal getTargetGoal() { return targetGoal; }
    public void setTargetGoal(BigDecimal targetGoal) { this.targetGoal = targetGoal; }

    public double getTargetProgressPercentage() { return targetProgressPercentage; }
    public void setTargetProgressPercentage(double targetProgressPercentage) { this.targetProgressPercentage = targetProgressPercentage; }

    public Map<String, Long> getTierBreakdown() { return tierBreakdown; }
    public void setTierBreakdown(Map<String, Long> tierBreakdown) { this.tierBreakdown = tierBreakdown; }

    public Map<String, Long> getStatusBreakdown() { return statusBreakdown; }
    public void setStatusBreakdown(Map<String, Long> statusBreakdown) { this.statusBreakdown = statusBreakdown; }

    public static class Builder {
        private BigDecimal totalPledged;
        private BigDecimal totalReceived;
        private BigDecimal totalBalanceRemaining;
        private long totalLeads;
        private long confirmedCount;
        private long negotiatingCount;
        private long contactedCount;
        private BigDecimal targetGoal;
        private double targetProgressPercentage;
        private Map<String, Long> tierBreakdown;
        private Map<String, Long> statusBreakdown;

        public Builder totalPledged(BigDecimal totalPledged) { this.totalPledged = totalPledged; return this; }
        public Builder totalReceived(BigDecimal totalReceived) { this.totalReceived = totalReceived; return this; }
        public Builder totalBalanceRemaining(BigDecimal totalBalanceRemaining) { this.totalBalanceRemaining = totalBalanceRemaining; return this; }
        public Builder totalLeads(long totalLeads) { this.totalLeads = totalLeads; return this; }
        public Builder confirmedCount(long confirmedCount) { this.confirmedCount = confirmedCount; return this; }
        public Builder negotiatingCount(long negotiatingCount) { this.negotiatingCount = negotiatingCount; return this; }
        public Builder contactedCount(long contactedCount) { this.contactedCount = contactedCount; return this; }
        public Builder targetGoal(BigDecimal targetGoal) { this.targetGoal = targetGoal; return this; }
        public Builder targetProgressPercentage(double targetProgressPercentage) { this.targetProgressPercentage = targetProgressPercentage; return this; }
        public Builder tierBreakdown(Map<String, Long> tierBreakdown) { this.tierBreakdown = tierBreakdown; return this; }
        public Builder statusBreakdown(Map<String, Long> statusBreakdown) { this.statusBreakdown = statusBreakdown; return this; }

        public DashboardStatsDto build() {
            return new DashboardStatsDto(totalPledged, totalReceived, totalBalanceRemaining, totalLeads,
                    confirmedCount, negotiatingCount, contactedCount, targetGoal, targetProgressPercentage,
                    tierBreakdown, statusBreakdown);
        }
    }
}
