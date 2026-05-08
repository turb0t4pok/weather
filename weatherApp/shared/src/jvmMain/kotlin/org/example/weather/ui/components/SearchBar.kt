package org.example.weather.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.*
import androidx.compose.ui.unit.dp

@Composable
actual fun SearchBar(
    value: String,
    onValueChange: (String) -> Unit,
    onSearch: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .weight(1f)
                .onKeyEvent { event ->
                    // Enter по клавише на десктопе
                    if (event.key == Key.Enter && event.type == KeyEventType.KeyUp) {
                        onSearch(); true
                    } else false
                },
            placeholder = { Text("Введите город...") },
            singleLine = true,
            shape = RoundedCornerShape(4.dp)
        )
        Button(
            onClick = onSearch,
            shape = RoundedCornerShape(4.dp)
        ) {
            Text("Найти")
        }
    }
}