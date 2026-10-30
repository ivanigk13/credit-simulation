package com.credit.simulator.view;

import com.credit.simulator.constants.CommonConstant;
import com.credit.simulator.enums.VehicleCondition;
import com.credit.simulator.enums.VehicleType;
import com.credit.simulator.model.CreditRequest;
import com.credit.simulator.model.CreditResponse;
import com.credit.simulator.model.SheetHolder;
import com.credit.simulator.service.CreditService;
import com.credit.simulator.validator.CreditValidator;

import java.math.BigDecimal;
import java.time.Year;
import java.util.Scanner;

public class SimulateView implements CommonView{

    private final Scanner scanner;
    private final CreditValidator creditValidator;
    private final CreditService creditService;
    private final SheetHolder sheetHolder;


    public SimulateView(Scanner scanner, CreditValidator creditValidator, CreditService creditService, SheetHolder sheetHolder) {
        this.scanner = scanner;
        this.creditValidator = creditValidator;
        this.creditService = creditService;
        this.sheetHolder = sheetHolder;
    }

    @Override
    public void show() {
        System.out.println();
        System.out.println("=== CREDIT SIMULATION ===");

        VehicleType vehicleType = inputVehicleType();
        VehicleCondition vehicleCondition = inputVehicleCondition();
        int vehicleYear = inputVehicleYear(vehicleCondition);
        BigDecimal totalLoan = inputTotalLoanAmount();
        int loanTenure = inputLoanTenure();
        BigDecimal downPayment = inputDownPayment();

        CreditRequest request = new CreditRequest(
                vehicleType,
                vehicleCondition,
                vehicleYear,
                totalLoan,
                loanTenure,
                downPayment
        );
        try {
            creditValidator.validate(request);
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
            return;
        }

        CreditResponse creditResponse = creditService.calculate(request);
        creditService.displaySimulation(creditResponse);
        sheetHolder.setCreditResponse(creditResponse);
    }

    private VehicleType inputVehicleType() {
        while (true) {
            try {
                System.out.print("Input Jenis Kendaraan (Mobil/Motor)(insensitive case): ");
                return VehicleType.from(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Tolong input Mobil atau Motor");
            }
        }
    }

    private VehicleCondition inputVehicleCondition() {
        while (true) {
            try {
                System.out.print("Input Keadaan Kendaraan (Baru/Bekas)(insensitive case): ");
                return VehicleCondition.from(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Tolong input Baru atau Bekas");
            }
        }
    }

    private int inputVehicleYear(VehicleCondition vehicleCondition) {
        while (true) {
            try {
                System.out.print("Input Tahun Kendaraan: ");
                int year = Integer.parseInt(scanner.nextLine());
                if (year < 1990 || year > Year.now().getValue()) {
                    System.out.println("Tahun kendaraan baru minimal 1990 sampai "+  Year.now().getValue());
                }
                return year;
            } catch (Exception e) {
                System.out.println(CommonConstant.INVALID_NUMERIC_FORMAT);
            }
        }
    }

    private BigDecimal inputTotalLoanAmount() {
        while (true) {
            try {
                System.out.print("Input Total Pinjaman Total: ");
                BigDecimal total = new BigDecimal(scanner.nextLine());
                if(total.compareTo(BigDecimal.ZERO) <= 0 || total.compareTo(new BigDecimal(1_000_000_000)) > 0){
                    System.out.println("Total pinjaman tidak dapat kurang dari 0 atau lebih dari 1 Milliar");
                }
                return total;
            } catch (Exception e) {
                System.out.println(CommonConstant.INVALID_NUMERIC_FORMAT);
            }
        }
    }

    private int inputLoanTenure() {
        while (true) {
            try {
                System.out.print("Input Tenor Pinjaman (1-6): ");
                int tenure = Integer.parseInt(scanner.nextLine());
                if (tenure < 1 || tenure > 6) {
                    System.out.println("Tenor harus antara 1 dan 6");
                    continue;
                }
                return tenure;
            } catch (Exception e) {
                System.out.println(CommonConstant.INVALID_NUMERIC_FORMAT);
            }
        }
    }

    private BigDecimal inputDownPayment() {
        while (true) {
            try {
                System.out.print("Jumlah DP: ");
                return new BigDecimal(scanner.nextLine());
            } catch (Exception e) {
                System.out.println(CommonConstant.INVALID_NUMERIC_FORMAT);
            }
        }
    }
}
