package com.credit.simulator;

import com.credit.simulator.model.CreditRequest;
import com.credit.simulator.model.CreditResponse;
import com.credit.simulator.service.CreditService;
import com.credit.simulator.service.CreditServiceImpl;
import com.credit.simulator.service.FileService;
import com.credit.simulator.service.FileTxtService;
import com.credit.simulator.validator.CreditValidator;
import com.credit.simulator.view.MainView;

public class Main {
    public static void main(String[] args) {
        MainView mainView = new MainView();
        mainView.init();
        if(args.length > 0){
            FileService fileService = new FileTxtService();
            CreditService creditService = new CreditServiceImpl();
            CreditValidator creditValidator = new CreditValidator();

            try {
                CreditRequest creditRequest = fileService.readFile(args[0]);
                creditValidator.validate(creditRequest);
                CreditResponse creditResponse = creditService.calculate(creditRequest);
                creditService.displaySimulation(creditResponse);
            }catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
            }catch (Exception e){
                System.out.println("System Error: "+ e.getMessage());
            }
        }else {
            mainView.show();
        }
    }
}