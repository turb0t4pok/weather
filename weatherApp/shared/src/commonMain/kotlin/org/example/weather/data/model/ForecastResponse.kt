package org.example.weather.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ForecastResponse(
    @SerialName("list") val items: List<ForecastItem>
)

@Serializable
data class ForecastItem(
    @SerialName("dt_txt") val dateText: String,
    @SerialName("main") val main: Main,
    @SerialName("weather") val weather: List<WeatherItem>,
    @SerialName("wind") val wind: Wind,
    @SerialName("pop") val pop: Double = 0.0
)