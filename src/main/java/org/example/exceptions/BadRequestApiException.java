package org.example.exceptions;

import javax.ws.rs.core.Response;

public class BadRequestApiException extends ApiException {
    public BadRequestApiException(String message) {
        super(message, Response.Status.BAD_REQUEST);
    }
}
