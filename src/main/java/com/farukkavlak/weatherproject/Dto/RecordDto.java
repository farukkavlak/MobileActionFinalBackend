package com.farukkavlak.weatherproject.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Data
@Getter
@Setter
public class RecordDto {
    @JsonProperty("City")
    String City;
    @JsonProperty("Results")
    List<ResultDto> Results;
}
