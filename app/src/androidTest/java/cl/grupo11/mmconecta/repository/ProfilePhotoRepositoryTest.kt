package cl.grupo11.mmconecta.repository

import android.graphics.Bitmap
import android.graphics.Color
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File

@RunWith(AndroidJUnit4::class)
class ProfilePhotoRepositoryTest {
    @Test fun imageSurvivesReopeningAndReplacement() = runBlocking {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val directory = File(context.cacheDir, "photo-test-${System.nanoTime()}").apply { mkdirs() }
        try {
            val repository = LocalProfilePhotoRepository(directory)
            assertNull(repository.load())
            val image = Bitmap.createBitmap(8, 8, Bitmap.Config.ARGB_8888).apply { eraseColor(Color.RED) }
            repository.save(image)
            val restored = LocalProfilePhotoRepository(directory).load()!!
            assertEquals(8, restored.width)
            assertEquals(Color.RED, restored.getPixel(0, 0))
            val replacement = Bitmap.createBitmap(4, 4, Bitmap.Config.ARGB_8888).apply { eraseColor(Color.BLUE) }
            repository.save(replacement)
            val updated = LocalProfilePhotoRepository(directory).load()!!
            assertEquals(4, updated.width)
            assertEquals(Color.BLUE, updated.getPixel(0, 0))
        } finally {
            directory.deleteRecursively()
        }
    }
}
