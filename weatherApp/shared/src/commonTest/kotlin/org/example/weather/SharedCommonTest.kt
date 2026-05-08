package org.example.weather

import org.example.weather.data.cache.WeatherCache
import org.example.weather.data.model.*
import org.example.weather.viewmodel.WeatherViewModel
import org.example.weather.repository.WeatherRepository
import org.example.weather.data.api.WeatherApi
import kotlin.test.*

// ---- Фейковые данные ----

fun fakeWeatherResponse(city: String = "Minsk") = WeatherResponse(
    cityName = city,
    sys = Sys(country = "BY", sunrise = 1000L, sunset = 2000L),
    main = Main(
        temp = 20.0, feelsLike = 18.0,
        tempMin = 15.0, tempMax = 25.0,
        humidity = 60, pressure = 1013
    ),
    wind = Wind(speed = 3.5),
    weather = listOf(WeatherItem(description = "ясно", icon = "01d")),
    visibility = 10000
)

fun fakeForecastResponse() = ForecastResponse(
    items = listOf(
        ForecastItem(
            dateText = "2024-05-04 12:00:00",
            main = Main(20.0, 18.0, 15.0, 25.0, 60, 1013),
            weather = listOf(WeatherItem("ясно", "01d")),
            wind = Wind(3.5),
            pop = 0.1
        )
    )
)

// ---- Фейковый кэш ----

class FakeWeatherCache(
    private val freshnessResult: Boolean = false,
    private val storedData: WeatherResponse? = null
) : WeatherCache() {
    var savedCity: String? = null
    var savedData: WeatherResponse? = null

    override fun isFresh(city: String, maxAgeMs: Long): Boolean = freshnessResult
    override fun loadWeather(city: String): WeatherResponse? = storedData
    override fun saveWeather(city: String, data: WeatherResponse) {
        savedCity = city
        savedData = data
    }
}

// ---- Тест 1: кэш возвращает данные если они свежие ----

class SharedCommonTest {

    @Test
    fun weatherCache_returnsCachedData_whenFresh() {
        val expected = fakeWeatherResponse("Paris")
        val cache = FakeWeatherCache(freshnessResult = true, storedData = expected)
        val result = cache.loadWeather("Paris")
        assertEquals("Paris", result?.cityName)
        assertEquals(20.0, result?.main?.temp)
    }

    // ---- Тест 2: кэш возвращает null если пуст ----

    @Test
    fun weatherCache_returnsNull_whenEmpty() {
        val cache = FakeWeatherCache(freshnessResult = false, storedData = null)
        val result = cache.loadWeather("London")
        assertNull(result)
    }

    // ---- Тест 3: кэш сохраняет данные ----

    @Test
    fun weatherCache_savesData_correctly() {
        val cache = FakeWeatherCache()
        val data = fakeWeatherResponse("Berlin")
        cache.saveWeather("Berlin", data)
        assertEquals("Berlin", cache.savedCity)
        assertEquals("Berlin", cache.savedData?.cityName)
    }
}