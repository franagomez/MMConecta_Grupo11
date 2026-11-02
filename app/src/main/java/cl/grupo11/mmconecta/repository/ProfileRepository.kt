package cl.grupo11.mmconecta.repository

import android.content.Context
import cl.grupo11.mmconecta.model.UserProfile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

interface ProfileRepository {
    suspend fun load(): UserProfile?
    suspend fun save(profile: UserProfile)
}

class LocalProfileRepository(context: Context, preferencesName: String = "profile") : ProfileRepository {
    private val preferences = context.applicationContext.getSharedPreferences(preferencesName, Context.MODE_PRIVATE)

    override suspend fun load(): UserProfile? = withContext(Dispatchers.IO) {
        if (!preferences.contains("name")) null else UserProfile(
            name = preferences.getString("name", "").orEmpty(),
            email = preferences.getString("email", "").orEmpty(),
            business = preferences.getString("business", "").orEmpty(),
            interests = preferences.getStringSet("interests", emptySet()).orEmpty().toSet()
        )
    }

    override suspend fun save(profile: UserProfile) = withContext(Dispatchers.IO) {
        val written = preferences.edit()
            .putString("name", profile.name)
            .putString("email", profile.email)
            .putString("business", profile.business)
            .putStringSet("interests", profile.interests.toSet())
            .commit()
        if (!written) throw IOException("No se pudo guardar el perfil")
    }
}
