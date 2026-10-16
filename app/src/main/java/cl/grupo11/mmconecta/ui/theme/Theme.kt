package cl.grupo11.mmconecta.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Paleta provisional; no representa una identidad corporativa autorizada.
private val LightColors = lightColorScheme(primary = Color(0xFF7A263A),
    secondary = Color(0xFF725B40), background = Color(0xFFFFF8F5))
private val DarkColors = darkColorScheme(primary = Color(0xFFFFB1C0),
    secondary = Color(0xFFE0C2A0))

@Composable
fun MMConectaTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = if (isSystemInDarkTheme()) DarkColors else LightColors,
        content = content)
}
