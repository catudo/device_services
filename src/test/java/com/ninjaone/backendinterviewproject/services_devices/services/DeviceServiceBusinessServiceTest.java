package com.ninjaone.backendinterviewproject.services_devices.services;

import com.ninjaone.backendinterviewproject.services_devices.cache.RudimentaryCache;
import com.ninjaone.backendinterviewproject.services_devices.cache.RudimentaryStrategyCacheFactory;
import com.ninjaone.backendinterviewproject.services_devices.dto.DeviceServiceDTO;
import com.ninjaone.backendinterviewproject.services_devices.enums.CacheType;
import com.ninjaone.backendinterviewproject.services_devices.exceptions.DevicesServicesException;
import com.ninjaone.backendinterviewproject.services_devices.models.Device;
import com.ninjaone.backendinterviewproject.services_devices.models.DevicesService;
import com.ninjaone.backendinterviewproject.services_devices.models.ServiceBusiness;
import com.ninjaone.backendinterviewproject.services_devices.repositories.DeviceRepository;
import com.ninjaone.backendinterviewproject.services_devices.repositories.DevicesServiceRepository;
import com.ninjaone.backendinterviewproject.services_devices.repositories.ServiceRepository;
import com.ninjaone.backendinterviewproject.services_devices.response.TotalResponse;
import com.ninjaone.backendinterviewproject.services_devices.response.UnitPriceResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class DeviceServiceBusinessServiceTest {
    @Mock
    private DeviceRepository deviceRepository;
    @Mock
    private ServiceRepository serviceRepository;
    @Mock
    private DevicesServiceRepository devicesServiceRepository;
    @Mock
    private RudimentaryStrategyCacheFactory rudimentaryStrategyCacheFactory;
    @Mock
    private RudimentaryCache rudimentaryCache;
    @Mock
    private ResourceBundle messages;
    @Mock
    private Logger log;

    DeviceServiceBusinessService deviceServiceBusinessService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        when(rudimentaryStrategyCacheFactory.getCacheType(CacheType.DEVICE_SERVICE)).thenReturn(rudimentaryCache);
        deviceServiceBusinessService = new DeviceServiceBusinessService(deviceRepository, serviceRepository,
                devicesServiceRepository,
                rudimentaryStrategyCacheFactory);
    }

    @Test
    void associateDeviceServiceValidInputReturnsDevicesService() {
        // given
        DeviceServiceDTO deviceServiceDTO = new DeviceServiceDTO(1L, 2L, 10.0, 3);
        Device device = new Device();
        ServiceBusiness service = new ServiceBusiness();
        DevicesService expected = new DevicesService(1L, device, service, 10.0, Instant.now());
        when(deviceRepository.findById(1L)).thenReturn(Optional.of(device));
        when(serviceRepository.findById(2L)).thenReturn(Optional.of(service));
        when(devicesServiceRepository.findDevicesServiceByDeviceAndServiceBusiness(device, service)).thenReturn(Optional.empty());
        when(devicesServiceRepository.save(expected)).thenReturn(expected);

        // when
        deviceServiceBusinessService.associateDeviceService(deviceServiceDTO);
        // then

        verify(rudimentaryCache, atLeast(1)).save(any());
    }


    @Test
    public void associateDeviceServiceEmptyPrice() {
       assertThrows(DevicesServicesException.class, () -> deviceServiceBusinessService.associateDeviceService(new DeviceServiceDTO()));
    }

    @Test
    public void associateDeviceServiceEmptyDevice() {
        when(deviceRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(DevicesServicesException.class, () -> deviceServiceBusinessService.associateDeviceService(DeviceServiceDTO.builder().price(1D).build()));
    }

    @Test
    public void associateDeviceServiceAlreadyExistDeviceService() {
        Device device = new Device();
        ServiceBusiness service = new ServiceBusiness();
        when(deviceRepository.findById(any())).thenReturn(Optional.of(device));
        when(serviceRepository.findById(any())).thenReturn(Optional.of(service));
        when(serviceRepository.findById(any())).thenReturn(Optional.of(service));
        when(devicesServiceRepository.findDevicesServiceByDeviceAndServiceBusiness(any(), any())).thenReturn(Optional.of(DevicesService.builder().build()));
        assertThrows(DevicesServicesException.class, () -> deviceServiceBusinessService.associateDeviceService(DeviceServiceDTO.builder().price(1D).build()));
    }

    @Test
    public void associateDeviceServiceEmptyService() {
        Device device = new Device();
        when(deviceRepository.findById(any())).thenReturn(Optional.of(device));
        when(serviceRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(DevicesServicesException.class, () -> deviceServiceBusinessService.associateDeviceService(DeviceServiceDTO.builder().price(1D).build()));
    }

    @Test
    public void deleteDeviceServiceAssociationShouldDeleteDeviceServiceAssociation() {
        //Arrange
        DeviceServiceDTO deviceServiceDTO = DeviceServiceDTO.builder()
                .deviceId(1L)
                .serviceId(1L)
                .build();
        Device device = Device.builder().id(1L).build();
        ServiceBusiness serviceBusiness = ServiceBusiness.builder().id(1L).build();
        DevicesService devicesService = DevicesService.builder()
                .device(device)
                .serviceBusiness(serviceBusiness)
                .build();
        Optional<DevicesService> devicesServiceOptional = Optional.of(devicesService);
        Optional<Device> deviceOptional = Optional.of(device);
        Optional<ServiceBusiness> serviceBusinessOptional = Optional.of(serviceBusiness);

        when(deviceRepository.findById(deviceServiceDTO.getDeviceId())).thenReturn(deviceOptional);
        when(serviceRepository.findById(deviceServiceDTO.getServiceId())).thenReturn(serviceBusinessOptional);
        when(devicesServiceRepository.findDevicesServiceByDeviceAndServiceBusiness(deviceOptional.get(), serviceBusinessOptional.get())).thenReturn(devicesServiceOptional);
        doNothing().when(rudimentaryCache).delete(devicesServiceOptional.get());
        doNothing().when(devicesServiceRepository).delete(devicesServiceOptional.get());

        //Act
        deviceServiceBusinessService.deleteDeviceServiceAssociation(deviceServiceDTO);

        //Assert
        verify(rudimentaryCache, times(1)).delete(devicesServiceOptional.get());
        verify(devicesServiceRepository, times(1)).delete(devicesServiceOptional.get());
    }

    @Test
    public void deleteDeviceServiceAssociationDeviceServiceEmpty() {
        //Arrange
        DeviceServiceDTO deviceServiceDTO = DeviceServiceDTO.builder()
                .deviceId(1L)
                .serviceId(1L)
                .build();
        Device device = Device.builder().id(1L).build();
        ServiceBusiness serviceBusiness = ServiceBusiness.builder().id(1L).build();
        DevicesService devicesService = DevicesService.builder()
                .device(device)
                .serviceBusiness(serviceBusiness)
                .build();
        Optional<DevicesService> devicesServiceOptional = Optional.empty();
        Optional<Device> deviceOptional = Optional.of(device);
        Optional<ServiceBusiness> serviceBusinessOptional = Optional.of(serviceBusiness);

        when(deviceRepository.findById(deviceServiceDTO.getDeviceId())).thenReturn(deviceOptional);
        when(serviceRepository.findById(deviceServiceDTO.getServiceId())).thenReturn(serviceBusinessOptional);
        when(devicesServiceRepository.findDevicesServiceByDeviceAndServiceBusiness(deviceOptional.get(), serviceBusinessOptional.get())).thenReturn(devicesServiceOptional);

        //Assert
        assertThrows(DevicesServicesException.class, () -> deviceServiceBusinessService.deleteDeviceServiceAssociation(deviceServiceDTO));
    }

    @Test
    public void testGetPrices() {
        // create test data
        DeviceServiceDTO[] servicesList = new DeviceServiceDTO[]{
                new DeviceServiceDTO(1L, 2L,1D,1),
                new DeviceServiceDTO(3L, 4L,1D,1)
        };

        Device device1 = new Device();
        device1.setId(1L);
        Device device3 = new Device();
        device3.setId(3L);

        ServiceBusiness service2 = new ServiceBusiness();
        service2.setId(2L);
        ServiceBusiness service4 = new ServiceBusiness();
        service4.setId(4L);

        DevicesService devicesService1 = new DevicesService();
        devicesService1.setDevice(device1);
        devicesService1.setServiceBusiness(service2);
        devicesService1.setPrice(10.0);

        DevicesService devicesService2 = new DevicesService();
        devicesService2.setDevice(device3);
        devicesService2.setServiceBusiness(service4);
        devicesService2.setPrice(20.0);

        // mock repository methods
        when(deviceRepository.findById(1L)).thenReturn(Optional.of(device1));
        when(deviceRepository.findById(3L)).thenReturn(Optional.of(device3));
        when(serviceRepository.findById(2L)).thenReturn(Optional.of(service2));
        when(serviceRepository.findById(4L)).thenReturn(Optional.of(service4));
        when(devicesServiceRepository.findDevicesServiceByDeviceAndServiceBusiness(device1, service2))
                .thenReturn(Optional.of(devicesService1));
        when(devicesServiceRepository.findDevicesServiceByDeviceAndServiceBusiness(device3, service4))
                .thenReturn(Optional.of(devicesService2));

        // mock cache methods
        when(rudimentaryCache.get("1_2")).thenReturn(null);
        when(rudimentaryCache.get("3_4")).thenReturn(devicesService2);

        // call the service method
        TotalResponse result = deviceServiceBusinessService.getPrices(servicesList);

        // assert the result
        assertNotNull(result);
        assertEquals(30.0, result.getTotal(), 0.001);
        assertEquals(2, result.getUnitPrices().size());
        assertEquals(10.0, result.getUnitPrices().get(0).getTotal(), 0.001);
        assertEquals(20.0, result.getUnitPrices().get(1).getTotal(), 0.001);
    }

}

