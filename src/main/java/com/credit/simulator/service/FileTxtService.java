package com.credit.simulator.service;

import com.credit.simulator.enums.VehicleCondition;
import com.credit.simulator.enums.VehicleType;
import com.credit.simulator.model.CreditRequest;
import com.credit.simulator.utils.StringUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class FileTxtService implements FileService {

    @Override
    public CreditRequest readFile(String filePath) {
        Map<String, String> values = contructRequestFromFile(filePath);

        CreditRequest creditRequest = new CreditRequest();
        creditRequest.setVehicleType(VehicleType.from(get(values, "vehicleType")));
        creditRequest.setVehicleCondition(VehicleCondition.from(get(values, "vehicleCondition")));
        creditRequest.setVehicleYear(Integer.parseInt(get(values, "vehicleYear")));
        creditRequest.setTotalLoanAmount(new BigDecimal(get(values, "totalLoanAmount")));
        creditRequest.setLoanTenure(Integer.parseInt(get(values, "loanTenure")));
        creditRequest.setDownPayment(new BigDecimal(get(values, "downPayment")));

        return creditRequest;
    }

    private Map<String, String> contructRequestFromFile(String filePath) {
        Map<String, String> values = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                String[] split = line.split("=", 2);
                if (split.length != 2) continue;
                values.put(split[0].trim(), split[1].trim());
            }
        } catch (IOException e) {
            throw new RuntimeException("Gagal membaca file input", e);
        }
        return values;
    }

    private String get(Map<String, String> values, String key) {
        String value = values.get(key);
        if (StringUtils.isBlank(value)) {
            throw new IllegalArgumentException("Property '" + key + "' tidak ditemukan");
        }
        return value;
    }
}
