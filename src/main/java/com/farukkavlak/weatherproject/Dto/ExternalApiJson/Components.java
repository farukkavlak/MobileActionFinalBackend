package com.farukkavlak.weatherproject.Dto.ExternalApiJson;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class Components {
    String co;
    String o3;
    String so2;
}
