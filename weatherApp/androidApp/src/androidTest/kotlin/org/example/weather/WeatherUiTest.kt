package org.example.weather

import androidx.compose.material3.Text
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import org.junit.Test
import org.example.weather.data.model.*
import org.example.weather.ui.components.*
import org.example.weather.ui.screens.DetailScreen
import org.example.weather.ui.theme.WeatherAppTheme

fun fakeWeather(city: String = "Minsk") = WeatherResponse(
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

class WeatherUiTest {

    @get:Rule
    val composeRule = androidx.compose.ui.test.junit4.v2.createComposeRule()

    // UI Тест 1: WeatherCard отображает контент
    @Test
    fun weatherCard_displaysContent() {
        composeRule.setContent {
            WeatherAppTheme {
                WeatherCard {
                    Text("25°C")
                }
            }
        }
        composeRule.waitForIdle()
        composeRule.onNodeWithText("25°C").assertIsDisplayed()
    }

    // UI Тест 2: SearchBar вызывает onSearch при нажатии
    @Test
    fun searchBar_callsOnSearch_whenButtonClicked() {
        var searched = false
        composeRule.setContent {
            WeatherAppTheme {
                SearchBar(
                    value = "Minsk",
                    onValueChange = {},
                    onSearch = { searched = true }
                )
            }
        }
        composeRule.waitForIdle()
        composeRule.onNodeWithText("🔍").performClick()
        composeRule.waitForIdle()
        assert(searched)
    }

    // UI Тест 3: DetailScreen показывает данные о погоде
    @Test
    fun detailScreen_displaysWeatherData() {
        val fakeData = fakeWeather("Minsk")
        composeRule.setContent {
            WeatherAppTheme {
                DetailScreen(data = fakeData)
            }
        }
        composeRule.waitForIdle()
        composeRule.onNodeWithText("Minsk, BY").assertIsDisplayed()
        composeRule.onNodeWithText("20°C").assertIsDisplayed()
        composeRule.onNodeWithText("💧 60%").assertIsDisplayed()
    }
}