package cl.grupo11.mmconecta.viewmodel

import androidx.lifecycle.ViewModel
import cl.grupo11.mmconecta.repository.ContentRepository
import cl.grupo11.mmconecta.repository.DemoContentRepository
import cl.grupo11.mmconecta.model.LearningContent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class HomeUiState(
    val categories: List<String> = emptyList(),
    val selectedCategory: String = "Todos",
    val contents: List<LearningContent> = emptyList()
)

class HomeViewModel(
    repository: ContentRepository = DemoContentRepository()
) : ViewModel() {
    private val allContents = repository.getContents()
    private val mutableState = MutableStateFlow(HomeUiState(
        categories = listOf("Todos") + allContents.map { it.category }.distinct(),
        contents = allContents
    ))
    val uiState = mutableState.asStateFlow()

    fun findContent(id: String?): LearningContent? = allContents.find { it.id == id }

    fun selectCategory(category: String) {
        if (category !in uiState.value.categories) return
        mutableState.update {
            it.copy(selectedCategory = category, contents = allContents.filter { content ->
                category == "Todos" || content.category == category
            })
        }
    }
}
