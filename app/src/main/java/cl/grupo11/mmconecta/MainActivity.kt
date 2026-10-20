package cl.grupo11.mmconecta

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import cl.grupo11.mmconecta.ui.detail.DetailScreen
import cl.grupo11.mmconecta.ui.home.HomeScreen
import cl.grupo11.mmconecta.ui.theme.MMConectaTheme
import cl.grupo11.mmconecta.viewmodel.HomeViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MMConectaTheme {
                val homeViewModel: HomeViewModel = viewModel()
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "home") {
                    composable("home") {
                        val state by homeViewModel.uiState.collectAsStateWithLifecycle()
                        HomeScreen(
                            state = state,
                            onCategorySelected = homeViewModel::selectCategory,
                            onContentClick = { id -> navController.navigate("detalle/$id") }
                        )
                    }
                    composable(
                        route = "detalle/{contentId}",
                        arguments = listOf(navArgument("contentId") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val contentId = backStackEntry.arguments?.getString("contentId")
                        DetailScreen(
                            content = homeViewModel.getContentById(contentId),
                            onBack = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}