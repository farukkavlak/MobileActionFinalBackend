package com.farukkavlak.weatherproject.Dto.PollutantInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter

public class CODto {
    @JsonProperty("CO")
    String CO;
    public CODto(String co) {
        this.setCO(co);
    }
}
