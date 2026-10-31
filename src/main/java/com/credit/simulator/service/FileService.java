package com.credit.simulator.service;

import com.credit.simulator.model.CreditRequest;

public interface FileService {
    CreditRequest readFile(String filePath);
}
