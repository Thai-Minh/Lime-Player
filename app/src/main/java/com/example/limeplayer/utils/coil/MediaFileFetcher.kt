package com.example.limeplayer.utils.coil

import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.media.ThumbnailUtils
import android.os.Build
import android.util.Size
import android.webkit.MimeTypeMap
import coil.ImageLoader
import coil.decode.DataSource
import coil.decode.ImageSource
import coil.fetch.DrawableResult
import coil.fetch.FetchResult
import coil.fetch.Fetcher
import coil.fetch.SourceResult
import coil.request.Options
import com.example.filelock.data.readHeader
import com.example.filelock.share.FileLockerStream
import com.example.filelock.utils.LOCK_FOLDER
import com.example.limeplayer.BaseApplication
import com.example.limeplayer.utils.getExtension
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okio.buffer
import okio.source
import java.io.File

sealed class MediaImageLoader(open val path: String)

data class ImageThumb(
    override val path: String,
) : MediaImageLoader(path = path)

data class AudioThumb(
    override val path: String
) : MediaImageLoader(path = path)

data class VideoThumb(
    override val path: String
) : MediaImageLoader(path = path)

val MediaImageLoader.key: String
    get() {
        return when (this) {
            is ImageThumb -> this.path
            is AudioThumb -> this.path
            is VideoThumb -> this.path
            else -> throw IllegalArgumentException("Unknown MediaImageLoader type")
        }
    }

suspend fun loadAudioThumb(data: AudioThumb, options: Options): DrawableResult? =
    withContext(Dispatchers.IO) {
        return@withContext try {
            data.path.findAudioThumbnail()?.getDrawableResult(context = options.context)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

suspend fun loadImageThumb(data: ImageThumb, options: Options): FetchResult? =
    withContext(Dispatchers.IO) {
        return@withContext try {
            data.path.findImageThumbnail()?.getDrawableResult(context = options.context)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

suspend fun loadVideoThumb(data: VideoThumb, options: Options): FetchResult? =
    withContext(Dispatchers.IO) {

        var bitmap: Bitmap? = null

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            bitmap = exceptionWrapper {
                ThumbnailUtils.createVideoThumbnail(
                    File(data.path),
                    Size(500, 500),
                    null
                )
            }
        }

        if (bitmap == null) {
            try {
                val metadata = MediaMetadataRetriever()
                metadata.setDataSource(data.path)
                bitmap = metadata.getFrameAtTime(1000)

                metadata.release()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            if (bitmap != null) {
                bitmap = bitmap.scaleDown(500f, false)
            }
        }

        return@withContext bitmap?.getDrawableResult(context = options.context)
    }

fun loadPrivateImage(path: String): FetchResult {
    val file = File(path)

    val header = file.inputStream().use {
        it.readHeader()
    }

    val realPath = header?.path ?: file.absolutePath
    val source = FileLockerStream(path).source().buffer()

    return SourceResult(
        source = ImageSource(source, BaseApplication.application),
        mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(realPath.getExtension()),
        dataSource = DataSource.DISK
    )
}

class MediaFileFetcher(private val loader: MediaImageLoader, private val options: Options) :
    Fetcher {

    override suspend fun fetch(): FetchResult? {
        return if (loader.path.contains(LOCK_FOLDER.name)) {
            loadPrivateImage(loader.path)
        } else
            when (loader) {
                is AudioThumb -> loadAudioThumb(loader, options)
                is ImageThumb -> loadImageThumb(loader, options)
                is VideoThumb -> loadVideoThumb(loader, options)
            }
    }

    class Factory : Fetcher.Factory<MediaImageLoader> {
        override fun create(
            data: MediaImageLoader,
            options: Options,
            imageLoader: ImageLoader
        ): Fetcher {
            return MediaFileFetcher(data, options)
        }
    }
}

private fun <T> exceptionWrapper(block: () -> T): T? {
    return try {
        block()
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}