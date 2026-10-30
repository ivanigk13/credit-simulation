package com.credit.simulator.view;

import com.credit.simulator.model.SheetHolder;
import com.credit.simulator.utils.StringUtils;

import java.util.Scanner;

public class SaveSheetView implements CommonView{

    private final SheetHolder sheetHolder;
    private final Scanner scanner;

    public SaveSheetView(Scanner scanner, SheetHolder sheetHolder) {
        this.scanner = scanner;
        this.sheetHolder = sheetHolder;
    }

    @Override
    public void show(){
        if(sheetHolder.getCreditResponse() == null){
            System.out.println("Tidak ada simulasi credit");
        }else{
            String sheetName = inputSheetName(sheetHolder);
            sheetHolder.addSheet(sheetName);
            sheetHolder.setCreditResponse(null);
        }
    }

    private String inputSheetName(SheetHolder sheetHolder) {
        while (true) {
            System.out.print("Sheet name: ");
            String sheetName = scanner.nextLine().trim();
            if (StringUtils.isBlank(sheetName)) {
                System.out.println("Nama sheet tidak boleh kosong");
                continue;
            }

            if(sheetHolder.getSheets().containsKey(sheetName)){
                System.out.println("Nama sheet '" + sheetName + "' sudah digunakan");
                continue;
            }
            return sheetName;
        }
    }
}
