package com.farukkavlak.weatherproject.Dto.PollutantInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class SO2Dto {
    @JsonProperty("SO2")
    String SO2;
    public SO2Dto(String so2) {
        this.setSO2(so2);
    }
}
