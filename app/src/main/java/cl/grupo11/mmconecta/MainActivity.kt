package cl.grupo11.mmconecta

import android.os.Bundle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import cl.grupo11.mmconecta.repository.LocalProfileRepository
import cl.grupo11.mmconecta.repository.LocalFavoritesRepository
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
import cl.grupo11.mmconecta.ui.profile.ProfileScreen
import cl.grupo11.mmconecta.viewmodel.ProfileViewModel
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
                val homeViewModel: HomeViewModel = viewModel(factory = viewModelFactory {
                    initializer { HomeViewModel(LocalFavoritesRepository(applicationContext)) }
                })
                val profileViewModel: ProfileViewModel = viewModel(factory = viewModelFactory {
                    initializer { ProfileViewModel(LocalProfileRepository(applicationContext)) }
                })
                val profileState by profileViewModel.uiState.collectAsStateWithLifecycle()
                var showProfile by rememberSaveable { mutableStateOf(false) }
                val state by homeViewModel.uiState.collectAsStateWithLifecycle()
                var selectedId by rememberSaveable { mutableStateOf<String?>(null) }
                val selectedContent = homeViewModel.findContent(selectedId)
                BackHandler(enabled = showProfile || selectedContent != null) {
                    if (showProfile) showProfile = false else selectedId = null
                }
                if (showProfile) {
                    ProfileScreen(profileState, profileViewModel::changeName, profileViewModel::changeEmail,
                        profileViewModel::selectBusiness, profileViewModel::toggleInterest,
                        profileViewModel::save, onBack = { showProfile = false })
                } else if (selectedContent == null) {
                    HomeScreen(state, homeViewModel::selectCategory,
                        onContentSelected = { selectedId = it }, onProfileSelected = { showProfile = true },
                        onFavoriteSelected = homeViewModel::toggleFavorite,
                        onFavoritesFilterChanged = homeViewModel::showFavorites,
                        onRetryFavorites = homeViewModel::loadFavorites)
                } else {
                    ContentDetailScreen(selectedContent, onBack = { selectedId = null })
                }
            }
        }
    }
}
