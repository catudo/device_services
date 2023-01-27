package com.ninjaone.backendinterviewproject.services_devices.controllers;

import com.ninjaone.backendinterviewproject.services_devices.configuration.GlobalConfiguration;
import com.ninjaone.backendinterviewproject.services_devices.dto.DeviceServiceDTO;
import com.ninjaone.backendinterviewproject.services_devices.models.DevicesService;
import com.ninjaone.backendinterviewproject.services_devices.response.TotalResponse;
import com.ninjaone.backendinterviewproject.services_devices.services.DeviceServiceBusinessService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(GlobalConfiguration.API_PATH + "/device_service")
public class DeviceServiceController {

    private final DeviceServiceBusinessService deviceServiceBusinessService;

    private DeviceServiceController(final DeviceServiceBusinessService deviceServiceBusinessService) {
        this.deviceServiceBusinessService = deviceServiceBusinessService;
    }

    @PostMapping
    public ResponseEntity<DevicesService> save(@RequestBody DeviceServiceDTO deviceServiceDTO) {
        return new ResponseEntity<>(deviceServiceBusinessService.associateDeviceService(deviceServiceDTO), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity delete(@RequestBody DeviceServiceDTO deviceServiceDTO) {
        deviceServiceBusinessService.deleteDeviceServiceAssociation(deviceServiceDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "get_prices")
    public ResponseEntity<TotalResponse> getPrices(@RequestBody DeviceServiceDTO[] deviceServiceDTO) {
        return new ResponseEntity<>(deviceServiceBusinessService.getPrices(deviceServiceDTO), HttpStatus.OK);
    }




}
