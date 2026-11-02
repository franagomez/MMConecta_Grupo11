package cl.grupo11.mmconecta.repository

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.AtomicFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException

interface ProfilePhotoRepository {
    suspend fun load(): Bitmap?
    suspend fun save(photo: Bitmap)
}

class LocalProfilePhotoRepository(directory: File) : ProfilePhotoRepository {
    private val photoFile = AtomicFile(File(directory, "profile-photo.png"))

    override suspend fun load(): Bitmap? = withContext(Dispatchers.IO) {
        try {
            photoFile.openRead().use {
                BitmapFactory.decodeStream(it) ?: throw IOException("Imagen no válida")
            }
        } catch (error: FileNotFoundException) {
            null
        }
    }

    override suspend fun save(photo: Bitmap) = withContext(Dispatchers.IO) {
        val stream = photoFile.startWrite()
        try {
            if (!photo.compress(Bitmap.CompressFormat.PNG, 100, stream)) {
                throw IOException("No se pudo escribir la imagen")
            }
            photoFile.finishWrite(stream)
        } catch (error: Exception) {
            photoFile.failWrite(stream)
            throw error
        }
    }
}
