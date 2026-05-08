package org.example.weather

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import org.example.weather.data.api.WeatherApi
import org.example.weather.repository.WeatherRepository
import org.example.weather.viewmodel.WeatherViewModel
import kotlinx.browser.document

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    val viewModel = WeatherViewModel(
        WeatherRepository(WeatherApi())
    )
    ComposeViewport(document.body!!) {
        App(viewModel = viewModel)
    }
}