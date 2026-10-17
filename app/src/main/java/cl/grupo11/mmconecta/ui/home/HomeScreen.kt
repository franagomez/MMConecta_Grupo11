package cl.grupo11.mmconecta.ui.home

import cl.grupo11.mmconecta.viewmodel.HomeUiState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(state: HomeUiState, onCategorySelected: (String) -> Unit, onContentSelected: (String) -> Unit, onProfileSelected: () -> Unit,
    onFavoriteSelected: (String) -> Unit,
    onFavoritesFilterChanged: (Boolean) -> Unit,
    onRetryFavorites: () -> Unit
) {
    Scaffold { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text("MMConecta", style = MaterialTheme.typography.headlineLarge)
                Text("Aprende, crea y conecta", style = MaterialTheme.typography.titleMedium)
                OutlinedButton(onClick = onProfileSelected) { Text("Mi perfil") }
            }
            item {
                Text("Conocimiento para tu negocio", style = MaterialTheme.typography.headlineSmall)
                Text("Explora contenidos de panadería, pastelería y chocolatería.")
            }
            item {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(state.categories) { category ->
                        FilterChip(
                            selected = category == state.selectedCategory,
                            onClick = { onCategorySelected(category) },
                            label = { Text(category) }
                        )
                    }
                }
            }
            item {
                FilterChip(selected = state.onlyFavorites,
                    onClick = { onFavoritesFilterChanged(!state.onlyFavorites) },
                    label = { Text("Mis favoritos") })
                if (state.favoritesBusy) LinearProgressIndicator(Modifier.fillMaxWidth())
                state.error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                if (!state.favoritesLoaded && !state.favoritesBusy) {
                    TextButton(onClick = onRetryFavorites) { Text("Reintentar carga") }
                }
                if (state.contents.isEmpty() && state.favoritesLoaded) {
                    Text(if (state.onlyFavorites) "No tienes favoritos en esta categoría. Puedes agregarlos desde el catálogo."
                        else "No hay contenidos en esta categoría.")
                }
            }
            items(state.contents, key = { it.id }) { content ->
                Card(onClick = { onContentSelected(content.id) }, modifier = Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(content.category, color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.labelLarge)
                        Text(content.title, style = MaterialTheme.typography.titleLarge)
                        Text(content.description, style = MaterialTheme.typography.bodyLarge)
                        OutlinedButton(
                            enabled = state.favoritesLoaded && !state.favoritesBusy,
                            onClick = { onFavoriteSelected(content.id) }
                        ) {
                            Text(if (content.id in state.favoriteIds) "Quitar de favoritos" else "Guardar en favoritos")
                        }
                    }
                }
            }
            item {
                Text("Prototipo académico · Grupo 11\nContenidos de demostración",
                    style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
