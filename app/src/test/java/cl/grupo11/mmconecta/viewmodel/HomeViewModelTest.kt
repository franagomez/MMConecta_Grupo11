package cl.grupo11.mmconecta.viewmodel

import cl.grupo11.mmconecta.repository.FavoritesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.test.resetMain
import org.junit.*
import org.junit.Assert.*

private class FakeFavorites : FavoritesRepository {
    var ids = emptySet<String>()
    var fail = false
    override suspend fun load(): Set<String> {
        if (fail) error("Read failed")
        return ids
    }
    override suspend fun save(ids: Set<String>) {
        if (fail) error("Write failed")
        this.ids = ids
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {
    @Before fun setup() { Dispatchers.setMain(UnconfinedTestDispatcher()) }
    @After fun cleanup() { Dispatchers.resetMain() }

    @Test fun favoritesSurviveNewViewModelAndCombineWithCategory() {
        val repository = FakeFavorites()
        val vm = HomeViewModel(repository)
        val first = vm.uiState.value.contents.first()
        vm.toggleFavorite(first.id)
        val reopened = HomeViewModel(repository)
        reopened.showFavorites(true)
        assertEquals(listOf(first), reopened.uiState.value.contents)
        val anotherCategory = reopened.uiState.value.categories.first { it != "Todos" && it != first.category }
        reopened.selectCategory(anotherCategory)
        assertTrue(reopened.uiState.value.contents.isEmpty())
        reopened.selectCategory("Todos")
        assertEquals(listOf(first), reopened.uiState.value.contents)
        reopened.toggleFavorite(first.id)
        assertTrue(reopened.uiState.value.contents.isEmpty())
        assertTrue(repository.ids.isEmpty())
        reopened.showFavorites(false)
        assertTrue(reopened.uiState.value.contents.isNotEmpty())
    }

    @Test fun failedWritePreservesSelectionAndRetrySucceeds() {
        val repository = FakeFavorites()
        val vm = HomeViewModel(repository)
        val id = vm.uiState.value.contents.first().id
        repository.fail = true
        vm.toggleFavorite(id)
        assertTrue(vm.uiState.value.favoriteIds.isEmpty())
        assertNotNull(vm.uiState.value.error)
        assertFalse(vm.uiState.value.favoritesBusy)
        repository.fail = false
        vm.toggleFavorite(id)
        assertEquals(setOf(id), vm.uiState.value.favoriteIds)
        assertNull(vm.uiState.value.error)
    }

    @Test fun failedLoadCannotOverwriteExistingFavorites() {
        val repository = FakeFavorites().apply { fail = true }
        val vm = HomeViewModel(repository)
        vm.toggleFavorite(vm.uiState.value.contents.first().id)
        assertFalse(vm.uiState.value.favoritesLoaded)
        assertNotNull(vm.uiState.value.error)
        repository.fail = false
        vm.loadFavorites()
        assertTrue(vm.uiState.value.favoritesLoaded)
        assertNull(vm.uiState.value.error)
    }

    @Test fun unknownIdsAndCategoriesDoNotChangeCatalog() {
        val repository = FakeFavorites().apply { ids = setOf("removed-content") }
        val vm = HomeViewModel(repository)
        val initial = vm.uiState.value.contents
        vm.toggleFavorite("unknown")
        vm.selectCategory("unknown")
        assertEquals(initial, vm.uiState.value.contents)
        assertTrue(vm.uiState.value.favoriteIds.isEmpty())
    }
}
