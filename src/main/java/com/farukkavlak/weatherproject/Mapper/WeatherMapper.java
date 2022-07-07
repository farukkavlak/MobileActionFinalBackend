package com.farukkavlak.weatherproject.Mapper;

import com.farukkavlak.weatherproject.Dto.*;
import com.farukkavlak.weatherproject.Dto.PollutantInfo.CODto;
import com.farukkavlak.weatherproject.Dto.PollutantInfo.O3Dto;
import com.farukkavlak.weatherproject.Dto.PollutantInfo.SO2Dto;
import com.farukkavlak.weatherproject.Entity.Record;
import com.farukkavlak.weatherproject.Entity.Result;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.farukkavlak.weatherproject.Util.DateUtil.convertDateToString;

public class WeatherMapper {
    public RecordDto convertToRecordDto(Record record){
        RecordDto recordDto = new RecordDto();
        recordDto.setCity(record.getCity());
        recordDto.setResults(convertToResultDtolist(record.getResultList()));
        return recordDto;
    }
    public List<ResultDto> convertToResultDtolist(List<Result> results){
        List<ResultDto> resultDtos = new ArrayList<>();
        for(Result result : results){
            ResultDto resultDto = new ResultDto();
            Date date = new Date((result.getDate())*1000);
            resultDto.setDate(convertDateToString(date));
            resultDto.getCategories().add(new CODto(result.getCategories().getCO()));
            resultDto.getCategories().add(new SO2Dto(result.getCategories().getSO2()));
            resultDto.getCategories().add(new O3Dto(result.getCategories().getO3()));
            resultDtos.add(resultDto);
        }
        return resultDtos;
    }
}
