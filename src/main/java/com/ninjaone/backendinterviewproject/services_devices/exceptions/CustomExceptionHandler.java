package com.ninjaone.backendinterviewproject.services_devices.exceptions;

import com.ninjaone.backendinterviewproject.services_devices.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.ResourceBundle;

@Slf4j
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    private static ResourceBundle messages = ResourceBundle.getBundle("messages");

    @ExceptionHandler(DevicesServicesException.class)
    public final ResponseEntity<ErrorResponse> handleDevicesServicesException(
            final Exception  exception,
            final WebRequest request) {
        final  DevicesServicesException except = ( DevicesServicesException) exception;
        final ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                messages.getString(except.getMessage()),
                except.getErrors());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorResponse> handleAnyException(final Exception except,
                                                                  final WebRequest request) {
        log.error(except.getStackTrace().toString());
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                messages.getString("error.general"),
                new ArrayList<>()), HttpStatus.resolve(HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }
}
