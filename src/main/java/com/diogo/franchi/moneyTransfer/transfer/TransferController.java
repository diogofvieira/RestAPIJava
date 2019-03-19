package com.diogo.franchi.moneyTransfer.transfer;

import com.diogo.franchi.moneyTransfer.model.Account;
import com.diogo.franchi.moneyTransfer.model.Error;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;

import static spark.utils.Assert.notNull;

public class TransferController {
		
		private final TransferService transferService;
		
		public TransferController(TransferService transferService) {
	        notNull(transferService);
	        this.transferService = transferService;
	    }

	    public Object doTransfer(Request req, Response res) {
	        TransferRequest transferRequest = new Gson().fromJson(req.body(), TransferRequest.class);
	        try {
	        	Account account = transferService.update(transferRequest);
	        	res.status(200);
	        	return account;
	        } catch (IllegalArgumentException ex) { 
	            res.status(400);
	            return new Error(400, "Bad Request");
	        } catch (Exception ex) {
	            res.status(500);
	            return new Error(500, "Internal Server Error");
	        }
	    }
}
