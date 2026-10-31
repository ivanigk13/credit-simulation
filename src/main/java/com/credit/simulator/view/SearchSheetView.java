package com.credit.simulator.view;

import com.credit.simulator.model.CreditResponse;
import com.credit.simulator.model.SheetHolder;
import com.credit.simulator.service.CreditService;
import com.credit.simulator.utils.StringUtils;

import java.util.Scanner;

public class SearchSheetView implements CommonView{

    private final Scanner scanner;
    private final SheetHolder sheetHolder;
    private final CreditService creditService;

    public SearchSheetView(Scanner scanner, SheetHolder sheetHolder, CreditService creditService) {
        this.scanner = scanner;
        this.sheetHolder = sheetHolder;
        this.creditService = creditService;
    }

    @Override
    public void     show() {
        String sheetName = inputSheetName();
        CreditResponse creditResponse = sheetHolder.getSheets().get(sheetName);
        if(creditResponse == null){
            System.out.println("Sheet tidak ditemukan");
        }else{
            creditService.displaySimulation(creditResponse);
        }
    }

    private String inputSheetName() {
        while (true) {
            System.out.print("Sheet name: ");
            String sheetName = scanner.nextLine().trim();
            if (StringUtils.isBlank(sheetName)) {
                System.out.println("Nama sheet tidak boleh kosong");
                continue;
            }
            return sheetName;
        }
    }
}
