package cl.grupo11.mmconecta.ui.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cl.grupo11.mmconecta.model.LearningContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentDetailScreen(content: LearningContent?, onBack: () -> Unit) {
    Scaffold(topBar = {
        TopAppBar(title = { Text("Contenido") }, navigationIcon = {
            TextButton(onClick = onBack) { Text("Volver") }
        })
    }) { padding ->
        if (content == null) {
            Box(Modifier.fillMaxSize().padding(padding), contentAlignment = androidx.compose.ui.Alignment.Center) {
                Text("No se encontró el contenido.")
            }
            return@Scaffold
        }
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { Text(content.category, color = MaterialTheme.colorScheme.primary) }
            item { Text(content.title, style = MaterialTheme.typography.headlineMedium) }
            item { Text(content.description, style = MaterialTheme.typography.bodyLarge) }
            item { Text("Para comenzar", style = MaterialTheme.typography.titleLarge) }
            itemsIndexed(content.steps) { index, step ->
                Text("${index + 1}. $step", style = MaterialTheme.typography.bodyLarge)
            }
            item {
                Text("Contenido de demostración para el prototipo académico. No corresponde a material oficial de Master Martini.",
                    style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
