package org.example.weather.data.cache

import org.example.weather.data.model.WeatherResponse
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

open class WeatherCache {

    private val cache = mutableMapOf<String, String>()
    private val timestamps = mutableMapOf<String, Long>()

    open fun saveWeather(city: String, data: WeatherResponse) {
        cache[city] = Json.encodeToString(data)
        timestamps[city] = currentTimeMillis()
    }

    open fun loadWeather(city: String): WeatherResponse? {
        val json = cache[city] ?: return null
        return try {
            Json { ignoreUnknownKeys = true }.decodeFromString(json)
        } catch (e: Exception) {
            null
        }
    }

    open fun isFresh(city: String, maxAgeMs: Long = 10 * 60 * 1000L): Boolean {
        val ts = timestamps[city] ?: return false
        return currentTimeMillis() - ts < maxAgeMs
    }

    fun clearAll() {
        cache.clear()
        timestamps.clear()
    }
}