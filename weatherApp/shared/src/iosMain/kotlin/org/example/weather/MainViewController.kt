package org.example.weather

import androidx.compose.ui.window.ComposeUIViewController
import org.example.weather.data.api.WeatherApi
import org.example.weather.repository.WeatherRepository
import org.example.weather.viewmodel.WeatherViewModel

fun MainViewController() = ComposeUIViewController {
    val viewModel = WeatherViewModel(
        WeatherRepository(WeatherApi())
    )
    App(viewModel = viewModel)
}