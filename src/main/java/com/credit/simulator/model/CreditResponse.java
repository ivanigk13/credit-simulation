package com.credit.simulator.model;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

public class CreditResponse {
    private BigDecimal vehiclePrice;
    private BigDecimal downPayment;
    private Integer loanTenure;
    private BigDecimal monthlyAverage;
    private Map<Integer, YearlyCreditDetail> yearlyDetails;

    public CreditResponse() {}

    public CreditResponse(BigDecimal vehiclePrice, BigDecimal downPayment, Integer loanTenure, BigDecimal monthlyAverage, Map<Integer, YearlyCreditDetail> yearlyDetails) {
        this.vehiclePrice = vehiclePrice;
        this.downPayment = downPayment;
        this.loanTenure = loanTenure;
        this.monthlyAverage = monthlyAverage;
        this.yearlyDetails = yearlyDetails;
    }

    public BigDecimal getVehiclePrice() {
        return vehiclePrice;
    }

    public void setVehiclePrice(BigDecimal vehiclePrice) {
        this.vehiclePrice = vehiclePrice;
    }

    public BigDecimal getDownPayment() {
        return downPayment;
    }

    public void setDownPayment(BigDecimal downPayment) {
        this.downPayment = downPayment;
    }

    public Integer getLoanTenure() {
        return loanTenure;
    }

    public void setLoanTenure(Integer loanTenure) {
        this.loanTenure = loanTenure;
    }

    public BigDecimal getMonthlyAverage() {
        return monthlyAverage;
    }

    public void setMonthlyAverage(BigDecimal monthlyAverage) {
        this.monthlyAverage = monthlyAverage;
    }

    public Map<Integer, YearlyCreditDetail> getYearlyDetails() {
        return yearlyDetails;
    }

    public void setYearlyDetails(Map<Integer, YearlyCreditDetail> yearlyDetails) {
        this.yearlyDetails = yearlyDetails;
    }
}
