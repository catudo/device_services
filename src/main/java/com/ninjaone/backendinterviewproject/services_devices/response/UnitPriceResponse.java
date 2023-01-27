package com.ninjaone.backendinterviewproject.services_devices.response;

import com.ninjaone.backendinterviewproject.services_devices.dto.DeviceServiceDTO;
import com.ninjaone.backendinterviewproject.services_devices.models.DevicesService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UnitPriceResponse {

    private DevicesService devicesService;
    private double total;

    private DeviceServiceDTO deviceServiceDTO;

    public UnitPriceResponse(final DevicesService devicesService, final DeviceServiceDTO deviceServiceDTO) {
        this.devicesService = devicesService;
        this.deviceServiceDTO = deviceServiceDTO;
        total = deviceServiceDTO.getQuantity() * devicesService.getPrice();

    }

}
