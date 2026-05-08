package org.example.weather.repository

import org.example.weather.data.api.WeatherApi
import org.example.weather.data.cache.WeatherCache
import org.example.weather.data.model.ForecastResponse
import org.example.weather.data.model.WeatherResponse

class WeatherRepository(
    private val api: WeatherApi,
    private val cache: WeatherCache = WeatherCache()
) {
    suspend fun getWeather(city: String): Result<WeatherResponse> = runCatching {
        if (cache.isFresh(city)) {
            cache.loadWeather(city) ?: fetchAndCache(city)
        } else {
            fetchAndCache(city)
        }
    }

    suspend fun getForecast(city: String): Result<ForecastResponse> = runCatching {
        api.fetchForecast(city)
    }

    private suspend fun fetchAndCache(city: String): WeatherResponse {
        val data = api.fetchCurrent(city)
        cache.saveWeather(city, data)
        return data
    }
}