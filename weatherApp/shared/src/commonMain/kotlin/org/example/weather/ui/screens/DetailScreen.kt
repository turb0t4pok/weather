package org.example.weather.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.weather.data.model.WeatherResponse
import org.example.weather.ui.components.InfoChip
import org.example.weather.ui.components.WeatherCard

@Composable
fun DetailScreen(data: WeatherResponse) {
    val weather = data.weather.firstOrNull()

    WeatherCard {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                "${data.cityName}, ${data.sys.country}",
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                weather?.description?.replaceFirstChar { it.uppercase() } ?: "",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                "${data.main.temp.toInt()}°C",
                style = MaterialTheme.typography.displayLarge
            )
            Text(
                "Ощущается: ${data.main.feelsLike.toInt()}°C",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                InfoChip("💧 ${data.main.humidity}%")
                InfoChip("💨 ${data.wind.speed} м/с")
                InfoChip("🌡 ${data.main.pressure} гПа")
            }
        }
    }
}