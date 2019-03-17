package com.diogo.franchi.moneyTransfer.dao;

import com.diogo.franchi.moneyTransfer.model.Error;
import com.diogo.franchi.moneyTransfer.model.Account;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.List;

public class AccountDAO {

    private static final List<Account> MEMORY_DATA_BASE = new ArrayList<>();

    public static Object newAccount(Request request, Response response) {
        Account data = new Gson().fromJson(request.body(), Account.class);
        if (data == null || data.getAmount() == null) {
            response.status(400);
            return new Error(400,"Bad Request");
        }
        Account account = Account.newAccount(data);

        try {
            save(account);
            response.status(201);
            return account;
        } catch (Exception ex){
            response.status(500);
            return new Error(500,"Internal Server Error");
        }
    }

    private static void save(Account account) {
        MEMORY_DATA_BASE.add(account);
    }

    public static List<Account> allAccounts(Request request, Response response) {
        if(MEMORY_DATA_BASE.size() > 0) {
            response.status(200);
            return MEMORY_DATA_BASE;
        }else{
            response.status(404);
            return MEMORY_DATA_BASE;
        }
    }

    static Account getAccount(String accountNumber) {
        for (Account account: MEMORY_DATA_BASE) {
            if (account.getAccountNumber().equalsIgnoreCase(accountNumber)) {
                return account;
            }
        }
        return null;
    }

    static void update(Account account) {
        for (Account updatedAccount : MEMORY_DATA_BASE) {
            if (updatedAccount.getAccountNumber().equalsIgnoreCase(account.getAccountNumber())) {
                updatedAccount.setAmount(account.getAmount());
            }
        }
    }

    public static Object deleteAll(Request request, Response response) {
        try {
            MEMORY_DATA_BASE.clear();
            return MEMORY_DATA_BASE;
        }catch (Exception ex){
            response.status(500);
            return new Error(500,"Internal Server Error");
        }
    }
}
