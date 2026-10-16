package cl.grupo11.mmconecta.viewmodel

import cl.grupo11.mmconecta.model.UserProfile
import org.junit.Assert.*
import org.junit.Test
import org.junit.Before
import org.junit.After
import cl.grupo11.mmconecta.repository.ProfileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.test.resetMain

private class FakeProfileRepository(var profile: UserProfile? = null) : ProfileRepository {
    var fail = false
    override suspend fun load(): UserProfile? {
        if (fail) error("Read failed")
        return profile
    }
    override suspend fun save(profile: UserProfile) {
        if (fail) error("Write failed")
        this.profile = profile
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
class ProfileViewModelTest {
    @Before fun setup() { Dispatchers.setMain(UnconfinedTestDispatcher()) }
    @After fun cleanup() { Dispatchers.resetMain() }
    private val valid = UserProfile("Ana Demo", "ana@example.com", "Pastelería", setOf("Panadería"))

    @Test fun emptyProfileReportsAllRequiredFields() {
        val errors = validateProfile(UserProfile())
        assertNotNull(errors.name)
        assertNotNull(errors.email)
        assertNotNull(errors.business)
        assertNotNull(errors.interests)
        assertFalse(errors.isValid)
    }

    @Test fun rejectsMalformedEmails() {
        listOf("ana", "ana@", "a@@example.com", "ana@example", "a b@example.com", "a@example..com")
            .forEach { assertNotNull(it, validateProfile(valid.copy(email = it)).email) }
        assertTrue(validateProfile(valid.copy(email = "ana.demo+curso@example.cl")).isValid)
    }

    @Test fun validatesNameBoundsAndKnownOptions() {
        assertNotNull(validateProfile(valid.copy(name = "  ")).name)
        assertNotNull(validateProfile(valid.copy(name = "A")).name)
        assertNotNull(validateProfile(valid.copy(name = "A".repeat(61))).name)
        assertTrue(validateProfile(valid.copy(name = "Ál")).isValid)
        assertNotNull(validateProfile(valid.copy(business = "Desconocido")).business)
        assertNotNull(validateProfile(valid.copy(interests = setOf("Desconocido"))).interests)
    }

    @Test fun savesNormalizedProfileAndKeepsLastValidSaveOnError() {
        val vm = ProfileViewModel(FakeProfileRepository())
        vm.changeName(" Ana Demo ")
        vm.changeEmail(" ana@example.com ")
        vm.selectBusiness("Pastelería")
        vm.toggleInterest("Panadería")
        vm.save()
        assertEquals(valid, vm.uiState.value.savedProfile)
        assertTrue(vm.uiState.value.saved)
        vm.changeEmail("incorrecto")
        assertFalse(vm.uiState.value.saved)
        assertNotNull(vm.uiState.value.errors.email)
        vm.save()
        assertEquals(valid, vm.uiState.value.savedProfile)
        assertFalse(vm.uiState.value.saved)
        vm.changeEmail("ana@example.com")
        assertNull(vm.uiState.value.errors.email)
    }

    @Test fun interestCanBeSelectedAndRemoved() {
        val vm = ProfileViewModel(FakeProfileRepository())
        vm.toggleInterest("Panadería")
        vm.toggleInterest("Pastelería")
        assertEquals(setOf("Panadería", "Pastelería"), vm.uiState.value.draft.interests)
        vm.toggleInterest("Panadería")
        assertEquals(setOf("Pastelería"), vm.uiState.value.draft.interests)
        vm.toggleInterest("Desconocido")
        assertEquals(setOf("Pastelería"), vm.uiState.value.draft.interests)
    }

    @Test fun newViewModelLoadsLastSavedProfile() {
        val repository = FakeProfileRepository(valid)
        val first = ProfileViewModel(repository)
        first.changeName("Ana Actualizada")
        first.save()
        val reopened = ProfileViewModel(repository)
        assertEquals("Ana Actualizada", reopened.uiState.value.draft.name)
        assertEquals(valid.email, reopened.uiState.value.draft.email)
        assertFalse(reopened.uiState.value.busy)
    }

    @Test fun failedWriteDoesNotReportSuccessAndCanRetry() {
        val repository = FakeProfileRepository(valid)
        val vm = ProfileViewModel(repository)
        vm.changeName("Ana Nueva")
        repository.fail = true
        vm.save()
        assertFalse(vm.uiState.value.saved)
        assertNotNull(vm.uiState.value.storageError)
        assertEquals(valid, repository.profile)
        repository.fail = false
        vm.save()
        assertTrue(vm.uiState.value.saved)
        assertNull(vm.uiState.value.storageError)
        assertEquals("Ana Nueva", repository.profile?.name)
    }

    @Test fun loadFailureIsVisibleAndDoesNotLockForm() {
        val repository = FakeProfileRepository().apply { fail = true }
        val vm = ProfileViewModel(repository)
        assertNotNull(vm.uiState.value.storageError)
        assertFalse(vm.uiState.value.busy)
        assertFalse(vm.uiState.value.saved)
    }
}
