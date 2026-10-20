package cl.grupo11.mmconecta.ui.home

import cl.grupo11.mmconecta.viewmodel.HomeUiState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    state: HomeUiState,
    onCategorySelected: (String) -> Unit,
    onContentClick: (String) -> Unit
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
            items(state.contents, key = { it.id }) { content ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onContentClick(content.id) }
                ) {
                    Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(content.category, color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.labelLarge)
                        Text(content.title, style = MaterialTheme.typography.titleLarge)
                        Text(content.description, style = MaterialTheme.typography.bodyLarge)
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