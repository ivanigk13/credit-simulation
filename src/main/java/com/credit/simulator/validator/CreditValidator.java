package com.credit.simulator.validator;

import com.credit.simulator.enums.VehicleCondition;
import com.credit.simulator.enums.VehicleType;
import com.credit.simulator.model.CreditRequest;

import java.math.BigDecimal;
import java.time.Year;

public class CreditValidator{

    public void validate(CreditRequest request) {

        VehicleType.from(request.getVehicleType().toString());
        VehicleCondition.from(request.getVehicleCondition().toString());

        if (request.getTotalLoanAmount().compareTo(BigDecimal.ZERO) <= 0 || request.getTotalLoanAmount().compareTo(BigDecimal.valueOf(1_000_000_000)) > 0) {
            throw new IllegalArgumentException("Total pinjaman tidak dapat kurang dari 0 atau lebih dari 1 Milliar");
        }

        if (request.getLoanTenure() < 1 || request.getLoanTenure() > 6) {
            throw new IllegalArgumentException("Tenor harus antara 1 dan 6");
        }

        if (request.getVehicleCondition() == VehicleCondition.BARU) {
            int minimumYear = Year.now().getValue() - 1;
            if (request.getVehicleYear() < minimumYear) {
                throw new IllegalArgumentException("Tahun kendaraan baru minimal " + minimumYear);
            }
        }

        BigDecimal minimumDpPercentage = request.getVehicleCondition() == VehicleCondition.BARU ? BigDecimal.valueOf(35) : BigDecimal.valueOf(25);

        BigDecimal minimumDp = request.getTotalLoanAmount()
            .multiply(minimumDpPercentage)
            .divide(BigDecimal.valueOf(100));

        if (request.getDownPayment().compareTo(minimumDp) < 0) {
            throw new IllegalArgumentException("DP minimal Rp. " + minimumDp.toPlainString());
        }
    }
}
