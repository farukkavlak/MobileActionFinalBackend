package com.farukkavlak.weatherproject.Service;
import com.farukkavlak.weatherproject.Dao.CategoryDao;
import com.farukkavlak.weatherproject.Dao.LogDao;
import com.farukkavlak.weatherproject.Dao.RecordDao;
import com.farukkavlak.weatherproject.Dao.ResultDao;
import com.farukkavlak.weatherproject.Dto.ExternalApiJson.Coordinates;
import com.farukkavlak.weatherproject.Dto.RecordDto;
import com.farukkavlak.weatherproject.Dto.ExternalApiJson.ResponseJson;
import com.farukkavlak.weatherproject.Entity.Category;
import com.farukkavlak.weatherproject.Entity.Log;
import com.farukkavlak.weatherproject.Entity.Result;
import com.farukkavlak.weatherproject.Enum.AirQualityIndex;
import com.farukkavlak.weatherproject.Generic.Enum.ErrorMessage;
import com.farukkavlak.weatherproject.Generic.Exception.BusinessException;
import com.farukkavlak.weatherproject.Mapper.WeatherMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.farukkavlak.weatherproject.Entity.Record;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.farukkavlak.weatherproject.Util.DateUtil.*;

@Service
@Slf4j
public class WeatherService {
    @Autowired
    private RecordDao recordDao;
    @Autowired
    private ResultDao resultDao;
    @Autowired
    private CategoryDao categoryDao;
    @Autowired
    private LogDao logDao;
    private final static String baseUrl = "https://api.openweathermap.org/";
    public RestTemplate restTemplate;

    public WeatherService() {
        this.restTemplate = new RestTemplate();

    }

    public RecordDto getWeather(String city_name, String first_date_str, String last_date_str) {
        Coordinates[] objects = getLocationObject(city_name);

        String lat = objects[0].getLat();
        String lon = objects[0].getLon();

        Date first_date = convertStringToDate(first_date_str);
        Date last_date = convertStringToDate(last_date_str);
        Date apiRestrictionDate = convertStringToDate("27-10-2020");

        if(first_date.compareTo(apiRestrictionDate)<0){
            throw new BusinessException(ErrorMessage.RESTRICT_API_DATE);
        }else if(last_date.compareTo(first_date)<0){
            throw new BusinessException(ErrorMessage.LAST_DATE_CANNOT_BEFORE_FIRST_DATE);
        }

        //To control program travel on exists dates
        //This boolean was used to control which dates will be retrieved from the database, which dates will be called from the API, and to provide transitions.
        boolean flagForExistInDb = resultDao.existsByDateAndCity(convertDateToTimeStamp(first_date), city_name);

        long tempFirstDate = convertDateToTimeStamp(first_date);
        long tempLastDate = convertDateToTimeStamp(last_date);

        Record record = new Record();
        record.setCity(city_name);

        // first date is before or equal to last date - to travel all dates
        while(first_date.compareTo(last_date)<=0){
            if(isResultExistsInDbByCityAndDate(city_name, first_date)){
                if(!flagForExistInDb){
                    flagForExistInDb = true;
                    callFromApi(lat,lon,tempFirstDate,plusOneDayForTimeStamp(tempLastDate),record);
                    tempFirstDate = plusOneDayForTimeStamp(tempLastDate);
                }else {
                    tempLastDate = convertDateToTimeStamp(first_date);
                    if(first_date.compareTo(last_date)==0){
                        callFromDb(city_name,tempFirstDate,plusOneDayForTimeStamp(tempLastDate),record);
                        tempFirstDate = plusOneDayForTimeStamp(tempLastDate);
                    }
                }
            }else{
                if(flagForExistInDb){
                    flagForExistInDb = false;
                    callFromDb(city_name,tempFirstDate,plusOneDayForTimeStamp(tempLastDate),record);
                    tempFirstDate = plusOneDayForTimeStamp(tempLastDate);
                }else {
                    tempLastDate = convertDateToTimeStamp(first_date);
                    if(first_date.compareTo(last_date)==0){
                        callFromApi(lat,lon,tempFirstDate,plusOneDayForTimeStamp(tempLastDate),record);
                        tempFirstDate = plusOneDayForTimeStamp(tempLastDate);
                    }
                }
            }
            first_date = plusOneDay(first_date);
        }

        //Convert record to record dto
        WeatherMapper weatherMapper = new WeatherMapper();
        RecordDto recordDto = weatherMapper.convertToRecordDto(record);
        recordDao.save(record);
        return recordDto;
    }

