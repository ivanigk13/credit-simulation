package com.credit.simulator.service;

import com.credit.simulator.model.CreditRequest;
import com.credit.simulator.model.CreditResponse;

public interface CreditService {
    CreditResponse calculate(CreditRequest creditRequest);
    void displaySimulation(CreditResponse  creditResponse);
    CreditRequest loadFromAPI();
}
