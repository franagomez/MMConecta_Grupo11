package cl.grupo11.mmconecta

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.content.ActivityNotFoundException
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import cl.grupo11.mmconecta.repository.LocalProfilePhotoRepository
import cl.grupo11.mmconecta.repository.SystemContentNotifier
import cl.grupo11.mmconecta.viewmodel.ProfilePhotoViewModel
import cl.grupo11.mmconecta.viewmodel.NotificationsViewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import cl.grupo11.mmconecta.repository.LocalProfileRepository
import cl.grupo11.mmconecta.repository.LocalFavoritesRepository
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.activity.compose.BackHandler
import cl.grupo11.mmconecta.ui.navigation.AppScreen
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
                val photoViewModel: ProfilePhotoViewModel = viewModel(factory = viewModelFactory {
                    initializer { ProfilePhotoViewModel(LocalProfilePhotoRepository(filesDir)) }
                })
                val notificationsViewModel: NotificationsViewModel = viewModel(factory = viewModelFactory {
                    initializer { NotificationsViewModel(SystemContentNotifier(applicationContext)) }
                })
                val photoState by photoViewModel.uiState.collectAsStateWithLifecycle()
                val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) {
                    photoViewModel.onCameraResult(it)
                }
                val notificationPermissionLauncher = rememberLauncherForActivityResult(
                    ActivityResultContracts.RequestPermission()
                ) { granted ->
                    if (granted) notificationsViewModel.sendTestNotification()
                    else notificationsViewModel.permissionDenied()
                }
                val notificationsState by notificationsViewModel.uiState.collectAsStateWithLifecycle()
                val profileState by profileViewModel.uiState.collectAsStateWithLifecycle()
                var showProfile by rememberSaveable { mutableStateOf(false) }
                val state by homeViewModel.uiState.collectAsStateWithLifecycle()
                var selectedId by rememberSaveable { mutableStateOf<String?>(null) }
                val selectedContent = homeViewModel.findContent(selectedId)
                BackHandler(enabled = showProfile || selectedContent != null) {
                    if (showProfile) showProfile = false else selectedId = null
                }
                val currentScreen = when {
                    showProfile -> AppScreen.Profile
                    selectedContent != null -> AppScreen.Detail(selectedContent)
                    else -> AppScreen.Home
                }
                AnimatedContent(
                    targetState = currentScreen,
                    label = "screen-transition",
                    transitionSpec = { fadeIn() togetherWith fadeOut() }
                ) { screen ->
                    when (screen) {
                        AppScreen.Profile -> ProfileScreen(profileState, profileViewModel::changeName, profileViewModel::changeEmail,
                            profileViewModel::selectBusiness, profileViewModel::toggleInterest,
                            profileViewModel::save, onBack = { showProfile = false },
                            photoState = photoState,
                            onTakePhoto = {
                                try {
                                    cameraLauncher.launch(null)
                                } catch (error: ActivityNotFoundException) {
                                    photoViewModel.cameraUnavailable()
                                } catch (error: SecurityException) {
                                    photoViewModel.cameraUnavailable()
                                }
                            })
                        is AppScreen.Detail -> ContentDetailScreen(
                            screen.content, onBack = { selectedId = null }
                        )
                        AppScreen.Home -> HomeScreen(state, homeViewModel::selectCategory,
                            onContentSelected = { selectedId = it }, onProfileSelected = { showProfile = true },
                            onFavoriteSelected = homeViewModel::toggleFavorite,
                            onFavoritesFilterChanged = homeViewModel::showFavorites,
                            onRetryFavorites = homeViewModel::loadFavorites,
                            notificationsState = notificationsState,
                            onSendNotification = {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                    notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                                } else {
                                    notificationsViewModel.sendTestNotification()
                                }
                            })
                    }
                }
            }
        }
    }
}
