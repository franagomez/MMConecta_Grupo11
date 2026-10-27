package cl.grupo11.mmconecta.repository

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import cl.grupo11.mmconecta.model.UserProfile
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LocalProfileRepositoryTest {
    @Test fun profileSurvivesRepositoryRecreationAndUpdates() = runBlocking {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val name = "profile_test"
        val preferences = context.getSharedPreferences(name, Context.MODE_PRIVATE)
        preferences.edit().clear().commit()
        try {
            val first = LocalProfileRepository(context, name)
            assertNull(first.load())
            val profile = UserProfile("Ana Demo", "ana@example.com", "Pastelería", setOf("Panadería", "Chocolatería"))
            first.save(profile)
            val reopened = LocalProfileRepository(context, name)
            assertEquals(profile, reopened.load())
            val updated = profile.copy(name = "Ana Nueva", interests = setOf("Pastelería"))
            reopened.save(updated)
            assertEquals(updated, LocalProfileRepository(context, name).load())
        } finally {
            preferences.edit().clear().commit()
        }
    }
}
