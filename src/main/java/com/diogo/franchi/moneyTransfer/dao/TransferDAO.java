package com.diogo.franchi.moneyTransfer.dao;

import com.diogo.franchi.moneyTransfer.model.Account;
import com.diogo.franchi.moneyTransfer.model.Error;
import com.diogo.franchi.moneyTransfer.model.Transfer;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;

import java.math.BigDecimal;

public class TransferDAO {

   public static Object doTransfer(Request request, Response response) {
       Transfer data = new Gson().fromJson(request.body(), Transfer.class);

       if (data == null || data.getAccountDebit() == null || data.getAccountCredit() == null) {
           response.status(400);
           return new Error(400,"Bad Request");
       }

       Account debit = AccountDAO.getAccount(data.getAccountDebit());
       Account credit = AccountDAO.getAccount(data.getAccountCredit());
       if(debit == null || credit == null){
           response.status(400);
           return new Error(400,"Bad Request");
       }

       BigDecimal debitAmount = debit.getAmount();
       BigDecimal creditAmount = credit.getAmount();

       //business rule
       debit.setAmount(debitAmount.subtract(data.getValue()));
       credit.setAmount(creditAmount.add(data.getValue()));

       try {
           AccountDAO.update(debit);
           AccountDAO.update(credit);
           return AccountDAO.getAccount(debit.getAccountNumber());
       } catch(Exception ex) {
           //Rollback
           response.status(500);
           return new Error(500,"Internal Server Error");
       }
   }
}
