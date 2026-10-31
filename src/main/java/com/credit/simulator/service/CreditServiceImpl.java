package com.credit.simulator.service;

import com.credit.simulator.enums.VehicleCondition;
import com.credit.simulator.enums.VehicleType;
import com.credit.simulator.model.CreditRequest;
import com.credit.simulator.model.CreditResponse;
import com.credit.simulator.model.YearlyCreditDetail;
import com.credit.simulator.utils.FormatUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

public class CreditServiceImpl implements CreditService {

    private static final String URL = "https://run.mocky.io/v3/9108b1da-beec-409e-ae14-e8091955666c";

    @Override
    public CreditResponse calculate(CreditRequest creditRequest) {
        Map<Integer, YearlyCreditDetail> yearlyDetails = new LinkedHashMap<>();
        String vehicle = String.format("%s %s", creditRequest.getVehicleType().getName(), creditRequest.getVehicleCondition().getName());
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
            vehicle,
            vehiclePrice,
            downPayment,
            creditRequest.getLoanTenure(),
            monthlyAverage,
            yearlyDetails
        );
    }

    @Override
    public void displaySimulation(CreditResponse creditResponse) {
        System.out.println("\n=== Hasil Simulasi Kendaraan ===");
        System.out.println("Kendaraan: " + creditResponse.getVehicle());
        System.out.println("Harga Kendaraan: Rp. " +  FormatUtils.moneyFormat(creditResponse.getVehiclePrice()));
        System.out.println("Tenor: " + creditResponse.getLoanTenure());
        creditResponse.getYearlyDetails()
            .forEach((year, detail) ->
                System.out.printf("tahun %d : Rp. %,.2f/bln , Suku Bunga : %.1f%%%n",
                year,
                detail.getMonthlyInstallment(),
                detail.getInterestRate().multiply(BigDecimal.valueOf(100))
            ));
        System.out.println("Rata-rata perbulan: Rp. " + FormatUtils.moneyFormat(creditResponse.getMonthlyAverage()));
    }

    @Override
    public CreditRequest loadFromAPI() {
        try {
            URL url = new URL(URL);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder json = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                json.append(line);
            }
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(json.toString(), CreditRequest.class);

        } catch (Exception e) {
            System.out.println("Endpoint tidak dapat diakses, menggunakan data sample.");
            return createDefaultRequest();
        }
    }

    private CreditRequest createDefaultRequest() {
        CreditRequest request = new CreditRequest();
        request.setVehicleType(VehicleType.MOBIL);
        request.setVehicleCondition(VehicleCondition.BARU);
        request.setVehicleYear(2025);
        request.setTotalLoanAmount(BigDecimal.valueOf(1_000_000_000L));
        request.setLoanTenure(6);
        request.setDownPayment(BigDecimal.valueOf(500_000_000L));
        return request;
    }

    private BigDecimal getInterestRate(VehicleType vehicleType, int year) {
        BigDecimal baseRate = vehicleType == VehicleType.MOBIL
            ? new BigDecimal("0.08")
            : new BigDecimal("0.09");

        BigDecimal increase = BigDecimal.ZERO;
        for (int currentYear = 2; currentYear <= year; currentYear++) {
            if (currentYear % 2 == 0) {
                increase = increase.add(new BigDecimal("0.001"));
            } else {
                increase = increase.add(new BigDecimal("0.005"));
            }
        }
        return baseRate.add(increase);
    }
}
