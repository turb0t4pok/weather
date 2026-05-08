package org.example.weather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import org.example.weather.data.api.WeatherApi
import org.example.weather.repository.WeatherRepository
import org.example.weather.viewmodel.WeatherViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = WeatherViewModel(
            WeatherRepository(WeatherApi())
        )
        setContent {
            App(viewModel = viewModel)
        }
    }
}