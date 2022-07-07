package com.farukkavlak.weatherproject.Service;

import com.farukkavlak.weatherproject.Config.H2TestProfileJPAConfig;
import com.farukkavlak.weatherproject.Dao.CategoryDao;
import com.farukkavlak.weatherproject.Dao.LogDao;
import com.farukkavlak.weatherproject.Dao.RecordDao;
import com.farukkavlak.weatherproject.Dao.ResultDao;
import com.farukkavlak.weatherproject.Dto.RecordDto;
import com.farukkavlak.weatherproject.Entity.Record;
import com.farukkavlak.weatherproject.Generic.Enum.ErrorMessage;
import com.farukkavlak.weatherproject.Generic.Exception.BusinessException;
import com.farukkavlak.weatherproject.WeatherprojectApplication;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ExtendWith(OutputCaptureExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@SpringBootTest(classes = {WeatherprojectApplication.class, H2TestProfileJPAConfig.class})
public class ServiceTest {
    @Mock
    private RecordDao recordDao;
    @Mock
    private ResultDao resultDao;
    @Mock
    private CategoryDao categoryDao;
    @Mock
    private LogDao logDao;
    @InjectMocks
    public WeatherService weatherService;

    @Test
    public void shouldNotGetRestrictDateError(){
        String city_name = "Ankara";
        String first_date = "15-05-2020";
        String last_date = "23-05-2020";
        BusinessException businessException = assertThrows(BusinessException.class,() -> weatherService.getWeather(city_name,first_date,last_date));
        assertEquals(ErrorMessage.RESTRICT_API_DATE,businessException.getBaseErrorMessage());
    }

    @Test
    public void shouldNotGetFirstDateCannotBeforeLastError(){
        String city_name = "Barcelona";
        String first_date = "15-06-2021";
        String last_date = "10-06-2021";
        BusinessException businessException = assertThrows(BusinessException.class,() -> weatherService.getWeather(city_name,first_date,last_date));
        assertEquals(ErrorMessage.LAST_DATE_CANNOT_BEFORE_FIRST_DATE,businessException.getBaseErrorMessage());
    }

    @Test
    public void shouldGetWeatherDataFromApi1(CapturedOutput output){
        String city_name = "Ankara";
        String first_date = "06-06-2021";
        String last_date = "19-06-2021";
        RecordDto recordDto = mock(RecordDto.class);
        when(weatherService.getWeather(city_name,first_date,last_date)).thenReturn(recordDto);
        recordDto = weatherService.getWeather(city_name,first_date,last_date);
        assertNotNull(recordDto);
        assertNotNull(recordDto.getResults());
        assertEquals(recordDto.getCity(),city_name);
        assertThat(output).contains("Between Sun Jun 06 00:00:00 UTC 2021 and Sat Jun 19 00:00:00 UTC 2021 weather info taken from api.");
    }
    @Test
    public void shouldGetWeatherDataFromApi2(CapturedOutput output){
        String city_name = "Mumbai";
        String first_date = "05-06-2022";
        String last_date = "24-06-2022";
        RecordDto recordDto = mock(RecordDto.class);
        when(weatherService.getWeather(city_name,first_date,last_date)).thenReturn(recordDto);
        recordDto = weatherService.getWeather(city_name,first_date,last_date);
        assertNotNull(recordDto);
        assertNotNull(recordDto.getResults());
        assertEquals(recordDto.getCity(),city_name);
        assertThat(output).contains("Between Sun Jun 05 00:00:00 UTC 2022 and Fri Jun 24 00:00:00 UTC 2022 weather info taken from api.");
    }
    @Test
    public void shouldGetWeatherDataFromApi3(CapturedOutput output){
        String city_name = "Tokyo";
        String first_date = "08-09-2021";
        String last_date = "23-09-2021";
        RecordDto recordDto = mock(RecordDto.class);
        when(weatherService.getWeather(city_name,first_date,last_date)).thenReturn(recordDto);
        recordDto = weatherService.getWeather(city_name,first_date,last_date);
        assertNotNull(recordDto);
        assertNotNull(recordDto.getResults());
        assertEquals(recordDto.getCity(),city_name);
        assertThat(output).contains("Between Wed Sep 08 00:00:00 UTC 2021 and Thu Sep 23 00:00:00 UTC 2021 weather info taken from api.");
    }
    @Test
    public void shouldGetWeatherDataFromApi4(CapturedOutput output){
        String city_name = "Barcelona";
        String first_date = "15-03-2022";
        String last_date = "29-03-2022";
        RecordDto recordDto = mock(RecordDto.class);
        when(weatherService.getWeather(city_name,first_date,last_date)).thenReturn(recordDto);
        recordDto = weatherService.getWeather(city_name,first_date,last_date);
        assertNotNull(recordDto);
        assertNotNull(recordDto.getResults());
        assertEquals(recordDto.getCity(),city_name);
        assertThat(output).contains("Between Tue Mar 15 00:00:00 UTC 2022 and Tue Mar 29 00:00:00 UTC 2022 weather info taken from api.");
    }
    @Test
    public void shouldGetWeatherDataFromApi5(CapturedOutput output){
        String city_name = "London";
        String first_date = "03-12-2021";
        String last_date = "24-12-2021";
        RecordDto recordDto = mock(RecordDto.class);
        when(weatherService.getWeather(city_name,first_date,last_date)).thenReturn(recordDto);
        recordDto = weatherService.getWeather(city_name,first_date,last_date);
        assertNotNull(recordDto);
        assertNotNull(recordDto.getResults());
        assertEquals(recordDto.getCity(),city_name);
        assertThat(output).contains("Between Fri Dec 03 00:00:00 UTC 2021 and Fri Dec 24 00:00:00 UTC 2021 weather info taken from api.");
    }
    @Test
    public void shouldGetWeatherDataFromApi6(CapturedOutput output){
        String city_name = "Tokyo";
        String first_date = "03-12-2021";
        String last_date = "27-12-2021";
        RecordDto recordDto = mock(RecordDto.class);
        when(weatherService.getWeather(city_name,first_date,last_date)).thenReturn(recordDto);
        recordDto = weatherService.getWeather(city_name,first_date,last_date);
        assertNotNull(recordDto);
        assertNotNull(recordDto.getResults());
        assertEquals(recordDto.getCity(),city_name);
        assertThat(output).contains("Between Fri Dec 03 00:00:00 UTC 2021 and Mon Dec 27 00:00:00 UTC 2021 weather info taken from api.");
    }
    @Test
    public void shouldGetWeatherDataFromApi7(CapturedOutput output){
        String city_name = "Mumbai";
        String first_date = "10-03-2022";
        String last_date = "17-03-2022";
        RecordDto recordDto = mock(RecordDto.class);
        when(weatherService.getWeather(city_name,first_date,last_date)).thenReturn(recordDto);
        recordDto = weatherService.getWeather(city_name,first_date,last_date);
        assertNotNull(recordDto);
        assertNotNull(recordDto.getResults());
        assertEquals(recordDto.getCity(),city_name);
        assertThat(output).contains("Between Thu Mar 10 00:00:00 UTC 2022 and Thu Mar 17 00:00:00 UTC 2022 weather info taken from api.");
    }
    @Test
    public void shouldGetWeatherDataFromDb(CapturedOutput output){
        String city_name = "Barcelona";
        Long first_date = 1655683200L;
        Long last_date = 1656547200L;
        Record record = mock(Record.class);
        weatherService.callFromDb(city_name,first_date,last_date,record);
        assertThat(output).contains("Between Mon Jun 20 00:00:00 UTC 2022 and Wed Jun 29 00:00:00 UTC 2022 weather info taken from database.");
    }
    @Test
    public void deleteWeatherError(){
        String city_name = "Barcelona";
        String first_date = "06-06-2021";
        String last_date = "12-06-2021";
        BusinessException businessException = assertThrows(BusinessException.class,() -> weatherService.deleteWeather(city_name,first_date,last_date));
        assertEquals(ErrorMessage.NO_RESULT_IN_DB,businessException.getBaseErrorMessage());
    }

}
