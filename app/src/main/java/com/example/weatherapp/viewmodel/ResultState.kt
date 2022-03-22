package com.example.weatherapp.viewmodel
import com.example.weatherapp.model.Results

sealed class ResultState {
    object LOADING : ResultState()
    class SUCCESS(val results: Results) : ResultState()
    class ERROR(val error: Throwable) : ResultState()
}
