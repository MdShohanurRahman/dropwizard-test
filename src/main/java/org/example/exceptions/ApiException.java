package org.example.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class ApiException extends WebApplicationException {
    ApiException(String message, Response.Status status) {
        super(
                Response
                        .status(status)
                        .entity(new ErrorMessage(message, status)).build()
        );
    }

    public static class ErrorMessage {
        private String message;
        private int statusCode;

        public ErrorMessage(String message, Response.Status status) {
            this.message = message;
            this.statusCode = status.getStatusCode();
        }

        public String getMessage() {
            return message;
        }

        public int getStatusCode() {
            return statusCode;
        }
    }

}
