package com.credit.simulator.service;

import com.credit.simulator.enums.VehicleType;
import com.credit.simulator.model.CreditRequest;
import com.credit.simulator.model.CreditResponse;
import com.credit.simulator.model.YearlyCreditDetail;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedHashMap;
import java.util.Map;

public class CreditServiceImpl implements CreditService {

    @Override
    public CreditResponse calculate(CreditRequest creditRequest) {
        Map<Integer, YearlyCreditDetail> yearlyDetails = new LinkedHashMap<>();

        BigDecimal vehiclePrice = creditRequest.getTotalLoanAmount();
        BigDecimal downPayment = creditRequest.getDownPayment();
        BigDecimal principal = vehiclePrice.subtract(downPayment);
        BigDecimal totalYearlyInstallment = BigDecimal.ZERO;
        for (int year = 1; year <= creditRequest.getLoanTenure(); year++) {
            BigDecimal rate = getInterestRate(creditRequest.getVehicleType(), year);
            BigDecimal totalLoan = principal.add(principal.multiply(rate));
            int remainingTenure = creditRequest.getLoanTenure() - year + 1;
            BigDecimal yearlyInstallment = totalLoan.divide(BigDecimal.valueOf(remainingTenure), 2, RoundingMode.HALF_UP);
            BigDecimal monthlyInstallment = yearlyInstallment.divide(BigDecimal.valueOf(12), 2, RoundingMode.HALF_UP);
            YearlyCreditDetail detail = new YearlyCreditDetail(
                    principal,
                    rate,
                    totalLoan,
                    yearlyInstallment,
                    monthlyInstallment
                );
            yearlyDetails.put(year, detail);
            totalYearlyInstallment = totalYearlyInstallment.add(yearlyInstallment);
            principal = totalLoan.subtract(yearlyInstallment);
        }

        BigDecimal monthlyAverage = totalYearlyInstallment
            .divide(BigDecimal.valueOf(creditRequest.getLoanTenure() * 12L), 2, RoundingMode.HALF_UP);

        return new CreditResponse(
            vehiclePrice,
            downPayment,
            creditRequest.getLoanTenure(),
            monthlyAverage,
            yearlyDetails
        );
    }

    @Override
    public void displaySimulation(CreditResponse creditResponse) {
        creditResponse.getYearlyDetails()
            .forEach((year, detail) ->
                System.out.printf("tahun %d : Rp. %,.2f/bln , Suku Bunga : %.1f%%%n",
                year,
                detail.getMonthlyInstallment(),
                detail.getInterestRate().multiply(BigDecimal.valueOf(100))
            ));
    }

    private BigDecimal getInterestRate(
        VehicleType vehicleType,
        int year) {

        BigDecimal baseRate = vehicleType == VehicleType.MOBIL
                ? BigDecimal.valueOf(0.08)
                : BigDecimal.valueOf(0.09);

        if (year == 1) {
            return baseRate;
        }

        if (year % 2 == 0) {
            return baseRate.add(
                BigDecimal.valueOf(0.001 * (year - 1))
            );
        }

        return baseRate.add(
            BigDecimal.valueOf(0.001 * (year - 2))
                .add(BigDecimal.valueOf(0.005))
        );
    }
}
