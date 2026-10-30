package com.credit.simulator.model;

import java.math.BigDecimal;

public class YearlyCreditDetail {

    private BigDecimal principal;
    private BigDecimal interestRate;
    private BigDecimal totalLoan;
    private BigDecimal yearlyInstallment;
    private BigDecimal monthlyInstallment;

    public YearlyCreditDetail() {
    }

    public YearlyCreditDetail(
        BigDecimal principal,
        BigDecimal interestRate,
        BigDecimal totalLoan,
        BigDecimal yearlyInstallment,
        BigDecimal monthlyInstallment
    ) {
        this.principal = principal;
        this.interestRate = interestRate;
        this.totalLoan = totalLoan;
        this.yearlyInstallment = yearlyInstallment;
        this.monthlyInstallment = monthlyInstallment;
    }

    public BigDecimal getPrincipal() {
        return principal;
    }

    public void setPrincipal(BigDecimal principal) {
        this.principal = principal;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public BigDecimal getTotalLoan() {
        return totalLoan;
    }

    public void setTotalLoan(BigDecimal totalLoan) {
        this.totalLoan = totalLoan;
    }

    public BigDecimal getYearlyInstallment() {
        return yearlyInstallment;
    }

    public void setYearlyInstallment(BigDecimal yearlyInstallment) {
        this.yearlyInstallment = yearlyInstallment;
    }

    public BigDecimal getMonthlyInstallment() {
        return monthlyInstallment;
    }

    public void setMonthlyInstallment(BigDecimal monthlyInstallment) {
        this.monthlyInstallment = monthlyInstallment;
    }

}
