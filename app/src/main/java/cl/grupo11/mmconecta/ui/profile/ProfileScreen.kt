package cl.grupo11.mmconecta.ui.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
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
    onBack: () -> Unit
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
            item { Text("Prueba con datos ficticios. Todos los campos son obligatorios.") }
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
                Text("Tipo de negocio", style = MaterialTheme.typography.titleMedium)
                businessOptions.forEach { option ->
                    FilterChip(enabled = !state.busy, selected = state.draft.business == option,
                        onClick = { onBusinessSelected(option) }, label = { Text(option) })
                }
                state.errors.business?.let { Text(it, color = MaterialTheme.colorScheme.error) }
            }
            item {
                Text("Tus intereses", style = MaterialTheme.typography.titleMedium)
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
                state.storageError?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                if (state.saved) Text("Perfil guardado en este dispositivo.", color = MaterialTheme.colorScheme.primary)
                else if (state.submitted && !state.errors.isValid) {
                    Text("Revisa los campos indicados arriba.", color = MaterialTheme.colorScheme.error)
                }
                Text("Los cambios se conservan al cerrar la app después de pulsar Guardar perfil.",
                    style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
