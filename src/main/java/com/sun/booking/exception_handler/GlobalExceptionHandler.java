package com.sun.booking.exception_handler;


import java.util.stream.Collectors;

import org.slf4j.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.sun.booking.common.LogUtil;
import com.sun.booking.common.httpresponse.BaseResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<Object>> handleValidationExceptions(
            Exception ex) {

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String message = ex.getMessage();

        logger.error(LogUtil.formatException(ex));

        if (ex instanceof AccessDeniedException) {
            status = HttpStatus.FORBIDDEN;
        }

        if (ex instanceof MethodArgumentNotValidException) {
            status = HttpStatus.BAD_REQUEST;

            MethodArgumentNotValidException validationEx =
                    (MethodArgumentNotValidException) ex;

            message = validationEx.getBindingResult()
                    .getFieldErrors()
                    .stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.joining(", "));
        }

        if(ex instanceof ResponseStatusException) {
            ResponseStatusException responseStatusEx = (org.springframework.web.server.ResponseStatusException) ex;
            status = HttpStatus.BAD_REQUEST;
            message = responseStatusEx.getReason();
        }

        return new ResponseEntity<>(new BaseResponse<>(null, status.value(), message), status);
    }
}
