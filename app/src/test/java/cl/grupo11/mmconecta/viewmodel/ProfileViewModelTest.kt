package cl.grupo11.mmconecta.viewmodel

import cl.grupo11.mmconecta.model.UserProfile
import org.junit.Assert.*
import org.junit.Test

class ProfileViewModelTest {
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
        val vm = ProfileViewModel()
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
        val vm = ProfileViewModel()
        vm.toggleInterest("Panadería")
        vm.toggleInterest("Pastelería")
        assertEquals(setOf("Panadería", "Pastelería"), vm.uiState.value.draft.interests)
        vm.toggleInterest("Panadería")
        assertEquals(setOf("Pastelería"), vm.uiState.value.draft.interests)
        vm.toggleInterest("Desconocido")
        assertEquals(setOf("Pastelería"), vm.uiState.value.draft.interests)
    }
}
