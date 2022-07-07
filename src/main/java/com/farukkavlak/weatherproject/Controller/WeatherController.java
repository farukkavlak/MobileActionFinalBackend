package com.farukkavlak.weatherproject.Controller;

import com.farukkavlak.weatherproject.Dto.RecordDto;
import com.farukkavlak.weatherproject.Service.WeatherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

import static com.farukkavlak.weatherproject.Util.DateUtil.getCurrentDateStr;
import static com.farukkavlak.weatherproject.Util.DateUtil.getLastWeekDateStr;

@RestController
@CrossOrigin
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping(value = {"/getWeather/"})
    public ResponseEntity getWeather(@RequestParam(name = "city") String city, @RequestParam(name = "startDate",required = false)String startDate, @RequestParam(name = "endDate",required = false) String endDate){
        //Date is optional - Check null or empty condition
        //If input is without dates , get last week information
        if(startDate == null || startDate.equals("")){
            String first_date_str = getCurrentDateStr();
            String last_date_str = getLastWeekDateStr(new Date());
            RecordDto recordDto = weatherService.getWeather(city,last_date_str,first_date_str);
            return new ResponseEntity<>(recordDto, HttpStatus.OK);
        }
        RecordDto recordDto = weatherService.getWeather(city,startDate,endDate);
        return new ResponseEntity<>(recordDto, HttpStatus.OK);
    }
    @DeleteMapping("/deleteWeatherData/")
    public ResponseEntity deleteWeatherData(@RequestParam("city") String city, @RequestParam(name = "startDate")String startDate, @RequestParam(name = "endDate") String endDate){
        return weatherService.deleteWeather(city,startDate,endDate);
    }
}
