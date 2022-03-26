
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
        val feelsLike = forecast.main.feelsLike.kelvinToCelsius()
        val finalFeelsLike = feelsLike.roundToFloor()
        binding.temperature.text = String.format("${forecast.main.temp.kelvinToCelsius().roundToFloor()}\u2103")
        binding.dateTime.text = forecast.dtTxt
        binding.feelsLike.text = String.format("$finalFeelsLike\u2103")

        binding.forecastItem.setOnClickListener {
            onForecastClicked.invoke(forecast)
        }
    }
}