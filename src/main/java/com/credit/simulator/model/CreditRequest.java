package com.credit.simulator.model;

import com.credit.simulator.enums.VehicleCondition;
import com.credit.simulator.enums.VehicleType;

import java.math.BigDecimal;

public class CreditRequest {
    private VehicleType vehicleType;
    private VehicleCondition vehicleCondition;
    private int vehicleYear;
    private BigDecimal totalLoanAmount;
    private int loanTenure;
    private BigDecimal downPayment;

    public CreditRequest(){}

    public CreditRequest(VehicleType vehicleType, VehicleCondition vehicleCondition, int vehicleYear, BigDecimal totalLoanAmount, int loanTenure, BigDecimal downPayment) {
        this.vehicleType = vehicleType;
        this.vehicleCondition = vehicleCondition;
        this.vehicleYear = vehicleYear;
        this.totalLoanAmount = totalLoanAmount;
        this.loanTenure = loanTenure;
        this.downPayment = downPayment;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public VehicleCondition getVehicleCondition() {
        return vehicleCondition;
    }

    public void setVehicleCondition(VehicleCondition vehicleCondition) {
        this.vehicleCondition = vehicleCondition;
    }

    public int getVehicleYear() {
        return vehicleYear;
    }

    public void setVehicleYear(int vehicleYear) {
        this.vehicleYear = vehicleYear;
    }

    public BigDecimal getTotalLoanAmount() {
        return totalLoanAmount;
    }

    public void setTotalLoanAmount(BigDecimal totalLoanAmount) {
        this.totalLoanAmount = totalLoanAmount;
    }

    public int getLoanTenure() {
        return loanTenure;
    }

    public void setLoanTenure(int loanTenure) {
        this.loanTenure = loanTenure;
    }

    public BigDecimal getDownPayment() {
        return downPayment;
    }

    public void setDownPayment(BigDecimal downPayment) {
        this.downPayment = downPayment;
    }
}
