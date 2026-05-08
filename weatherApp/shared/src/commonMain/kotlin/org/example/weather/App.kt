package org.example.weather

import androidx.compose.runtime.Composable
import org.example.weather.ui.screens.MainScreen
import org.example.weather.viewmodel.WeatherViewModel

@Composable
fun App(viewModel: WeatherViewModel) {
    MainScreen(viewModel = viewModel)
}