package cl.grupo11.mmconecta.ui.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.Image
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.animation.core.tween
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import cl.grupo11.mmconecta.viewmodel.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    state: ProfileUiState,
    onNameChanged: (String) -> Unit,
    onEmailChanged: (String) -> Unit,
    onBusinessSelected: (String) -> Unit,
    onInterestSelected: (String) -> Unit,
    onSave: () -> Unit,
    onBack: () -> Unit,
    photoState: ProfilePhotoUiState,
    onTakePhoto: () -> Unit
) {
    Scaffold(topBar = {
        TopAppBar(title = { Text("Mi perfil") },
            navigationIcon = { TextButton(onClick = onBack) { Text("Volver") } })
    }) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding).imePadding(),
            contentPadding = PaddingValues(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text("Imagen de prueba (opcional)", style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.semantics { heading() })
                photoState.photo?.let {
                    Image(bitmap = it.asImageBitmap(), contentDescription = "Imagen guardada del perfil",
                        modifier = Modifier.size(120.dp), contentScale = ContentScale.Fit)
                }
                OutlinedButton(enabled = !photoState.busy, onClick = onTakePhoto) {
                    Text(if (photoState.photo == null) "Tomar imagen de prueba" else "Cambiar imagen")
                }
                if (photoState.busy) LinearProgressIndicator(Modifier.fillMaxWidth())
                ProfileFeedback(photoState.message, photoState.isError)
                Text("Usa un objeto de prueba sin datos personales. La imagen se guarda al confirmar la captura.",
                    style = MaterialTheme.typography.bodySmall)
            }
            item { Text("Prueba con datos ficticios. Los campos siguientes son obligatorios.") }
            item {
                OutlinedTextField(value = state.draft.name, onValueChange = onNameChanged,
                    enabled = !state.busy, label = { Text("Nombre") }, singleLine = true, modifier = Modifier.fillMaxWidth(),
                    isError = state.errors.name != null,
                    supportingText = { state.errors.name?.let { Text(it) } })
            }
            item {
                OutlinedTextField(value = state.draft.email, onValueChange = onEmailChanged,
                    enabled = !state.busy, label = { Text("Correo") }, singleLine = true, modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    isError = state.errors.email != null,
                    supportingText = { state.errors.email?.let { Text(it) } })
            }
            item {
                Text("Tipo de negocio", style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.semantics { heading() })
                businessOptions.forEach { option ->
                    FilterChip(enabled = !state.busy, selected = state.draft.business == option,
                        onClick = { onBusinessSelected(option) }, label = { Text(option) })
                }
                state.errors.business?.let { Text(it, color = MaterialTheme.colorScheme.error) }
            }
            item {
                Text("Tus intereses", style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.semantics { heading() })
                Text("Puedes elegir más de uno.")
                interestOptions.forEach { option ->
                    FilterChip(enabled = !state.busy, selected = option in state.draft.interests,
                        onClick = { onInterestSelected(option) }, label = { Text(option) })
                }
                state.errors.interests?.let { Text(it, color = MaterialTheme.colorScheme.error) }
            }
            item {
                Button(enabled = !state.busy, onClick = onSave, modifier = Modifier.fillMaxWidth()) { Text("Guardar perfil") }
                if (state.busy) LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                val message = when {
                    state.busy -> "Procesando perfil…"
                    state.storageError != null -> state.storageError
                    state.saved -> "Perfil guardado en este dispositivo."
                    state.submitted && !state.errors.isValid -> "Revisa los campos indicados arriba."
                    else -> null
                }
                ProfileFeedback(message, state.storageError != null || (state.submitted && !state.errors.isValid))
                Text("Los cambios se conservan al cerrar la app después de pulsar Guardar perfil.",
                    style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

/** Mantiene el aviso visible; la transición solo acompaña el cambio de estado. */
@Composable
private fun ProfileFeedback(message: String?, isError: Boolean) {
    AnimatedContent(
        targetState = message to isError,
        modifier = Modifier.fillMaxWidth().semantics { liveRegion = LiveRegionMode.Polite },
        transitionSpec = { fadeIn(tween(180)) togetherWith fadeOut(tween(120)) },
        label = "Mensaje del perfil"
    ) { (text, error) ->
        if (text != null) {
            Text(
                text = text,
                color = if (error) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
    }
}
