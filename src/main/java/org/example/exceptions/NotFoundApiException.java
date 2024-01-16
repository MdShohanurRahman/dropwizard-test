package org.example.exceptions;

import javax.ws.rs.core.Response;

public class NotFoundApiException extends ApiException {
    public NotFoundApiException(String message) {
        super(message, Response.Status.NOT_FOUND);
    }
}
