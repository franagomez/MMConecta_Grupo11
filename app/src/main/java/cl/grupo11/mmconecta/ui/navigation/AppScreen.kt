package cl.grupo11.mmconecta.ui.navigation

import cl.grupo11.mmconecta.model.LearningContent

// Cada destino conserva los datos que necesita durante su animación de salida.
sealed interface AppScreen {
    data object Home : AppScreen
    data object Profile : AppScreen
    data class Detail(val content: LearningContent) : AppScreen
}
