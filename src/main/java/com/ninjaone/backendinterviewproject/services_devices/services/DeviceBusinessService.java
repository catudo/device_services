package com.ninjaone.backendinterviewproject.services_devices.services;

import com.ninjaone.backendinterviewproject.services_devices.converter.DeviceMapper;
import com.ninjaone.backendinterviewproject.services_devices.dto.DeviceDTO;
import com.ninjaone.backendinterviewproject.services_devices.enums.DeviceType;
import com.ninjaone.backendinterviewproject.services_devices.exceptions.DevicesServicesException;
import com.ninjaone.backendinterviewproject.services_devices.models.Device;
import com.ninjaone.backendinterviewproject.services_devices.repositories.DeviceRepository;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
@Service
public class DeviceBusinessService {

    private final DeviceRepository deviceRepository;

    @Autowired
    public DeviceBusinessService(final DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
   }

    public Device saveDevice(DeviceDTO deviceDTO) {

        final boolean isValidDeviceType = EnumUtils
                .isValidEnum(DeviceType.class, deviceDTO.getType());

        if (!isValidDeviceType) {
            throw new DevicesServicesException("devices.wrong.type");
        }

        final Device device = DeviceMapper.INSTANCE.convert(deviceDTO);

        Optional<Device> deviceOptional = deviceRepository
                .findDeviceBySystemNameAndType(deviceDTO.getSystemName(), deviceDTO.getType());

        if (deviceOptional.isPresent()) {
            throw new DevicesServicesException("devices.already_exist");
        }

        final Device savedDevice = deviceRepository.save(device);

        return savedDevice;

    }

    public void deleteDevice(Long id) {

        Optional<Device> deviceOptional = deviceRepository.findById(id);

        if (!deviceOptional.isPresent()) {
            throw new DevicesServicesException("devices.does_not_exist");
        }

        Device currentDevice = deviceOptional.get();

        if(currentDevice.getDevicesServices().size()>0){
            throw new DevicesServicesException("device_service.association.not_empty");
        }

        deviceRepository.delete(deviceOptional.get());

    }

}
