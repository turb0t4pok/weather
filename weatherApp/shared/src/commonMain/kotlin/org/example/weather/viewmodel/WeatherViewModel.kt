package org.example.weather.viewmodel

import org.example.weather.repository.WeatherRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WeatherViewModel(private val repository: WeatherRepository) {

    private val _state = MutableStateFlow(WeatherUiState())
    val state: StateFlow<WeatherUiState> = _state.asStateFlow()

    private val scope = CoroutineScope(Dispatchers.Default + Job())

    fun loadWeather(city: String) {
        scope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            repository.getWeather(city)
                .onSuccess { data ->
                    _state.update { it.copy(isLoading = false, weather = data) }
                }
                .onFailure { e ->
                    _state.update { it.copy(isLoading = false, error = e.message ?: "Неизвестная ошибка") }
                }
        }
    }

    fun loadForecast(city: String) {
        scope.launch {
            repository.getForecast(city)
                .onSuccess { data ->
                    _state.update { it.copy(forecast = data) }
                }
                .onFailure { e ->
                    _state.update { it.copy(error = e.message) }
                }
        }
    }
}