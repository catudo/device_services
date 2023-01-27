package com.ninjaone.backendinterviewproject.services_devices.services;

import com.ninjaone.backendinterviewproject.services_devices.converter.ServiceMapper;
import com.ninjaone.backendinterviewproject.services_devices.dto.ServiceDTO;
import com.ninjaone.backendinterviewproject.services_devices.models.Device;
import com.ninjaone.backendinterviewproject.services_devices.models.ServiceBusiness;
import com.ninjaone.backendinterviewproject.services_devices.exceptions.DevicesServicesException;
import com.ninjaone.backendinterviewproject.services_devices.repositories.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Optional;


@Service
public class ServiceBusinessService {

    private final ServiceRepository serviceRepository;

    @Autowired
    public ServiceBusinessService(final ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;

    }

    public ServiceBusiness saveService(ServiceDTO serviceDTO) {

        final ServiceBusiness serviceBusiness = ServiceMapper.INSTANCE.convert(serviceDTO);

        Optional<ServiceBusiness> serviceOptional = serviceRepository.findByType(serviceDTO.getType());

        if (serviceOptional.isPresent()) {
            throw new DevicesServicesException("service.already_exist");
        }

        final ServiceBusiness savedService = serviceRepository.save(serviceBusiness);

        return savedService;

    }

    public void deleteService(Long id) {

        Optional<ServiceBusiness> serviceOptional = serviceRepository.findById(id);

        if (!serviceOptional.isPresent()) {
            throw new DevicesServicesException("service.does_not_exist");
        }

        ServiceBusiness service = serviceOptional.get();

        if(service.getDevicesServices().size()>0){
            throw new DevicesServicesException("device_service.association.not_empty");
        }

        serviceRepository.delete(serviceOptional.get());

    }

}
