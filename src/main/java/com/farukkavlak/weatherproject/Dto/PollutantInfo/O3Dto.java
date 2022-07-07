package com.farukkavlak.weatherproject.Dto.PollutantInfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class O3Dto {
    @JsonProperty("O3")
    String O3;
    public O3Dto(String o3) {
        this.setO3(o3);
    }
}
