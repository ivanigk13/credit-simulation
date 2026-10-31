package com.credit.simulator.view;

import com.credit.simulator.model.CreditRequest;
import com.credit.simulator.model.CreditResponse;
import com.credit.simulator.model.SheetHolder;
import com.credit.simulator.service.CreditService;

public class LoadView implements CommonView{

    private final CreditService creditService;
    private final SheetHolder sheetHolder;

    public LoadView(CreditService creditService, SheetHolder sheetHolder) {
        this.creditService = creditService;
        this.sheetHolder = sheetHolder;
    }

    @Override
    public void show() {
        CreditRequest creditRequest = creditService.loadFromAPI();
        CreditResponse creditResponse = creditService.calculate(creditRequest);
        creditService.displaySimulation(creditResponse);
        sheetHolder.setCreditResponse(creditResponse);
    }
}
