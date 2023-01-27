package com.ninjaone.backendinterviewproject.services_devices.services;


import com.ninjaone.backendinterviewproject.services_devices.converter.ServiceMapper;
import com.ninjaone.backendinterviewproject.services_devices.dto.ServiceDTO;
import com.ninjaone.backendinterviewproject.services_devices.enums.DeviceType;
import com.ninjaone.backendinterviewproject.services_devices.exceptions.DevicesServicesException;
import com.ninjaone.backendinterviewproject.services_devices.models.DevicesService;
import com.ninjaone.backendinterviewproject.services_devices.models.ServiceBusiness;
import com.ninjaone.backendinterviewproject.services_devices.repositories.ServiceRepository;
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

public class ServiceBusinessServiceTest {
    @InjectMocks
    private ServiceBusinessService serviceBusinessService;

    @Mock
    private ServiceRepository serviceRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSaveServiceValid() {
        ServiceDTO serviceDTO = new ServiceDTO();
        serviceDTO.setId(1L);
        ServiceBusiness serviceBusiness = ServiceMapper.INSTANCE.convert(serviceDTO);
        when(serviceRepository.findByType(any())).thenReturn(Optional.empty());
        when(serviceRepository.save(any())).thenReturn(serviceBusiness);
        ServiceBusiness savedService = serviceBusinessService.saveService(serviceDTO);
       assertEquals(savedService.getId(), serviceBusiness.getId());
    }

    @Test
    public void testSaveService_alreadyExist() {
        ServiceDTO serviceDTO = new ServiceDTO();
        serviceDTO.setType("testType");
        when(serviceRepository.findByType(any())).thenReturn(Optional.of(new ServiceBusiness()));
        assertThrows(DevicesServicesException.class, () -> serviceBusinessService.saveService(serviceDTO));
    }

    @Test
    public void testDeleteService_valid() {
        ServiceBusiness service = new ServiceBusiness();
        when(serviceRepository.findById(any())).thenReturn(Optional.of(service));
        serviceBusinessService.deleteService(1L);
        verify(serviceRepository).delete(service);
    }

    @Test
    public void testDeleteService_notExist() {
        when(serviceRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(DevicesServicesException.class, () -> serviceBusinessService.deleteService(1L));
    }

    @Test
    public void testDeleteDeviceAssociated() {
        Optional<ServiceBusiness> optionalDevice = Optional.of(ServiceBusiness.builder()
                .devicesServices(Set.of(new DevicesService())).build());

        when(serviceRepository.findById(any())).thenReturn(optionalDevice);
        assertThrows(DevicesServicesException.class, () -> serviceBusinessService.deleteService(1L));
    }



}

