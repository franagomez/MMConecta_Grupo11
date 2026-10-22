package cl.grupo11.mmconecta

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import org.junit.Rule
import org.junit.Test

class NavigationIntegrationTest {
    @get:Rule val compose = createAndroidComposeRule<MainActivity>()

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
}
