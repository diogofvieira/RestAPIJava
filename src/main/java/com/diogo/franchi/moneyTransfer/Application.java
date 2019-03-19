package com.diogo.franchi.moneyTransfer;

import com.diogo.franchi.moneyTransfer.account.AccountController;
import com.diogo.franchi.moneyTransfer.account.AccountDAO;
import com.diogo.franchi.moneyTransfer.account.AccountService;
import com.diogo.franchi.moneyTransfer.dao.EmbeddedDatabase;
import com.diogo.franchi.moneyTransfer.model.Error;
import com.diogo.franchi.moneyTransfer.transfer.TransferController;
import com.diogo.franchi.moneyTransfer.transfer.TransferService;
import com.google.gson.Gson;

import static spark.Spark.*;

public class Application {

    private static final String APPLICATION_JSON = "application/json";
    private static Gson gson = new Gson();
    private static AccountController accountController;
    private static TransferController transferController;
    
    

    public static void main(String[] args) {
        accountController = new AccountController(new AccountService(new AccountDAO()));
        transferController = new TransferController(new TransferService(new AccountDAO()));
        bootstrapRoutes();
        EmbeddedDatabase.openServerDataBase();
        EmbeddedDatabase.createTable();
                
    }

    private static void bootstrapRoutes() {
        before((req, res) -> res.type(APPLICATION_JSON));

        post("/account",
                APPLICATION_JSON,
                accountController::newAccount,
                gson::toJson);

        get("/accounts",
                APPLICATION_JSON,
                accountController::allAccounts,
                gson::toJson);

        post("/transfer",
                APPLICATION_JSON,
                transferController::doTransfer,
                gson::toJson);

        post("/accounts/delete",
                APPLICATION_JSON,
                accountController::deleteAll,
                gson::toJson);

        notFound(gson.toJson(new Error(404, "Path Not Found")));
        internalServerError(gson.toJson(new Error(500, "Default Internal Server Error")));
    }
    
    
    
    


}
