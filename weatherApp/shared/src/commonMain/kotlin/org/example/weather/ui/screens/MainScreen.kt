package org.example.weather.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.weather.ui.components.*
import org.example.weather.ui.theme.WeatherAppTheme
import org.example.weather.viewmodel.WeatherViewModel

@Composable
fun MainScreen(viewModel: WeatherViewModel) {
    val state by viewModel.state.collectAsState()
    var cityInput by remember { mutableStateOf("") }

    WeatherAppTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "🌤 Погода",
                style = MaterialTheme.typography.headlineMedium
            )

            SearchBar(
                value = cityInput,
                onValueChange = { cityInput = it },
                onSearch = { viewModel.loadWeather(cityInput) }
            )

            when {
                state.isLoading -> LoadingIndicator()
                state.error != null -> ErrorCard(state.error!!)
                state.weather != null -> DetailScreen(data = state.weather!!)
            }
        }
    }
}