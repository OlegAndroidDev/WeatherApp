package com.example.weatherapp

import java.math.RoundingMode
import java.text.DecimalFormat

fun kelvinToCelsius(kelvin: Double): Double {
    return (kelvin - 273)
}

fun roundToFloor(double: Double): String {
    val df = DecimalFormat("#.##")
    df.roundingMode = RoundingMode.FLOOR
    return df.format(double)
}