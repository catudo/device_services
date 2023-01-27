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
import com.ninjaone.backendinterviewproject.services_devices.response.ErrorBody;
import com.ninjaone.backendinterviewproject.services_devices.response.TotalResponse;
import com.ninjaone.backendinterviewproject.services_devices.response.UnitPriceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DeviceServiceBusinessService {

    private final DeviceRepository deviceRepository;
    private final ServiceRepository serviceRepository;

    private final DevicesServiceRepository devicesServiceRepository;

    private final RudimentaryStrategyCacheFactory rudimentaryStrategyCacheFactory;

    private final RudimentaryCache rudimentaryCache;

    private static ResourceBundle messages = ResourceBundle.getBundle("messages");

    @Autowired
    public DeviceServiceBusinessService(final DeviceRepository deviceRepository, final ServiceRepository serviceRepository, final DevicesServiceRepository devicesServiceRepository, final RudimentaryStrategyCacheFactory rudimentaryStrategyCacheFactory) {
        this.deviceRepository = deviceRepository;
        this.serviceRepository = serviceRepository;
        this.devicesServiceRepository = devicesServiceRepository;
        this.rudimentaryStrategyCacheFactory = rudimentaryStrategyCacheFactory;
        this.rudimentaryCache = rudimentaryStrategyCacheFactory.getCacheType(CacheType.DEVICE_SERVICE);
    }


    public DevicesService associateDeviceService(DeviceServiceDTO deviceServiceDTO) {
        final Optional<Device> deviceOptional = deviceRepository.findById(deviceServiceDTO.getDeviceId());

        if (Objects.isNull(deviceServiceDTO.getPrice())) {
            throw new DevicesServicesException("device_service.empty_price");
        }

        if (deviceOptional.isEmpty()) {
            throw new DevicesServicesException("devices.does_not_exist");
        }

        final Optional<ServiceBusiness> serviceOptional = serviceRepository.findById(deviceServiceDTO.getServiceId());

        if (serviceOptional.isEmpty()) {
            throw new DevicesServicesException("service.does_not_exist");
        }

        Optional<DevicesService> devicesServiceOptional = devicesServiceRepository
                .findDevicesServiceByDeviceAndServiceBusiness(deviceOptional.get(), serviceOptional.get());


        if (devicesServiceOptional.isPresent()) {
            throw new DevicesServicesException("device_service.already_exist");
        }


        final DevicesService devicesService = DevicesService.builder()
                .device(deviceOptional.get()).serviceBusiness(serviceOptional.get())
                .price(deviceServiceDTO.getPrice()).build();


        final DevicesService saved = devicesServiceRepository.save(devicesService);
        rudimentaryCache.save(saved);

        return saved;

    }

    public void deleteDeviceServiceAssociation(DeviceServiceDTO deviceServiceDTO) {

        Optional<ServiceBusiness> serviceOptional = serviceRepository.findById(deviceServiceDTO.getServiceId());
        final Optional<Device> deviceOptional = deviceRepository.findById(deviceServiceDTO.getDeviceId());

        Optional<DevicesService> devicesServiceOptional = devicesServiceRepository
                .findDevicesServiceByDeviceAndServiceBusiness(deviceOptional.get(), serviceOptional.get());

        if (devicesServiceOptional.isEmpty()) {
            throw new DevicesServicesException("device_service.empty");
        }

        rudimentaryCache.delete(devicesServiceOptional.get());
        devicesServiceRepository.delete(devicesServiceOptional.get());

    }


    public TotalResponse getPrices(final DeviceServiceDTO[] servicesList) {

        if (servicesList.length == 0) {
            throw new DevicesServicesException("device_service.list.empty");
        }

        final List<String> keys = Arrays.stream(servicesList)
                .map(service -> service.getDeviceId() + "_" + service.getServiceId())
                .collect(Collectors.toList());

        final List<DevicesService> deviceServiceList = new ArrayList<>();

        for (String key : keys) {
            updateCache(key, deviceServiceList);
        }

        List<UnitPriceResponse> prices = deviceServiceList.stream().map(devicesService -> {
            final DeviceServiceDTO deviceServiceDTO = Arrays.stream(servicesList)
                    .filter(deviceServiceDTOTemp -> deviceServiceDTOTemp.getDeviceId()
                            == devicesService.getDevice().getId() && deviceServiceDTOTemp.getServiceId()
                            == devicesService.getServiceBusiness().getId()).findFirst().get();
            return new UnitPriceResponse(devicesService,deviceServiceDTO);
        }).collect(Collectors.toList());

        final Double total = prices.stream().map(unit -> unit.getTotal())
                .collect(Collectors.summingDouble(Double::doubleValue));

        return TotalResponse.builder().unitPrices(prices).total(total).build();


    }


    private void updateCache(final String key, final List<DevicesService> deviceServiceList) {
        DevicesService devicesService = (DevicesService) rudimentaryCache.get(key);
        if (Objects.isNull(devicesService)) {
            final List<Long> ids = Arrays.stream(key.split("_")).map(id -> Long.parseLong(id))
                    .collect(Collectors.toList());
            final Optional<Device> deviceOptional = deviceRepository.findById(ids.get(0));

            if (deviceOptional.isEmpty()) {
                final DevicesServicesException exception = new DevicesServicesException("devices.does_not_exist");
                exception.setErrors(Arrays
                        .asList(new ErrorBody("device_id " + ids.get(0),
                                List.of(messages.getString("devices.does_not_exist")))));

                throw exception;
            }

            final Optional<ServiceBusiness> serviceOptional = serviceRepository.findById(ids.get(1));

            if (serviceOptional.isEmpty()) {
                final DevicesServicesException exception = new DevicesServicesException("service.does_not_exist");
                exception.setErrors(Arrays
                        .asList(new ErrorBody("service_id " + ids.get(1),
                                List.of(messages.getString("service.does_not_exist")))));

                throw exception;
            }
            log.info("generating cache to device {} and service {}",ids.get(0), ids.get(1) );

            Optional<DevicesService> devicesServiceOptional = devicesServiceRepository.
                    findDevicesServiceByDeviceAndServiceBusiness(deviceOptional.get(), serviceOptional.get());

            if (devicesServiceOptional.isEmpty()) {

                final DevicesServicesException exception = new DevicesServicesException("device_service.empty");
                exception.setErrors(Arrays
                        .asList(new ErrorBody("device_id" + ids.get(0) + " service_id" + ids.get(1),
                                List.of(messages.getString("device_service.empty")))));
            }

            devicesService = devicesServiceOptional.get();
            rudimentaryCache.save(devicesService);

        }

        deviceServiceList.add(devicesService);
    }


}
