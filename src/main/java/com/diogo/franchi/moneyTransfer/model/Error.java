package com.diogo.franchi.moneyTransfer.model;

public class Error {

    private final int httpStatusCode;
    private final String message;

    public Error(int httpStatusCode, String message) {
        this.httpStatusCode = httpStatusCode;
        this.message = message;
    }

}
