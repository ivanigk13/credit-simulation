package com.credit.simulator.view;

import com.credit.simulator.model.SheetHolder;
import com.credit.simulator.service.CreditService;
import com.credit.simulator.service.CreditServiceImpl;
import com.credit.simulator.validator.CreditValidator;

import java.util.Scanner;

public class MainView implements CommonView {

    private final Scanner scanner = new Scanner(System.in);
    private SimulateView simulateView;
    private CreditValidator creditValidator;
    private CreditService creditService;
    private SheetHolder sheetHolder;
    private SaveSheetView saveSheetView;

    public void init(){
        creditValidator = new CreditValidator();
        creditService = new CreditServiceImpl();
        sheetHolder = new SheetHolder();

        simulateView = new SimulateView(scanner, creditValidator, creditService, sheetHolder);
        saveSheetView = new SaveSheetView(scanner, sheetHolder);
    }
    @Override
    public void show(){
        while(true){
            System.out.println("=== CREDIT SIMULATOR ===");
            System.out.println("1. Calculate Credit");
            System.out.println("2. Save Sheet");
            System.out.println("3. Display All Sheet");
            System.out.println("4. Search Sheet");
            System.out.println("5. Load");
            System.out.println("0. Exit");
            int input = inputMain();
            switch(input) {
                case 1:
                    simulateView.show();
                    break;
                case 2:
                    saveSheetView.show();
                    break;
                case 3:
                    this.displayAllSheet();
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 0:
                    System.exit(0);
            }
        }
    }

    private void displayAllSheet() {
        if(sheetHolder.getSheets().isEmpty()){
            System.out.println("Tidak ada sheet yang disimpan");
        }else{
            sheetHolder.getSheets().forEach((sheetName, creditResponse) -> {
                System.out.println("Nama sheet: " + sheetName);
                creditService.displaySimulation(creditResponse);
                System.out.println();
            });
        }
    }

    private int inputMain() {
        while (true) {
            try {
                System.out.print(">> ");
                int input = Integer.parseInt(scanner.nextLine());

                if (input >= 0 && input <= 5) {
                    return input;
                }
                System.out.println("Invalid input.");

            } catch (Exception e) {
                System.out.println("Please enter a number.");
            }
        }
    }
}
