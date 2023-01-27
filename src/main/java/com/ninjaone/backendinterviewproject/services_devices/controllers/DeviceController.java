package com.ninjaone.backendinterviewproject.services_devices.controllers;

import com.ninjaone.backendinterviewproject.services_devices.configuration.GlobalConfiguration;
import com.ninjaone.backendinterviewproject.services_devices.dto.DeviceDTO;
import com.ninjaone.backendinterviewproject.services_devices.models.Device;
import com.ninjaone.backendinterviewproject.services_devices.services.DeviceBusinessService;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping(GlobalConfiguration.API_PATH + "/devices")
public class DeviceController {

    private final DeviceBusinessService deviceBusinessService;

    @Autowired
    public DeviceController(final DeviceBusinessService deviceBusinessService){
        this.deviceBusinessService = deviceBusinessService;
    }


    @PostMapping
    public ResponseEntity<Device> save(@RequestBody DeviceDTO deviceDTO) {
        return new ResponseEntity<>(deviceBusinessService.saveDevice(deviceDTO), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity delete(@NotNull @RequestParam("id") final Long id) {
        deviceBusinessService.deleteDevice(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
