package org.example.weather.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Modifier

@Composable
actual fun SearchBar(
    value: String,
    onValueChange: (String) -> Unit,
    onSearch: () -> Unit
) {
    SearchBar(
        query = value,
        onQueryChange = onValueChange,
        onSearch = { onSearch() },
        active = false,
        onActiveChange = {},
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text("Поиск города") },
        leadingIcon = {
            Icon(
                imageVector = androidx.compose.material.icons.Icons.Default.Search,
                contentDescription = null
            )
        }
    ) {}
}