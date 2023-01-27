package com.ninjaone.backendinterviewproject.services_devices.repositories;

import com.ninjaone.backendinterviewproject.services_devices.models.Device;
import com.ninjaone.backendinterviewproject.services_devices.models.DevicesService;
import com.ninjaone.backendinterviewproject.services_devices.models.ServiceBusiness;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DevicesServiceRepository extends JpaRepository<DevicesService, Long> {

    Optional<DevicesService> findDevicesServiceByDeviceAndServiceBusiness
            (final Device device, final ServiceBusiness serviceBusiness);
}