package org.example.weather.data.model

data class City(
    val name: String,
    val tempCelsius: Double = 0.0,
    val description: String = "",
    val isLoaded: Boolean = false
)