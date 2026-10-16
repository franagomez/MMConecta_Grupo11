package cl.grupo11.mmconecta

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.getValue
import cl.grupo11.mmconecta.ui.home.HomeScreen
import cl.grupo11.mmconecta.viewmodel.HomeViewModel
import cl.grupo11.mmconecta.ui.theme.MMConectaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MMConectaTheme {
                val homeViewModel: HomeViewModel = viewModel()
                val state by homeViewModel.uiState.collectAsStateWithLifecycle()
                HomeScreen(state, homeViewModel::selectCategory)
            }
        }
    }
}
