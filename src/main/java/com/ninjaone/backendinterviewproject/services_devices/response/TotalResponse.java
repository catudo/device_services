package com.ninjaone.backendinterviewproject.services_devices.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class TotalResponse {

    private List<UnitPriceResponse> unitPrices;
    private Double total;
}
