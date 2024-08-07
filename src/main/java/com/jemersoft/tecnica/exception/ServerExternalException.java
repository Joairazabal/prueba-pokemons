package com.jemersoft.tecnica.exception;

import lombok.Data;

@Data
public class ServerExternalException extends RuntimeException {
    private int httpCode;
    private String code;

    public ServerExternalException(String code, int statusCode, String message) {
        super(message);
        this.httpCode = statusCode;
        this.code = code;
    }

}
