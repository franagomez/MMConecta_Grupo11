package cl.grupo11.mmconecta.viewmodel

import android.graphics.Bitmap
import cl.grupo11.mmconecta.repository.ProfilePhotoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.test.resetMain
import org.junit.*
import org.junit.Assert.*

@OptIn(ExperimentalCoroutinesApi::class)
class ProfilePhotoViewModelTest {
    @Before fun setup() { Dispatchers.setMain(UnconfinedTestDispatcher()) }
    @After fun cleanup() { Dispatchers.resetMain() }

    @Test fun cancelledCaptureDoesNotWriteOrClaimSuccess() {
        var writes = 0
        val repository = object : ProfilePhotoRepository {
            override suspend fun load(): Bitmap? = null
            override suspend fun save(photo: Bitmap) { writes++ }
        }
        val vm = ProfilePhotoViewModel(repository)
        vm.onCameraResult(null)
        assertEquals(0, writes)
        assertFalse(vm.uiState.value.busy)
        assertFalse(vm.uiState.value.isError)
        assertEquals("No se recibió una imagen. Se conserva la anterior.", vm.uiState.value.message)
        vm.cameraUnavailable()
        assertTrue(vm.uiState.value.isError)
    }

    @Test fun failedReadAllowsTakingAnotherPicture() {
        val repository = object : ProfilePhotoRepository {
            override suspend fun load(): Bitmap? = error("Unreadable")
            override suspend fun save(photo: Bitmap) = Unit
        }
        val vm = ProfilePhotoViewModel(repository)
        assertFalse(vm.uiState.value.busy)
        assertTrue(vm.uiState.value.isError)
        assertNull(vm.uiState.value.photo)
    }
}
