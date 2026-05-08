package org.example.weather

import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.example.weather.data.api.WeatherApi
import org.example.weather.data.model.*
import org.example.weather.repository.WeatherRepository
import kotlin.test.*

fun mockWeatherJson(city: String = "Minsk") = """
{
  "name": "$city",
  "sys": { "country": "BY", "sunrise": 1000, "sunset": 2000 },
  "main": { "temp": 20.0, "feels_like": 18.0, "temp_min": 15.0,
            "temp_max": 25.0, "humidity": 60, "pressure": 1013 },
  "wind": { "speed": 3.5 },
  "weather": [{ "description": "ясно", "icon": "01d" }],
  "visibility": 10000
}
""".trimIndent()

fun mockForecastJson() = """
{
  "list": [{
    "dt_txt": "2024-05-04 12:00:00",
    "main": { "temp": 20.0, "feels_like": 18.0, "temp_min": 15.0,
              "temp_max": 25.0, "humidity": 60, "pressure": 1013 },
    "weather": [{ "description": "ясно", "icon": "01d" }],
    "wind": { "speed": 3.5 },
    "pop": 0.1
  }]
}
""".trimIndent()

fun buildMockApi(
    weatherJson: String = mockWeatherJson(),
    forecastJson: String = mockForecastJson()
): WeatherApi {
    val engine = MockEngine { request ->
        val url = request.url.toString()
        when {
            url.contains("weather") && !url.contains("forecast") -> respond(
                content = weatherJson,
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
            url.contains("forecast") -> respond(
                content = forecastJson,
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
            else -> respondError(HttpStatusCode.NotFound)
        }
    }
    val client = HttpClient(engine) {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }
    return WeatherApi(client)
}

class SharedLogicDesktopTest {

    // ---- Тест 1: API возвращает правильно распарсенные данные ----

    @Test
    fun api_fetchCurrent_returnsCorrectData() = runTest {
        val api = buildMockApi()
        val result = api.fetchCurrent("Minsk")
        assertEquals("Minsk", result.cityName)
        assertEquals(20.0, result.main.temp)
        assertEquals(60, result.main.humidity)
        assertEquals("BY", result.sys.country)
    }

    // ---- Тест 2: API возвращает прогноз ----

    @Test
    fun api_fetchForecast_returnsItems() = runTest {
        val api = buildMockApi()
        val result = api.fetchForecast("Minsk")
        assertEquals(1, result.items.size)
        assertEquals("2024-05-04 12:00:00", result.items[0].dateText)
        assertEquals(20.0, result.items[0].main.temp)
    }

    // ---- Тест 3: Repository использует кэш если данные свежие ----

    @Test
    fun repository_usesCachedData_whenFresh() = runTest {
        val cached = fakeWeatherResponse("Minsk")
        val cache = object : org.example.weather.data.cache.WeatherCache() {
            override fun isFresh(city: String, maxAgeMs: Long) = true
            override fun loadWeather(city: String) = cached
            override fun saveWeather(city: String, data: WeatherResponse) {}
        }
        val api = buildMockApi()
        val repo = WeatherRepository(api, cache)
        val result = repo.getWeather("Minsk")
        assertTrue(result.isSuccess)
        assertEquals("Minsk", result.getOrNull()?.cityName)
    }
}