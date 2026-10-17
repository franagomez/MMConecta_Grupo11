package cl.grupo11.mmconecta.repository

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

interface FavoritesRepository {
    suspend fun load(): Set<String>
    suspend fun save(ids: Set<String>)
}

class LocalFavoritesRepository(context: Context, name: String = "favorites") : FavoritesRepository {
    private val preferences = context.applicationContext.getSharedPreferences(name, Context.MODE_PRIVATE)
    override suspend fun load(): Set<String> = withContext(Dispatchers.IO) {
        preferences.getStringSet("content_ids", emptySet()).orEmpty().toSet()
    }
    override suspend fun save(ids: Set<String>) = withContext(Dispatchers.IO) {
        if (!preferences.edit().putStringSet("content_ids", ids.toSet()).commit()) {
            throw IOException("No se pudieron guardar los favoritos")
        }
    }
}
