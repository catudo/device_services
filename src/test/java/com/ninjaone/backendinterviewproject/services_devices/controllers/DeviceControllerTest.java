package com.ninjaone.backendinterviewproject.services_devices.controllers;

import com.ninjaone.backendinterviewproject.services_devices.dto.DeviceDTO;
import com.ninjaone.backendinterviewproject.services_devices.models.Device;
import com.ninjaone.backendinterviewproject.services_devices.models.DevicesService;
import com.ninjaone.backendinterviewproject.services_devices.models.ServiceBusiness;
import com.ninjaone.backendinterviewproject.services_devices.services.DeviceBusinessService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class DeviceControllerTest {
    @Mock
    private DeviceBusinessService deviceBusinessService;
    @InjectMocks
    private DeviceController deviceController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void saveShouldSaveDevice() {
        DeviceDTO deviceDTO = new DeviceDTO("","");
        Device device = new Device();
        when(deviceBusinessService.saveDevice(deviceDTO)).thenReturn(device);

        ResponseEntity<Device> response = deviceController.save(deviceDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(device, response.getBody());
        verify(deviceBusinessService, times(1)).saveDevice(deviceDTO);
    }

    @Test
    public void delete_shouldDeleteDevice() {
        Long id = 1L;
        deviceController.delete(id);

        verify(deviceBusinessService, times(1)).deleteDevice(id);
    }
}

