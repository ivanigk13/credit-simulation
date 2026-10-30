package com.credit.simulator.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class SheetHolder {

    private CreditResponse creditResponse;
    private Map<String, CreditResponse> sheets = new LinkedHashMap<>();

    public CreditResponse getCreditResponse() {
        return creditResponse;
    }

    public void setCreditResponse(CreditResponse creditResponse) {
        this.creditResponse = creditResponse;
    }

    public Map<String, CreditResponse> getSheets() {
        return sheets;
    }

    public void setSheets(Map<String, CreditResponse> sheets) {
        this.sheets = sheets;
    }

    public void addSheet(String sheetName) {
        sheets.put(sheetName, creditResponse);
    }
}
