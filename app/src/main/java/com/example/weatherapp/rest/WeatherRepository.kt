package com.example.weatherapp.rest

import com.example.weatherapp.model.City
import com.example.weatherapp.model.Results
import retrofit2.Response

class WeatherRepositoryImpl(
    private val weatherServiceApi:WeatherApi
    ): WeatherRepository{

    override suspend fun getCityForecast(city: String): Response<Results> {
        return weatherServiceApi.getForecast(city = city)
    }

}

interface WeatherRepository{
    suspend fun getCityForecast(city: String): Response<Results>
}


