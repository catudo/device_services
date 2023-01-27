package com.ninjaone.backendinterviewproject.services_devices.converter;

import com.ninjaone.backendinterviewproject.services_devices.dto.DeviceDTO;
import com.ninjaone.backendinterviewproject.services_devices.models.Device;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.core.convert.converter.Converter;

@Mapper
public interface DeviceMapper extends Converter<DeviceDTO,Device > {
    DeviceMapper INSTANCE = Mappers.getMapper(DeviceMapper.class);

    @Override
    Device convert(DeviceDTO source);
}
