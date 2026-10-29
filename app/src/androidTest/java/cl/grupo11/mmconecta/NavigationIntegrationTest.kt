package cl.grupo11.mmconecta

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import org.junit.Rule
import org.junit.Test

class NavigationIntegrationTest {
    @get:Rule val compose = createAndroidComposeRule<MainActivity>()

    @Test fun profileKeyboardMovesToEmailAndDoneKeepsDraft() {
        compose.onNodeWithText("Mi perfil").performClick()
        compose.onNodeWithText("Nombre").performScrollTo().performClick()
        compose.onNodeWithText("Nombre").performTextReplacement("Prueba teclado")
        compose.onNodeWithText("Nombre").performImeAction()
        compose.onNodeWithText("Correo").assertIsFocused()
        compose.onNodeWithText("Correo").performTextReplacement("teclado@example.com")
        compose.onNodeWithText("Correo").performImeAction()
        compose.onNodeWithText("Correo").assertIsNotFocused()
        compose.onNodeWithText("Prueba teclado").assertExists()
        compose.onNodeWithText("teclado@example.com").assertExists()
        compose.onNode(hasScrollToNodeAction()).performScrollToNode(hasText("Guardar perfil"))
        compose.onNodeWithText("Guardar perfil").assertIsDisplayed().assertIsEnabled()
        compose.onNodeWithText("Perfil guardado en este dispositivo.").assertDoesNotExist()
    }

    @Test fun detailReturnsToSelectedCategoryWithButtonAndSystemBack() {
        compose.onNode(hasText("Chocolatería") and isSelectable()).performClick()
        compose.onNodeWithText("Primeros pasos en chocolatería").performScrollTo().performClick()
        compose.onNodeWithText("Para comenzar").assertExists()
        compose.onNodeWithText("Volver").performClick()
        compose.onNode(hasText("Chocolatería") and isSelectable()).assertIsSelected()
        compose.onNodeWithText("Primeros pasos en chocolatería").performScrollTo().performClick()
        compose.onNodeWithText("Para comenzar").assertExists()
        compose.runOnIdle { compose.activity.onBackPressedDispatcher.onBackPressed() }
        compose.onNode(hasText("Chocolatería") and isSelectable()).assertIsSelected()
    }

    @Test fun detailSurvivesActivityRecreationAndReturnsHome() {
        compose.onNodeWithText("Primeros pasos en chocolatería").performScrollTo().performClick()
        compose.onNodeWithText("Para comenzar").assertExists()
        compose.activityRule.scenario.recreate()
        compose.onNodeWithText("Para comenzar").assertExists()
        compose.onNodeWithText("Volver").performClick()
        compose.onNodeWithText("Mi perfil").performScrollTo().assertIsDisplayed()
    }

    @Test fun profileDraftSurvivesReturnAndRecreationWithoutSaving() {
        compose.onNodeWithText("Mi perfil").performClick()
        compose.onNodeWithText("Nombre").performScrollTo().performTextReplacement("Prueba navegación")
        compose.onNodeWithText("Volver").performClick()
        compose.onNodeWithText("Mi perfil").performScrollTo().performClick()
        compose.onNodeWithText("Prueba navegación").performScrollTo().assertExists()
        compose.activityRule.scenario.recreate()
        compose.onNodeWithText("Prueba navegación").performScrollTo().assertExists()
        compose.runOnIdle { compose.activity.onBackPressedDispatcher.onBackPressed() }
        compose.onNodeWithText("MMConecta").assertExists()
    }
    @Test fun detailSurvivesLandscapeRotation() {
        compose.onNodeWithText("Primeros pasos en chocolatería").performScrollTo().performClick()
        compose.onNodeWithText("Para comenzar").assertExists()
        val original = compose.activity.requestedOrientation
        try {
            compose.runOnIdle {
                compose.activity.requestedOrientation = android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            }
            compose.waitUntil(10_000) {
                compose.activity.resources.configuration.orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE
            }
            compose.onNodeWithText("Para comenzar").performScrollTo().assertIsDisplayed()
            compose.onNodeWithText("Volver").performClick()
            compose.onNodeWithText("Mi perfil").performScrollTo().assertIsDisplayed()
        } finally {
            compose.runOnIdle { compose.activity.requestedOrientation = original }
        }
    }

}
