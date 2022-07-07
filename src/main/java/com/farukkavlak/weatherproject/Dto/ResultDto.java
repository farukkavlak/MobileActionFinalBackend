package com.farukkavlak.weatherproject.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class ResultDto {
    @JsonProperty("Date")
    String date;
    @JsonProperty("Categories")
    List<Object> categories = new ArrayList<>();
}
