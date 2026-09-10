package com.aiht.symposium.crm.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

public class QuickPaymentRequestDto {

    @PositiveOrZero(message = "Received amount must be positive or zero")
    private BigDecimal receivedAmount;

    @PositiveOrZero(message = "Balance amount must be positive or zero")
    private BigDecimal balanceAmount;

    @Pattern(regexp = "^(PENDING|PARTIAL|COMPLETED)$", message = "Payment status must be PENDING, PARTIAL, or COMPLETED")
    private String paymentStatus;

    public QuickPaymentRequestDto() {}

    public QuickPaymentRequestDto(BigDecimal receivedAmount, String paymentStatus) {
        this.receivedAmount = receivedAmount;
        this.paymentStatus = paymentStatus;
    }

    public QuickPaymentRequestDto(BigDecimal receivedAmount, BigDecimal balanceAmount, String paymentStatus) {
        this.receivedAmount = receivedAmount;
        this.balanceAmount = balanceAmount;
        this.paymentStatus = paymentStatus;
    }

    public BigDecimal getReceivedAmount() { return receivedAmount; }
    public void setReceivedAmount(BigDecimal receivedAmount) { this.receivedAmount = receivedAmount; }

    public BigDecimal getBalanceAmount() { return balanceAmount; }
    public void setBalanceAmount(BigDecimal balanceAmount) { this.balanceAmount = balanceAmount; }

    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }
}

