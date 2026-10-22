package cl.grupo11.mmconecta.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.grupo11.mmconecta.repository.ProfileRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.CancellationException
import cl.grupo11.mmconecta.model.UserProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

val businessOptions = listOf("Panadería", "Pastelería", "Chocolatería", "Otro emprendimiento")
val interestOptions = listOf("Panadería", "Pastelería", "Chocolatería")

data class ProfileErrors(
    val name: String? = null,
    val email: String? = null,
    val business: String? = null,
    val interests: String? = null
) {
    val isValid: Boolean get() = listOf(name, email, business, interests).all { it == null }
}

fun validateProfile(profile: UserProfile): ProfileErrors = ProfileErrors(
    name = if (profile.name.trim().length in 2..60) null else "Escribe un nombre de 2 a 60 caracteres.",
    email = if (profile.email.trim().length <= 254 &&
        Regex("^[^\\s@]+@[^\\s@.]+(?:\\.[^\\s@.]+)+$").matches(profile.email.trim())) null
        else "Escribe un correo válido, por ejemplo ana@example.com.",
    business = if (profile.business in businessOptions) null else "Selecciona un tipo de negocio.",
    interests = if (profile.interests.isNotEmpty() && profile.interests.all { it in interestOptions }) null
        else "Selecciona al menos un interés."
)

data class ProfileUiState(
    val draft: UserProfile = UserProfile(),
    val savedProfile: UserProfile? = null,
    val errors: ProfileErrors = ProfileErrors(),
    val submitted: Boolean = false,
    val saved: Boolean = false,
    val busy: Boolean = true,
    val storageError: String? = null
)

class ProfileViewModel(private val repository: ProfileRepository) : ViewModel() {
    private val mutableState = MutableStateFlow(ProfileUiState())
    val uiState = mutableState.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                val profile = repository.load()
                mutableState.update { it.copy(draft = profile ?: UserProfile(), savedProfile = profile, busy = false) }
            } catch (error: CancellationException) {
                throw error
            } catch (error: Exception) {
                mutableState.update { it.copy(busy = false, storageError = "No pudimos cargar el perfil. Vuelve a abrir la app para intentarlo de nuevo.") }
            }
        }
    }

    private fun edit(transform: (UserProfile) -> UserProfile) {
        if (uiState.value.busy) return
        mutableState.update {
            val draft = transform(it.draft)
            it.copy(draft = draft, saved = false,
                errors = if (it.submitted) validateProfile(draft) else ProfileErrors())
        }
    }

    fun changeName(value: String) = edit { it.copy(name = value) }
    fun changeEmail(value: String) = edit { it.copy(email = value) }
    fun selectBusiness(value: String) = edit { it.copy(business = value) }
    fun toggleInterest(value: String) {
        if (value !in interestOptions) return
        edit { it.copy(interests = if (value in it.interests) it.interests - value else it.interests + value) }
    }

    fun save() {
        if (uiState.value.busy) return
        val profile = uiState.value.draft.let { it.copy(name = it.name.trim(), email = it.email.trim()) }
        val errors = validateProfile(profile)
        mutableState.update { it.copy(draft = profile, errors = errors, submitted = true, saved = false) }
        if (!errors.isValid) return
        mutableState.update { it.copy(busy = true, storageError = null) }
        viewModelScope.launch {
            try {
                repository.save(profile)
                mutableState.update { it.copy(savedProfile = profile, saved = true, busy = false) }
            } catch (error: CancellationException) {
                throw error
            } catch (error: Exception) {
                mutableState.update { it.copy(busy = false, storageError = "No pudimos guardar el perfil. Inténtalo otra vez.") }
            }
        }
    }
}
