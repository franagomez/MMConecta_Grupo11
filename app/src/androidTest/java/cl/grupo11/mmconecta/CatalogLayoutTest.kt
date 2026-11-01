package cl.grupo11.mmconecta

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import cl.grupo11.mmconecta.model.LearningContent
import cl.grupo11.mmconecta.ui.home.HomeScreen
import cl.grupo11.mmconecta.ui.theme.MMConectaTheme
import cl.grupo11.mmconecta.viewmodel.HomeUiState
import cl.grupo11.mmconecta.viewmodel.NotificationsUiState
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class CatalogLayoutTest {
    @get:Rule val compose = createComposeRule()

    @Test fun catalogReflowsFromPhoneToTabletAndLargeText() {
        val width = mutableStateOf(400.dp)
        val scale = mutableStateOf(1f)
        val entries = (1..3).map { LearningContent("$it", "Contenido $it", "Panadería", "Prueba", emptyList()) }
        compose.setContent {
            val density = LocalDensity.current.density
            CompositionLocalProvider(LocalDensity provides Density(density, scale.value)) {
                MMConectaTheme {
                    Box(Modifier.requiredSize(width.value, 1600.dp)) {
                        HomeScreen(HomeUiState(contents = entries, favoritesBusy = false, favoritesLoaded = true),
                            {}, {}, {}, {}, {}, {}, NotificationsUiState(), {})
                    }
                }
            }
        }
        fun bounds(index: Int) = compose.onNodeWithText("Contenido $index", substring = false)
            .getUnclippedBoundsInRoot()
        assertTrue(bounds(2).top > bounds(1).top)
        compose.runOnIdle { width.value = 700.dp }
        assertEquals(bounds(1).top, bounds(2).top)
        assertTrue(bounds(2).left > bounds(1).left)
        assertTrue(bounds(3).top > bounds(1).top)
        compose.runOnIdle { width.value = 1000.dp }
        assertEquals(bounds(1).top, bounds(3).top)
        assertTrue(bounds(3).left > bounds(2).left)
        compose.runOnIdle { scale.value = 1.5f }
        assertTrue(bounds(2).top > bounds(1).top)
    }
}
