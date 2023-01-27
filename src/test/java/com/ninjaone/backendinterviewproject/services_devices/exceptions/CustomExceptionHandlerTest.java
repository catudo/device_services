package com.ninjaone.backendinterviewproject.services_devices.exceptions;

import com.ninjaone.backendinterviewproject.services_devices.response.ErrorResponse;
import org.apache.commons.logging.Log;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CustomExceptionHandlerTest {
    private CustomExceptionHandler exceptionHandler = new CustomExceptionHandler();

    @Test
    public void handleDevicesServicesException_shouldReturnBadRequest() {
        DevicesServicesException exception = new DevicesServicesException("devices.wrong.type");
        ResponseEntity<ErrorResponse> response = exceptionHandler.handleDevicesServicesException(exception, null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("incorrect device type", response.getBody().getMessage());
    }

    @Test
    public void handleAnyException_shouldReturnInternalServerError() {
        Exception exception = new Exception("Some error");
        ResponseEntity<ErrorResponse> response = exceptionHandler.handleAnyException(exception, null);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Internal server error", response.getBody().getMessage());
    }
}
