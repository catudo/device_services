package com.ninjaone.backendinterviewproject.services_devices.converter;

import com.ninjaone.backendinterviewproject.services_devices.dto.ServiceDTO;
import com.ninjaone.backendinterviewproject.services_devices.models.ServiceBusiness;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.core.convert.converter.Converter;

@Mapper
public interface ServiceMapper extends Converter<ServiceDTO, ServiceBusiness> {
    ServiceMapper INSTANCE = Mappers.getMapper(ServiceMapper.class);

    @Override
    ServiceBusiness convert(ServiceDTO source);
}
