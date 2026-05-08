package org.example.weather.viewmodel

import org.example.weather.data.model.WeatherResponse
import org.example.weather.data.model.ForecastResponse

data class WeatherUiState(
    val isLoading: Boolean = false,
    val weather: WeatherResponse? = null,
    val forecast: ForecastResponse? = null,
    val error: String? = null
)