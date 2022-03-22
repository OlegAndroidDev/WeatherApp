package com.example.weatherapp.adapter

import com.example.weatherapp.model.Forecast

interface ForecastClicked {
    fun onForecastClick(forecast: Forecast)
}