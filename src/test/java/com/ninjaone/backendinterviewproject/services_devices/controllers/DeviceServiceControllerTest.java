package com.ninjaone.backendinterviewproject.services_devices.controllers;

import com.ninjaone.backendinterviewproject.services_devices.dto.DeviceServiceDTO;
import com.ninjaone.backendinterviewproject.services_devices.models.Device;
import com.ninjaone.backendinterviewproject.services_devices.models.DevicesService;
import com.ninjaone.backendinterviewproject.services_devices.models.ServiceBusiness;
import com.ninjaone.backendinterviewproject.services_devices.response.TotalResponse;
import com.ninjaone.backendinterviewproject.services_devices.response.UnitPriceResponse;
import com.ninjaone.backendinterviewproject.services_devices.services.DeviceServiceBusinessService;
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
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class DeviceServiceControllerTest {
    @Mock
    private DeviceServiceBusinessService deviceServiceBusinessService;
    @InjectMocks
    private DeviceServiceController deviceServiceController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSave() {
        DeviceServiceDTO deviceServiceDTO = new DeviceServiceDTO();
        DevicesService expected = new DevicesService();
        when(deviceServiceBusinessService.associateDeviceService(any(DeviceServiceDTO.class))).thenReturn(expected);

        ResponseEntity<DevicesService> actual = deviceServiceController.save(deviceServiceDTO);

        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertEquals(expected, actual.getBody());
    }

    @Test
    public void testDelete() {
        DeviceServiceDTO deviceServiceDTO = new DeviceServiceDTO();
        doNothing().when(deviceServiceBusinessService).deleteDeviceServiceAssociation(any(DeviceServiceDTO.class));

        ResponseEntity actual = deviceServiceController.delete(deviceServiceDTO);

        assertEquals(HttpStatus.OK, actual.getStatusCode());
    }

    @Test
    public void testGetPrices() {
        DeviceServiceDTO[] deviceServiceDTO = new DeviceServiceDTO[1];
        TotalResponse expected = new TotalResponse();
        when(deviceServiceBusinessService.getPrices(any(DeviceServiceDTO[].class))).thenReturn(expected);

        ResponseEntity<TotalResponse> actual = deviceServiceController.getPrices(deviceServiceDTO);

        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertEquals(expected, actual.getBody());
    }

}

