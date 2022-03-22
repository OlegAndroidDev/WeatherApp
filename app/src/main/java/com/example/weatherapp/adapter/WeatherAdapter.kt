
package com.example.weatherapp.adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.databinding.ForecastItemBinding
import com.example.weatherapp.kelvinToCelsius
import com.example.weatherapp.model.Forecast
import com.example.weatherapp.roundToFloor
import java.math.RoundingMode
import java.text.DecimalFormat

class WeatherAdapter(
    private val forecastList: MutableList<Forecast> = mutableListOf(),
    private val onForecastClicked: (Forecast) -> Unit
) : RecyclerView.Adapter<WeatherViewHolder>(){

    fun setForecast(newForecast: List<Forecast>) {
        forecastList.clear()
        forecastList.addAll(newForecast)
        notifyItemRangeChanged(0, itemCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val view = ForecastItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return WeatherViewHolder(view, onForecastClicked)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) =
        holder.bind(forecastList[position])

    override fun getItemCount(): Int = forecastList.size
}

class WeatherViewHolder(
    private val binding: ForecastItemBinding,
    private val onForecastClicked: (Forecast) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(forecast: Forecast) {
        val feelsLike = kelvinToCelsius(forecast.main.feelsLike)
        val finalFeelsLike = roundToFloor(feelsLike)
        binding.temperature.text = "${roundToFloor(kelvinToCelsius(forecast.main.temp))}\u2103"
        binding.dateTime.text = forecast.dtTxt
        binding.feelsLike.text = "$finalFeelsLike\u2103"

        binding.forecastItem.setOnClickListener {
            onForecastClicked.invoke(forecast)
        }
    }
}