    public ResponseEntity deleteWeather(String city_name, String first_date, String last_date) {
        Date firstDate = convertStringToDate(first_date);
        Date lastDate = convertStringToDate(last_date);
        List<Result> deletedList = new ArrayList<>();

        //Traverse all day to detect deleted results , check is exist in db or not
        while(firstDate.compareTo(lastDate)<=0){
            if(resultDao.existsByDateAndCity(convertDateToTimeStamp(firstDate),city_name)){
                deletedList.add(resultDao.findByDateAndCity(convertDateToTimeStamp(firstDate),city_name));
                firstDate = plusOneDay(firstDate);
            }else {
                throw new BusinessException(ErrorMessage.NO_RESULT_IN_DB);
            }
        }
        //Delete all list from db
        while(deletedList.size()!=0){
            Result result = deletedList.get(0);
            for(Record record: result.getRecords()){
                record.getResultList().remove(result);
            }
            resultDao.delete(result);
            deletedList.remove(0);
        }
        return new ResponseEntity("Between "+first_date+" and "+last_date+" weather deleted from database.",HttpStatus.OK);
    }



    //Helper functions
    private long convertDateToTimeStamp(Date date) {
        return date.getTime()/1000;
    }
    // Get location info from api
    private Coordinates[] getLocationObject(String city_name) {
        String getLocationUrl = baseUrl+"geo/1.0/direct?q="+city_name+"&appid=837a1e54187b9ee68c1537706a04ecf9";
        ResponseEntity<Coordinates[]> responseEntity = restTemplate.getForEntity(getLocationUrl, Coordinates[].class);
        return responseEntity.getBody();
    }
    //To control this date is exists in database with city and date attributes
    private boolean isResultExistsInDbByCityAndDate(String city_name, Date first_date) {
        return resultDao.existsByDateAndCity(convertDateToTimeStamp(first_date), city_name);
    }
    //Get these dates from api
    private void callFromApi(String lat,String lon,long first_date_timestamp,long last_date_timestamp,Record record) {
        Date firstDate = new Date((first_date_timestamp)*1000);
        Date lastDate = new Date((last_date_timestamp-86400)*1000);
        String logDescription = "Between "+firstDate+" and "+lastDate+" weather info taken from api.";
        log.info(logDescription);
        logDao.save(new Log(logDescription,"Info"));
        String getAirPollutionUrl = baseUrl+"data/2.5/air_pollution/history?lat=" + lat + "&lon=" + lon + "&start="+first_date_timestamp+"&end="+last_date_timestamp+"&appid=837a1e54187b9ee68c1537706a04ecf9";
        ResponseEntity<ResponseJson> responseEntity = restTemplate.getForEntity(getAirPollutionUrl,ResponseJson.class);
        ResponseJson allHourlyData = responseEntity.getBody();
        determineResultsOfRecord(first_date_timestamp,last_date_timestamp, allHourlyData, record);
    }
    //Get these dates from database
    public void callFromDb(String city_name, long first_date_timestamp,long last_date_timestamp,Record record) {
        Date firstDate = new Date((first_date_timestamp)*1000);
        Date lastDate = new Date((last_date_timestamp-86400)*1000);
        String logDescription = "Between "+firstDate+" and "+lastDate+" weather info taken from database.";
        log.info(logDescription);
        logDao.save(new Log(logDescription,"Info"));
        determineResultsOfRecordFromDb(city_name,first_date_timestamp,last_date_timestamp,record);
    }

