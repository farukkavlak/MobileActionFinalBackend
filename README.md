# 
<h1 align="center">Mobile Action Final Project Backend Part</h1>
<h1 align="center">Ömer Faruk Kavlak</h1><br>

 <p align="center">
  &#8505; <a href="#demo">Demo</a> 
  &#8505; <a href="#project-details">Project Details</a> 
  &#8505; <a href="#technologies">Technologies</a> 
  &#8505; <a href="#project-requirements">Project Requirements</a> 
  &#8505; <a href="#test">Test</a> 
  &#8505; <a href="#swagger-screenshots">Swagger Screenshots</a> 
</p>

## Frontend Repo
https://github.com/farukkavlak/MobileActionFinalFrontend

## Demo
#### Since used free deployment services, there may be some delays in requests.
https://user-images.githubusercontent.com/79375232/177862614-1692973a-f8d3-4a58-893a-40978c7559f7.mp4

## Project Details

The main purpose of the application is to read the air pollution information(CO,O3,SO2 values) according to the City Name and Date Range using https://openweathermap.org/api, save it in the database and display it to the user.

## Technologies
<ul>
  <li>Frontend = ReactJS</li>
  <li>Backend = Java Spring Boot </li>
  <li>External Api = https://openweathermap.org/api</li>
  <li>Deployment = Heroku</li> <li>Link of Web Service = https://mobileaction-final-frontend.herokuapp.com/</li>
  <li>Database = PostgreSQL</li>
</ul>

## Project Requirements

- Historical Air Pollution (https://openweathermap.org/api/air-pollution#history) ve Geocoding (https://openweathermap.org/api/geocoding-api) endpointleri kulllanılacaktır.✔️
- With the help of the City Name information and the Geocoding API, the coordinate(lat,lon) information of the relevant city will be obtained.✔️
- The inquired air pollution information will include only Carbon monoxide (CO), Ozone (O3), Sulfur dioxide (SO2) information among the pollutant information returned by the relevant endpoint.✔️
- The pollutant concentration information read will be classified as “Good, Satisfactory, Moderate, Poor, Severe and Hazardous” using this table.✔️<br>
<img src="https://github.com/farukkavlak/MobileActionFinalBackend/blob/main/SwaggerUI/Aqi.png"></img>
- In the service, it will be possible to query City Name and Date Range information and historical air pollution information, and previously existing records in the database will be deleted.✔️
- The City Name will be mandatory when inquiring, and if the Date Range is not entered, it will be taken as the last 1 week by default.✔️
- Since historical data access is limited by the API from 27 November 2020 to the present, an error message will be returned regarding the incorrect date range for entries outside of these dates.✔️
- In every query request, the database will be looked at first, and an API query will be made only for dates that are not in the database. The results fetched from the API will be saved in the database. For the fetched results, the information whether they are fetched from the database or API will be logged.✔️
- It is assumed that the Open Weather API returns 24 data per day. Some days, errors may occur due to missing data (especially in January, February).✔️

## Test
- Controller Test : Method Coverage %100 - Line Coverage %100✔️
- Service Test : Method Coverage %100 - Line Coverage %75✔️
- Date Util Test : Method Coverage %100 - Line Coverage %100✔️
<img src="https://github.com/farukkavlak/MobileActionFinalBackend/blob/main/SwaggerUI/TestResults.png"></img>

## Swagger Screenshots
#### Get Weather From Api and Log
<img src="https://github.com/farukkavlak/MobileActionFinalBackend/blob/main/SwaggerUI/getWeatherFromApi.png"></img>
<img src="https://github.com/farukkavlak/MobileActionFinalBackend/blob/main/SwaggerUI/getWeatherFromApiLog.png"></img>

#### Get Weather From Db and then Api and Log
<img src="https://github.com/farukkavlak/MobileActionFinalBackend/blob/main/SwaggerUI/getWeatherFromApiDb.png"></img>
<img src="https://github.com/farukkavlak/MobileActionFinalBackend/blob/main/SwaggerUI/getWeatherFromApiDbLog.png"></img>

#### Get Weather From Api-Db-Api and Log
<img src="https://github.com/farukkavlak/MobileActionFinalBackend/blob/main/SwaggerUI/getWeatherFromApiDbApi.png"></img>
<img src="https://github.com/farukkavlak/MobileActionFinalBackend/blob/main/SwaggerUI/getWeatherFromApiDbApiLog.png"></img>

#### Get Weather Without Date (Default last week)
<img src="https://github.com/farukkavlak/MobileActionFinalBackend/blob/main/SwaggerUI/getWeatherWithoutDate.png"></img>

#### Error - Restrict Date - Earlier than 27 November 2020 and Log
<img src="https://github.com/farukkavlak/MobileActionFinalBackend/blob/main/SwaggerUI/getWeatherError2.png"></img>
<img src="https://github.com/farukkavlak/MobileActionFinalBackend/blob/main/SwaggerUI/getWeatherError2Log.png"></img>

#### Error - Last Date cannot be earlier than First Date
<img src="https://github.com/farukkavlak/MobileActionFinalBackend/blob/main/SwaggerUI/getWeatherError.png"></img>
<img src="https://github.com/farukkavlak/MobileActionFinalBackend/blob/main/SwaggerUI/getWeatherErrorLog.png"></img>

#### Delete Weather
<img src="https://github.com/farukkavlak/MobileActionFinalBackend/blob/main/SwaggerUI/deleteWeather.png"></img>

#### Delete Weather Error - Doesn't Exists in DB 
<img src="https://github.com/farukkavlak/MobileActionFinalBackend/blob/main/SwaggerUI/deleteWeatherError.png"></img>
<img src="https://github.com/farukkavlak/MobileActionFinalBackend/blob/main/SwaggerUI/deleteWeatherErrorLog.png"></img>





