package com.example.myweatherapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.model.Forecast
import com.example.weatherapp.model.Results
import com.example.weatherapp.rest.WeatherRepository
import com.example.weatherapp.rest.WeatherRepositoryImpl
import com.example.weatherapp.viewmodel.ResultState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WeatherViewModel(
    private val weatherRepository: WeatherRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    init {
        Log.d("VIEWMODEL", "CREATINGG VIEWMODEL")
    }

    private val _cityForecast : MutableLiveData<ResultState> = MutableLiveData(ResultState.LOADING)
    val cityForecast : LiveData<ResultState> get() = _cityForecast

    private var _cityName : MutableLiveData<String> = MutableLiveData()
    val cityName : LiveData<String> get() = _cityName

//    private var _cityName : String? = null

    private var _forecast : MutableLiveData<Forecast> = MutableLiveData()
    val forecast : LiveData<Forecast> get() = _forecast


    fun setForecast(forecast: Forecast) {
        _forecast.value = forecast
    }

    fun getChosenForecast() : Forecast? = forecast.value

    fun setCityName(city: String) {
//        _cityName.postValue(city)
        _cityName.value = city
        Log.d("view model fragment to string", cityName.value.toString())
    }

    fun getForecast(city: String) {
        viewModelScope.launch(dispatcher) {
            // in worker thread
            try {
                val response = weatherRepository.getCityForecast(city)
                if (response.isSuccessful) {
                    response.body()?.let {
                        // I have a forecast for the city
                        // _cityForecast.postValue(ResultState.SUCCESS(it))
                        withContext(Dispatchers.Main) {
                            // in main thread
                            _cityForecast.value = ResultState.SUCCESS(it)
                        }
                    } ?: throw Exception("Response null")
                }
                else {
                    throw Exception("Unsuccessful")
                }
            }
            catch (error: Exception) {
                _cityForecast.postValue(ResultState.ERROR(error))
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}

