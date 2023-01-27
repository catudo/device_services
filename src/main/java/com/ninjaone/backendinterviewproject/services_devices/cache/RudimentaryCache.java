package com.ninjaone.backendinterviewproject.services_devices.cache;


import java.util.HashMap;
import java.util.Objects;

public abstract class RudimentaryCache {;
    private static HashMap<String, Object> cache;

    public abstract void save(Object object);

    public abstract Object get(String identifier);

    public abstract void delete(Object identifier);

    public static HashMap getCacheInstance(){
        if(Objects.isNull(cache) ) {
            cache = new HashMap<>();
        }
        return cache;
    }



}