    public void determineResultsOfRecordFromDb(String city_name,long first_date_timestamp, long last_date_timestamp, Record record) {
        for(long i=first_date_timestamp;i<last_date_timestamp;i+=86400){
            if(resultDao.existsByDateAndCity(i,city_name)){
                Result resultSearch = resultDao.getByDateAndCity(i,city_name);
                record.getResultList().add(resultSearch);
            }

        }
    }
    private void determineResultsOfRecord(long first_date_timestamp,long last_date_timestamp, ResponseJson allHourlyData, Record record) {
        AirQualityIndex aqi = null;
        //Data taken from api for all hours - To traverse day by day used -> dayNo+=24
        for(int dayNo = 0; dayNo< allHourlyData.getList().length; dayNo+=24){
            Result result = new Result();
            Category category = new Category();


            //To compute value average for day -> Sum 24 data(per hour) , then divide by 24
            //And according to the result, give a tag from enum
            float coValueAvg = 0;
            float so2ValueAvg = 0;
            float o3ValueAvg = 0;

            for(int hourNo = dayNo;hourNo<(dayNo+24);hourNo++){
                coValueAvg += Float.parseFloat(allHourlyData.getList()[hourNo].getComponents().getCo());
                so2ValueAvg += Float.parseFloat(allHourlyData.getList()[hourNo].getComponents().getSo2());
                o3ValueAvg += Float.parseFloat(allHourlyData.getList()[hourNo].getComponents().getO3());
            }
            coValueAvg/=24;
            so2ValueAvg/=24;
            o3ValueAvg/=24;

            aqi = determineCOQuality(coValueAvg,aqi);
            category.setCO(aqi.name());

            aqi = determineSO2Quality(so2ValueAvg,aqi);
            category.setSO2(aqi.name());

            aqi = determineO3Quality(o3ValueAvg, aqi);
            category.setO3(aqi.name());

            result.setDate(first_date_timestamp);

            //Increase one day
            first_date_timestamp +=86400;

            result.setCategories(category);
            record.getResultList().add(result);

            result.setCity(record.getCity());
            categoryDao.save(category);
            resultDao.save(result);
            if(first_date_timestamp==last_date_timestamp){
                break;
            }
        }
    }
    //Determine air quality components according to the AQI Enum
    private AirQualityIndex determineCOQuality(float coValue,AirQualityIndex aqi) {
        if(coValue>=0&&coValue<50){
            aqi = AirQualityIndex.Good;
        }else if(coValue>=50&&coValue<100){
            aqi = AirQualityIndex.Satisfactory;
        }else if(coValue>=100&&coValue<200){
            aqi = AirQualityIndex.Moderate;
        }else if(coValue>=200&&coValue<300){
            aqi = AirQualityIndex.Poor;
        }else if(coValue>=300&&coValue<400){
            aqi = AirQualityIndex.Severe;
        }else if(coValue>=400){
            aqi = AirQualityIndex.Hazardous;
        }
        return aqi;
    }
    private AirQualityIndex determineSO2Quality(float so2Value,AirQualityIndex aqi) {
        if(so2Value>=0&&so2Value<40){
            aqi = AirQualityIndex.Good;
        }else if(so2Value>=40&&so2Value<80){
            aqi = AirQualityIndex.Satisfactory;
        }else if(so2Value>=80&&so2Value<380){
            aqi = AirQualityIndex.Moderate;
        }else if(so2Value>=380&&so2Value<800){
            aqi = AirQualityIndex.Poor;
        }else if(so2Value>=800&&so2Value<1600){
            aqi = AirQualityIndex.Severe;
        }else if(so2Value>=1600){
            aqi = AirQualityIndex.Hazardous;
        }
        return aqi;
    }
    private AirQualityIndex determineO3Quality(float o3Value,AirQualityIndex aqi) {
        if(o3Value>=0&&o3Value<50){
            aqi = AirQualityIndex.Good;
        }else if(o3Value>=50&&o3Value<100){
            aqi = AirQualityIndex.Satisfactory;
        }else if(o3Value>=100&&o3Value<168){
            aqi = AirQualityIndex.Moderate;
        }else if(o3Value>=168&&o3Value<208){
            aqi = AirQualityIndex.Poor;
        }else if(o3Value>=208&&o3Value<748){
            aqi = AirQualityIndex.Severe;
        }else if(o3Value>=748){
            aqi = AirQualityIndex.Hazardous;
        }
        return aqi;
    }
}
