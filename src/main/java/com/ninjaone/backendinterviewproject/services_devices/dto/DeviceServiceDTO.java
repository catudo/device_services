package com.ninjaone.backendinterviewproject.services_devices.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeviceServiceDTO {

    private Long deviceId;
    private Long serviceId;
    private Double price;

    private Integer quantity;


}
