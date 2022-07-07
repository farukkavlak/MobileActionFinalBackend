package com.farukkavlak.weatherproject.Dto.ExternalApiJson;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class Coordinates {
    String lat;
    String lon;
}
