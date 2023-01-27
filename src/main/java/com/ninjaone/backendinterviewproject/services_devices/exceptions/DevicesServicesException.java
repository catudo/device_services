package com.ninjaone.backendinterviewproject.services_devices.exceptions;

import com.ninjaone.backendinterviewproject.services_devices.response.ErrorBody;
import lombok.Data;

import java.util.List;

@Data
public class DevicesServicesException extends  RuntimeException{
    List<ErrorBody> errors;
    public DevicesServicesException (final String message){
        super(message);
    }

}
