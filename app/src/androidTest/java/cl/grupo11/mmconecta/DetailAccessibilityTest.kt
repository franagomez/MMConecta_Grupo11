package cl.grupo11.mmconecta

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import cl.grupo11.mmconecta.model.LearningContent
import cl.grupo11.mmconecta.ui.detail.ContentDetailScreen
import cl.grupo11.mmconecta.ui.theme.MMConectaTheme
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class DetailAccessibilityTest {
    @get:Rule val compose = createComposeRule()

    @Test fun largeTextKeepsLongDetailScrollableAndBackAvailable() {
        var returned = false
        val content = LearningContent("prueba", "Una preparación con un título largo para practicar",
            "Pastelería", "Descripción de prueba. ".repeat(20),
            listOf("Preparar los ingredientes.", "Revisar el resultado final."))
        compose.setContent {
            val density = LocalDensity.current.density
            CompositionLocalProvider(LocalDensity provides Density(density, fontScale = 2f)) {
                MMConectaTheme {
                    Box(Modifier.requiredSize(320.dp, 480.dp)) {
                        ContentDetailScreen(content) { returned = true }
                    }
                }
            }
        }
        compose.onNodeWithText(content.title).assert(SemanticsMatcher.keyIsDefined(SemanticsProperties.Heading))
        compose.onNode(hasScrollToNodeAction()).performScrollToNode(hasText("Para comenzar"))
        compose.onNodeWithText("Para comenzar").assert(SemanticsMatcher.keyIsDefined(SemanticsProperties.Heading))
        compose.onNode(hasScrollToNodeAction()).performScrollToNode(hasText("2. Revisar el resultado final."))
        compose.onNodeWithText("2. Revisar el resultado final.").assertIsDisplayed()
        compose.onNodeWithText("Volver").assertIsDisplayed().performClick()
        compose.runOnIdle { assertTrue(returned) }
    }

    @Test fun missingContentStillOffersReturn() {
        var returned = false
        compose.setContent { MMConectaTheme { ContentDetailScreen(null) { returned = true } } }
        compose.onNodeWithText("No se encontró el contenido.").assertIsDisplayed()
        compose.onNodeWithText("Volver").performClick()
        compose.runOnIdle { assertTrue(returned) }
    }
}
