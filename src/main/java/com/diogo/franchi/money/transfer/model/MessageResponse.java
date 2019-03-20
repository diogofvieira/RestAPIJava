package com.diogo.franchi.money.transfer.model;

public class MessageResponse {

    private int httpStatusCode;
    private final String message;

    public MessageResponse(int httpStatusCode, String message) {
        this.httpStatusCode = httpStatusCode;
        this.message = message;
    }
    
    public MessageResponse(String message) {
        this.message = message;
    }

	public int getHttpStatusCode() {
		return httpStatusCode;
	}

	public String getMessage() {
		return message;
	}
    
    

}
