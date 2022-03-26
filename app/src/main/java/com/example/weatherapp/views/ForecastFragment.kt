package com.example.myweatherapp.views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.MainActivity
import com.example.weatherapp.R
import com.example.weatherapp.adapter.WeatherAdapter
import com.example.weatherapp.databinding.FragmentForecastBinding
import com.example.weatherapp.viewmodel.ResultState
import com.example.weatherapp.views.BaseFragment


class ForecastFragment : BaseFragment() {

    private val binding by lazy {
        FragmentForecastBinding.inflate(layoutInflater)
    }

    private val weatherAdapter by lazy {
        WeatherAdapter(onForecastClicked = {forecast ->
            sharedViewModel.setForecast(forecast)
            findNavController().navigate(R.id.action_ForecastFragment_to_DetailsFragment)
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding.myRecycler.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )

            adapter = weatherAdapter
        }

        sharedViewModel.cityForecast.observe(viewLifecycleOwner, ::handleState)
        //myViewModel.cityName.observe(viewLifecycleOwner, Observer {
        //    Log.d("forecast fragment observer", it)
        //})

        val cityName = sharedViewModel.cityName.value
        binding.cityForecast.text = String.format("$cityName forecast")

        cityName?.let {
            sharedViewModel.getForecast(it)
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.returnToSearch.setOnClickListener {
            findNavController().navigate(R.id.action_ForecastFragment_to_SearchFragment)
        }
    }

    private fun handleState(resultState: ResultState) {
        when (resultState) {
            is ResultState.LOADING -> {
                Toast.makeText(requireContext(), "LOADING ....", Toast.LENGTH_LONG).show()
            }
            is ResultState.SUCCESS -> {
                weatherAdapter.setForecast(resultState.results.list)
            }
            is ResultState.ERROR -> {
                Toast.makeText(requireContext(),
                    resultState.error.localizedMessage,
                    Toast.LENGTH_LONG).show()
                Log.e("forecast fragment", resultState.error.localizedMessage)
            }
        }
    }
    companion object {
        fun newInstance() = ForecastFragment()
    }
}