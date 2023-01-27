package com.ninjaone.backendinterviewproject.services_devices.cache;

import com.ninjaone.backendinterviewproject.services_devices.enums.CacheType;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;

@Component
public class RudimentaryStrategyCacheFactory {

    private Map<CacheType, RudimentaryCache> strategies
            = new EnumMap<CacheType, RudimentaryCache>(CacheType.class);

    public RudimentaryStrategyCacheFactory(){
        initStrategies();
    }

    private void initStrategies(){
        strategies.put(CacheType.DEVICE_SERVICE, new DeviceServiceCache());
    }

    public RudimentaryCache getCacheType(final CacheType cacheType){
        return strategies.get(cacheType);

    }



}
