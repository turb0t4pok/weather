package org.example.weather.data.api

import org.example.weather.data.model.ForecastResponse
import org.example.weather.data.model.WeatherResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class WeatherApi(private val client: HttpClient = defaultClient()) {
    companion object {
        fun defaultClient() = HttpClient {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true; isLenient = true })
            }
            install(Logging) { level = LogLevel.INFO }
        }
    }

    private val apiKey = "ffe1e4d5139a7e69670e3036dfe29f2c"
    private val baseUrl = "https://api.openweathermap.org/data/2.5"

    suspend fun fetchCurrent(city: String): WeatherResponse =
        client.get("$baseUrl/weather") {
            parameter("q", city)
            parameter("appid", apiKey)
            parameter("units", "metric")
            parameter("lang", "ru")
        }.body()

    suspend fun fetchForecast(city: String): ForecastResponse =
        client.get("$baseUrl/forecast") {
            parameter("q", city)
            parameter("appid", apiKey)
            parameter("units", "metric")
            parameter("lang", "ru")
            parameter("cnt", 40)
        }.body()
}