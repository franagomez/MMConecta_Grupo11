package cl.grupo11.mmconecta.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.grupo11.mmconecta.repository.FavoritesRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.CancellationException
import cl.grupo11.mmconecta.repository.ContentRepository
import cl.grupo11.mmconecta.repository.DemoContentRepository
import cl.grupo11.mmconecta.model.LearningContent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class HomeUiState(
    val categories: List<String> = emptyList(),
    val selectedCategory: String = "Todos",
    val contents: List<LearningContent> = emptyList(),
    val favoriteIds: Set<String> = emptySet(),
    val onlyFavorites: Boolean = false,
    val favoritesBusy: Boolean = true,
    val favoritesLoaded: Boolean = false,
    val error: String? = null
)

class HomeViewModel(
    private val favorites: FavoritesRepository,
    repository: ContentRepository = DemoContentRepository()
) : ViewModel() {
    private val allContents = repository.getContents()
    private val mutableState = MutableStateFlow(HomeUiState(
        categories = listOf("Todos") + allContents.map { it.category }.distinct(),
        contents = allContents
    ))
    val uiState = mutableState.asStateFlow()

    fun findContent(id: String?): LearningContent? = allContents.find { it.id == id }

    init { loadFavorites() }

    private fun filtered(state: HomeUiState): HomeUiState = state.copy(contents = allContents.filter {
        (state.selectedCategory == "Todos" || it.category == state.selectedCategory) &&
            (!state.onlyFavorites || it.id in state.favoriteIds)
    })

    fun selectCategory(category: String) {
        if (category !in uiState.value.categories) return
        mutableState.update { filtered(it.copy(selectedCategory = category)) }
    }

    fun showFavorites(onlyFavorites: Boolean) {
        mutableState.update { filtered(it.copy(onlyFavorites = onlyFavorites)) }
    }

    fun loadFavorites() {
        if (uiState.value.favoritesLoaded) return
        mutableState.update { it.copy(favoritesBusy = true, error = null) }
        viewModelScope.launch {
            try {
                val ids = favorites.load().intersect(allContents.map { it.id }.toSet())
                mutableState.update { filtered(it.copy(favoriteIds = ids, favoritesBusy = false, favoritesLoaded = true)) }
            } catch (error: CancellationException) {
                throw error
            } catch (error: Exception) {
                mutableState.update { it.copy(favoritesBusy = false, error = "No pudimos cargar tus favoritos. Inténtalo otra vez.") }
            }
        }
    }

    fun toggleFavorite(id: String) {
        val state = uiState.value
        if (state.favoritesBusy || !state.favoritesLoaded || findContent(id) == null) return
        val ids = if (id in state.favoriteIds) state.favoriteIds - id else state.favoriteIds + id
        mutableState.update { it.copy(favoritesBusy = true, error = null) }
        viewModelScope.launch {
            try {
                favorites.save(ids)
                mutableState.update { filtered(it.copy(favoriteIds = ids, favoritesBusy = false)) }
            } catch (error: CancellationException) {
                throw error
            } catch (error: Exception) {
                mutableState.update { it.copy(favoritesBusy = false, error = "No pudimos cambiar el favorito. Vuelve a pulsar el botón para reintentar.") }
            }
        }
    }
}
