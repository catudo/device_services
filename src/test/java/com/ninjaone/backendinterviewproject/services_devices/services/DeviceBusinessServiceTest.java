package com.ninjaone.backendinterviewproject.services_devices.services;

import com.ninjaone.backendinterviewproject.services_devices.converter.DeviceMapper;
import com.ninjaone.backendinterviewproject.services_devices.dto.DeviceDTO;
import com.ninjaone.backendinterviewproject.services_devices.enums.DeviceType;
import com.ninjaone.backendinterviewproject.services_devices.exceptions.DevicesServicesException;
import com.ninjaone.backendinterviewproject.services_devices.models.Device;
import com.ninjaone.backendinterviewproject.services_devices.models.DevicesService;
import com.ninjaone.backendinterviewproject.services_devices.repositories.DeviceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class DeviceBusinessServiceTest {
    @InjectMocks
    private DeviceBusinessService deviceBusinessService;

    @Mock
    private DeviceRepository deviceRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSaveDeviceValid() {
        DeviceDTO deviceDTO = new DeviceDTO("systemName", DeviceType.MAC.name());
        Device device = DeviceMapper.INSTANCE.convert(deviceDTO);
        when(deviceRepository.findDeviceBySystemNameAndType(any(), any())).thenReturn(Optional.empty());
        when(deviceRepository.save(any())).thenReturn(device);
        assertEquals(device, deviceBusinessService.saveDevice(deviceDTO));
    }

    @Test
    public void testSaveDeviceInvalidDeviceType() {
        DeviceDTO deviceDTO = new DeviceDTO("systemName", "InvalidType");
        assertThrows(DevicesServicesException.class, () -> deviceBusinessService.saveDevice(deviceDTO));
    }

    @Test
    public void testSaveDeviceAlreadyExist() {
        DeviceDTO deviceDTO = new DeviceDTO("systemName", DeviceType.MAC.name());
        when(deviceRepository.findDeviceBySystemNameAndType(any(), any())).thenReturn(Optional.of(new Device()));
        assertThrows(DevicesServicesException.class, () -> deviceBusinessService.saveDevice(deviceDTO));
    }

    @Test
    public void testDeleteDeviceValid() {
        Device device = new Device();
        when(deviceRepository.findById(any())).thenReturn(Optional.of(device));
        deviceBusinessService.deleteDevice(1L);
        verify(deviceRepository).delete(device);
    }

    @Test
    public void testDeleteDeviceNotExist() {
        when(deviceRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(DevicesServicesException.class, () -> deviceBusinessService.deleteDevice(1L));
    }

    @Test
    public void testDeleteDeviceAssociated() {
        Optional<Device> optionalDevice = Optional.of(Device.builder()
                .devicesServices(Set.of(new DevicesService())).build());

        when(deviceRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(DevicesServicesException.class, () -> deviceBusinessService.deleteDevice(1L));
    }
}

