package org.example.weather.ui.components

import androidx.compose.runtime.Composable

@Composable
expect fun SearchBar(
    value: String,
    onValueChange: (String) -> Unit,
    onSearch: () -> Unit
)