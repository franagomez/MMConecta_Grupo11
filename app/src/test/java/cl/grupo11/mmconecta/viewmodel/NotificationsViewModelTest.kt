package cl.grupo11.mmconecta.viewmodel

import cl.grupo11.mmconecta.repository.ContentNotifier
import cl.grupo11.mmconecta.repository.NotificationsBlockedException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.test.resetMain
import org.junit.*
import org.junit.Assert.*

@OptIn(ExperimentalCoroutinesApi::class)
class NotificationsViewModelTest {
    @Before fun setup() { Dispatchers.setMain(UnconfinedTestDispatcher()) }
    @After fun cleanup() { Dispatchers.resetMain() }

    @Test fun blockedNotificationsShowErrorAndAllowRetry() {
        var blocked = true
        val notifier = object : ContentNotifier {
            override fun notifyNewContent(title: String, message: String) {
                if (blocked) throw NotificationsBlockedException()
            }
        }
        val vm = NotificationsViewModel(notifier)
        vm.sendTestNotification()
        assertTrue(vm.uiState.value.isError)
        assertFalse(vm.uiState.value.busy)
        assertTrue(vm.uiState.value.message!!.contains("desactivadas"))
        blocked = false
        vm.sendTestNotification()
        assertFalse(vm.uiState.value.isError)
        assertEquals("Notificación enviada.", vm.uiState.value.message)
    }

    @Test fun missingPermissionDoesNotClaimSuccess() {
        val vm = NotificationsViewModel(object : ContentNotifier {
            override fun notifyNewContent(title: String, message: String) {
                throw SecurityException()
            }
        })
        vm.sendTestNotification()
        assertTrue(vm.uiState.value.isError)
        assertFalse(vm.uiState.value.busy)
        assertTrue(vm.uiState.value.message!!.contains("permiso"))
    }
}
