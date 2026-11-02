package cl.grupo11.mmconecta.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.grupo11.mmconecta.repository.ProfilePhotoRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ProfilePhotoUiState(
    val photo: Bitmap? = null,
    val busy: Boolean = true,
    val message: String? = null,
    val isError: Boolean = false
)

class ProfilePhotoViewModel(private val repository: ProfilePhotoRepository) : ViewModel() {
    private val mutableState = MutableStateFlow(ProfilePhotoUiState())
    val uiState = mutableState.asStateFlow()

    private val initialLoad = viewModelScope.launch {
        try {
            val photo = repository.load()
            mutableState.update { it.copy(photo = photo, busy = false) }
        } catch (error: CancellationException) {
            throw error
        } catch (error: Exception) {
            mutableState.update { it.copy(busy = false, message = "No pudimos cargar la imagen guardada.", isError = true) }
        }
    }

    fun cameraUnavailable() {
        mutableState.update { it.copy(message = "No se pudo abrir la cámara. Comprueba que haya una aplicación de cámara disponible.", isError = true) }
    }

    fun onCameraResult(photo: Bitmap?) {
        if (photo == null) {
            mutableState.update { it.copy(message = "No se recibió una imagen. Se conserva la anterior.", isError = false) }
            return
        }
        viewModelScope.launch {
            initialLoad.join()
            mutableState.update { it.copy(busy = true, message = null, isError = false) }
            try {
                repository.save(photo)
                mutableState.update { it.copy(photo = photo, busy = false, message = "Imagen guardada en este dispositivo.") }
            } catch (error: CancellationException) {
                throw error
            } catch (error: Exception) {
                mutableState.update { it.copy(busy = false, message = "No pudimos guardar la imagen. Se conserva la anterior; vuelve a intentarlo.", isError = true) }
            }
        }
    }
}
