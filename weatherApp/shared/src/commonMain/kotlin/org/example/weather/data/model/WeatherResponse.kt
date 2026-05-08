package org.example.weather.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponse(
    @SerialName("name") val cityName: String,
    @SerialName("sys") val sys: Sys,
    @SerialName("main") val main: Main,
    @SerialName("wind") val wind: Wind,
    @SerialName("weather") val weather: List<WeatherItem>,
    @SerialName("visibility") val visibility: Int = 0
)

@Serializable
data class Sys(
    val country: String,
    val sunrise: Long,
    val sunset: Long
)

@Serializable
data class Main(
    val temp: Double,
    @SerialName("feels_like") val feelsLike: Double,
    @SerialName("temp_min") val tempMin: Double,
    @SerialName("temp_max") val tempMax: Double,
    val humidity: Int,
    val pressure: Int
)

@Serializable
data class Wind(val speed: Double)

@Serializable
data class WeatherItem(
    val description: String,
    val icon: String
)