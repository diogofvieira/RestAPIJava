package com.diogo.franchi.moneyTransfer;

import com.diogo.franchi.moneyTransfer.dao.AccountDAO;
import com.diogo.franchi.moneyTransfer.dao.TransferDAO;
import com.diogo.franchi.moneyTransfer.model.Error;
import com.google.gson.Gson;

import static spark.Spark.*;


public class Start {

   private static final String APPLICATION_JSON = "application/json";

   public static void main(String[] args) {
       routes();
   }

    public static void routes() {
        before((req, res) -> res.type(APPLICATION_JSON));
        Gson gson = new Gson();

        post("/account",
                APPLICATION_JSON,
                AccountDAO::newAccount,
                gson::toJson);
        get ("/accounts",
                APPLICATION_JSON,
                AccountDAO::allAccounts,
                gson::toJson);
        post("/transfer",
                 APPLICATION_JSON,
                 TransferDAO::doTransfer,
                 gson::toJson);
        post ("/accounts/delete",
                APPLICATION_JSON,
                AccountDAO::deleteAll,
                gson::toJson);

        notFound(gson.toJson(new Error(404, "Path Not Found")));
        internalServerError(gson.toJson(new Error(500, "Default Internal Server Error")));
    }

}
