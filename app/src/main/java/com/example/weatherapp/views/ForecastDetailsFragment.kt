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
        val cityName =  sharedViewModel.cityName.value

        binding.nameCity.text = cityName
        binding.dateTime.text = String.format("Date and time: " + chosenForecast?.dtTxt)

        chosenForecast?.let {
            val feelsLike = it.main.feelsLike.kelvinToCelsius().roundToFloor()
            binding.feelsLike.text = String.format("Feels like: $feelsLike\u2103")

            val temp = it.main.temp.kelvinToCelsius().roundToFloor()
            binding.temp.text = String.format("Temperature: $temp\u2103")

            binding.description.text = String.format("Description: " + it.weather[0].description)
            binding.humidity.text = String.format("Humidity: " + it.main.humidity.toString())
        }

        // Inflate the layout for this fragment
        return binding.root

    }

    override fun onResume() {
        super.onResume()

        binding.backButton.setOnClickListener {
            findNavController().navigate(R.id.action_DetailsFragment_to_ForecastFragment)
        }
    }
}