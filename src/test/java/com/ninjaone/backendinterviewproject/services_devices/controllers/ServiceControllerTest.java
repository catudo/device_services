package com.ninjaone.backendinterviewproject.services_devices.controllers;

import com.ninjaone.backendinterviewproject.services_devices.dto.ServiceDTO;

import com.ninjaone.backendinterviewproject.services_devices.models.ServiceBusiness;
import com.ninjaone.backendinterviewproject.services_devices.services.ServiceBusinessService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ServiceControllerTest {
    @Mock
    private ServiceBusinessService serviceBusinessService;


    @InjectMocks
    private ServiceController serviceController;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void saveShouldSaveDevice() {
        ServiceDTO serviceDTO = new ServiceDTO();
        ServiceBusiness serviceBusiness = new ServiceBusiness();
        when(serviceBusinessService.saveService(serviceDTO)).thenReturn(serviceBusiness);

        ResponseEntity<ServiceBusiness> response = serviceController.save(serviceDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(serviceBusiness, response.getBody());
        verify(serviceBusinessService, times(1)).saveService(serviceDTO);
    }

    @Test
    public void delete_shouldDeleteDevice() {
        Long id = 1L;
        serviceController.delete(id);

        verify(serviceBusinessService, times(1)).deleteService(id);
    }
}

