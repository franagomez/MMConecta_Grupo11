package cl.grupo11.mmconecta.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.grupo11.mmconecta.repository.ContentNotifier
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class NotificationsUiState(
    val busy: Boolean = false,
    val message: String? = null,
    val isError: Boolean = false
)

class NotificationsViewModel(private val notifier: ContentNotifier) : ViewModel() {
    private val mutableState = MutableStateFlow(NotificationsUiState())
    val uiState = mutableState.asStateFlow()

    fun permissionDenied() {
        mutableState.update {
            it.copy(message = "No se pudo enviar la notificación: permiso denegado. Puedes habilitarlo en Ajustes.", isError = true)
        }
    }

    fun sendTestNotification() {
        mutableState.update { it.copy(busy = true, message = null, isError = false) }
        viewModelScope.launch {
            try {
                notifier.notifyNewContent(
                    "Nuevo contenido en MMConecta",
                    "Hay una receta nueva disponible en el catálogo."
                )
                mutableState.update { it.copy(busy = false, message = "Notificación enviada.", isError = false) }
            } catch (error: CancellationException) {
                throw error
            } catch (error: SecurityException) {
                mutableState.update { it.copy(busy = false, message = "No se pudo enviar: falta el permiso de notificaciones.", isError = true) }
            } catch (error: Exception) {
                mutableState.update { it.copy(busy = false, message = "No pudimos enviar la notificación. Vuelve a intentarlo.", isError = true) }
            }
        }
    }
}
