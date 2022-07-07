package com.farukkavlak.weatherproject.Controller;

import com.farukkavlak.weatherproject.Config.H2TestProfileJPAConfig;
import com.farukkavlak.weatherproject.WeatherprojectApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = {WeatherprojectApplication.class, H2TestProfileJPAConfig.class})
public class ControllerTest{

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    void setup(){
        mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }

    @Test
    void getWeatherWithDates() throws Exception {
        String callPath ="/getWeather/";
        mockMvc.perform(
                get(callPath).param("city","Tokyo").param("startDate","06-06-2021").param("endDate","10-06-2021")).andExpect(status().isOk()).andReturn();
    }

    @Test
    void getWeatherWithoutDates() throws Exception {
        String callPath ="/getWeather/";
        mockMvc.perform(
                get(callPath).param("city","Ankara").param("startDate","").param("endDate","")).andExpect(status().isOk()).andReturn();
    }
    @Test
    void deleteWeather() throws Exception{
        //To delete data - first insert data to db
        String callPathFirst ="/getWeather/";
        mockMvc.perform(
                get(callPathFirst).param("city","Ankara").param("startDate","06-06-2021").param("endDate","10-06-2021")).andExpect(status().isOk()).andReturn();

        String callPath ="/deleteWeatherData/";
        mockMvc.perform(
                delete(callPath).param("city","Ankara").param("startDate","06-06-2021").param("endDate","10-06-2021")).andExpect(status().isOk()).andReturn();
    }
}
