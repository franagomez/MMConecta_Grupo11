package cl.grupo11.mmconecta

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.activity.compose.BackHandler
import cl.grupo11.mmconecta.ui.detail.ContentDetailScreen
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
                var selectedId by rememberSaveable { mutableStateOf<String?>(null) }
                val selectedContent = homeViewModel.findContent(selectedId)
                BackHandler(enabled = selectedContent != null) { selectedId = null }
                if (selectedContent == null) {
                    HomeScreen(state, homeViewModel::selectCategory) { selectedId = it }
                } else {
                    ContentDetailScreen(selectedContent, onBack = { selectedId = null })
                }
            }
        }
    }
}
