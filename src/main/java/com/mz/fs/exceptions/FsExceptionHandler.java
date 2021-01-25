package com.mz.fs.exceptions;

import com.mz.fs.dto.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class FsExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = FsException.class)
    protected ResponseEntity<ErrorResponse> handleException(FsException exception, WebRequest req) {
        return ResponseEntity.status(exception.getStatus().value()).body(new ErrorResponse(
                exception.getCorrelationId(),
                exception.getStatus().getReasonPhrase(),
                exception.getMessage(),
                exception.getTimeStamp()
        ));
    }
}
