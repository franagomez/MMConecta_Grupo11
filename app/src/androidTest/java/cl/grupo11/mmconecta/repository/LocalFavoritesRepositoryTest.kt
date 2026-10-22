package cl.grupo11.mmconecta.repository

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LocalFavoritesRepositoryTest {
    @Test fun savesReopensAndRemovesFavorites() = runBlocking {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val name = "favorites_test"
        val preferences = context.getSharedPreferences(name, Context.MODE_PRIVATE)
        preferences.edit().clear().commit()
        try {
            val repository = LocalFavoritesRepository(context, name)
            assertTrue(repository.load().isEmpty())
            repository.save(setOf("demo-1", "demo-2"))
            val reopened = LocalFavoritesRepository(context, name)
            assertEquals(setOf("demo-1", "demo-2"), reopened.load())
            reopened.save(setOf("demo-2"))
            assertEquals(setOf("demo-2"), LocalFavoritesRepository(context, name).load())
            reopened.save(emptySet())
            assertTrue(LocalFavoritesRepository(context, name).load().isEmpty())
        } finally {
            preferences.edit().clear().commit()
        }
    }
}
