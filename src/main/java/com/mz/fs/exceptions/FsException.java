package com.mz.fs.exceptions;


import org.springframework.http.HttpStatus;

public class FsException extends RuntimeException {

    private String correlationId;
    private HttpStatus status;
    private String message;
    private String timeStamp;

    public FsException(String correlationId, HttpStatus status, String message, String timeStamp) {
        this.correlationId = correlationId;
        this.status = status;
        this.message = message;
        this.timeStamp = timeStamp;
    }

    public FsException(String message, String correlationId, HttpStatus status, String message1, String timeStamp) {
        super(message);
        this.correlationId = correlationId;
        this.status = status;
        this.message = message1;
        this.timeStamp = timeStamp;
    }

    public FsException(String message, Throwable cause, String correlationId, HttpStatus status, String message1, String timeStamp) {
        super(message, cause);
        this.correlationId = correlationId;
        this.status = status;
        this.message = message1;
        this.timeStamp = timeStamp;
    }

    public FsException(Throwable cause, String correlationId, HttpStatus status, String message, String timeStamp) {
        super(cause);
        this.correlationId = correlationId;
        this.status = status;
        this.message = message;
        this.timeStamp = timeStamp;
    }

    public FsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String correlationId, HttpStatus status, String message1, String timeStamp) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.correlationId = correlationId;
        this.status = status;
        this.message = message1;
        this.timeStamp = timeStamp;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
