package com.example.weatherapp.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentForecastDetailsBinding
import com.example.weatherapp.kelvinToCelsius
import com.example.weatherapp.roundToFloor


class ForecastDetailsFragment : BaseFragment() {

    private val binding by lazy {
        FragmentForecastDetailsBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val chosenForecast = sharedViewModel.getChosenForecast()
        val cityName =  sharedViewModel.getCityName()

        binding.nameCity.text = cityName
        binding.dateTime.text = "Date and time: " + chosenForecast?.dtTxt

        val feelsLike = chosenForecast?.main?.feelsLike
            ?.let { roundToFloor(kelvinToCelsius(it)) }
        binding.feelsLike.text = "Feels like: $feelsLike\u2103"

        val temp = chosenForecast?.main?.temp
            ?.let { roundToFloor(kelvinToCelsius(it)) }
        binding.temp.text = "Temperature: $temp\u2103"

        binding.description.text = "Description: " + chosenForecast?.weather?.get(0)?.description
        binding.humidity.text = "Humidity: " + chosenForecast?.main?.humidity.toString()
        // Inflate the layout for this fragment
        return binding.root

    }

    override fun onResume() {
        super.onResume()

        binding.backButton.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.action_DetailsFragment_to_ForecastFragment)
        })
    }



    companion object {
        fun newInstance() = ForecastDetailsFragment()
    }
}