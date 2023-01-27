package com.ninjaone.backendinterviewproject.services_devices.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorBody {
    private String field;
    private List<String> message;
}
