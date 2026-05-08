package org.example.weather

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import androidx.compose.ui.unit.dp
import org.example.weather.data.api.WeatherApi
import org.example.weather.repository.WeatherRepository
import org.example.weather.viewmodel.WeatherViewModel

fun main() = application {
    val viewModel = WeatherViewModel(
        WeatherRepository(WeatherApi())
    )
    Window(
        onCloseRequest = ::exitApplication,
        title = "Weather App",
        state = rememberWindowState(width = 800.dp, height = 600.dp)
    ) {
        App(viewModel = viewModel)
    }
}