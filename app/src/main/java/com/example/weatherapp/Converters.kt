package com.example.weatherapp

import java.math.RoundingMode
import java.text.DecimalFormat

fun Double.kelvinToCelsius(): Double = (this - 273)

fun Double.roundToFloor(): String {
    val df = DecimalFormat("#.##")
    df.roundingMode = RoundingMode.FLOOR
    return df.format(this)
}