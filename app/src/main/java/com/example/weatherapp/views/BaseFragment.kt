package com.example.weatherapp.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myweatherapp.viewmodel.WeatherViewModel
import com.example.weatherapp.R
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
open class BaseFragment : Fragment() {

    protected val sharedViewModel: WeatherViewModel by sharedViewModel()
//    protected val myViewModel by activityViewModels<WeatherViewModel>()
}
