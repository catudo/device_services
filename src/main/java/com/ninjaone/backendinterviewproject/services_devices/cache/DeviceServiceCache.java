package com.ninjaone.backendinterviewproject.services_devices.cache;


import com.ninjaone.backendinterviewproject.services_devices.models.DevicesService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DeviceServiceCache extends RudimentaryCache {

    @Override
    public void save(Object object) {
        final DevicesService deviceService = (DevicesService) object;
        getCacheInstance().put(deviceService.getDevice().getId() + "_" + deviceService.getServiceBusiness().getId()
                , object);
        log.info(getCacheInstance().toString());

    }

    @Override
    public Object get(String identifier) {
        return getCacheInstance().get(identifier);

    }

    @Override
    public void delete(Object object) {
        final DevicesService deviceService = (DevicesService) object;
        log.info(getCacheInstance().toString());
        getCacheInstance().remove(deviceService.getDevice().getId() + "_" + deviceService.getServiceBusiness().getId());

    }


}
