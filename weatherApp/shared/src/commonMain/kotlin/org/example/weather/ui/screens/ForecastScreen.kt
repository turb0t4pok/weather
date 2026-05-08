package org.example.weather.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.weather.data.model.ForecastItem
import org.example.weather.ui.components.WeatherCard

@Composable
fun ForecastScreen(items: List<ForecastItem>) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items) { item ->
            WeatherCard {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            item.dateText,
                            style = MaterialTheme.typography.labelMedium
                        )
                        Text(
                            item.weather.firstOrNull()?.description
                                ?.replaceFirstChar { it.uppercase() } ?: "",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    Column(horizontalAlignment = androidx.compose.ui.Alignment.End) {
                        Text(
                            "${item.main.temp.toInt()}°C",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            "💧 ${item.main.humidity}%",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }
    }
}