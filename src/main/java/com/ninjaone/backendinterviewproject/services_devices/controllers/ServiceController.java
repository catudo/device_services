package com.ninjaone.backendinterviewproject.services_devices.controllers;

import com.ninjaone.backendinterviewproject.services_devices.configuration.GlobalConfiguration;
import com.ninjaone.backendinterviewproject.services_devices.dto.ServiceDTO;
import com.ninjaone.backendinterviewproject.services_devices.models.ServiceBusiness;
import com.ninjaone.backendinterviewproject.services_devices.services.ServiceBusinessService;
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
@RequestMapping(GlobalConfiguration.API_PATH + "/services")
public class ServiceController {

    private final ServiceBusinessService serviceBusinessService;

    @Autowired
    public ServiceController(final ServiceBusinessService serviceBusinessService){
        this.serviceBusinessService = serviceBusinessService;
    }


    @PostMapping
    public ResponseEntity<ServiceBusiness> save(@RequestBody ServiceDTO serviceDTO) {
        return new ResponseEntity<>(serviceBusinessService.saveService(serviceDTO), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity delete(@NotNull @RequestParam("id") final Long id) {
        serviceBusinessService.deleteService(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
