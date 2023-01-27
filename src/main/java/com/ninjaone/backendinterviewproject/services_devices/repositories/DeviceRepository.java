package com.ninjaone.backendinterviewproject.services_devices.repositories;

import com.ninjaone.backendinterviewproject.services_devices.models.Device;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeviceRepository extends JpaRepository<Device, Long> {

    Optional<Device> findDeviceBySystemNameAndType(final String systemName, String type );
}