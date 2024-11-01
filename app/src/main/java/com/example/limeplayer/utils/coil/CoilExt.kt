package com.example.limeplayer.utils.coil

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.ThumbnailUtils
import android.os.Build
import android.util.Size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.core.graphics.drawable.toDrawable
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.decode.DataSource
import coil.fetch.DrawableResult
import coil.imageLoader
import coil.request.ImageRequest
import java.io.File
import kotlin.math.roundToInt

fun Bitmap.scaleDown(
    maxImageSize: Float, filter: Boolean
): Bitmap {
    val ratio = (maxImageSize / this.width).coerceAtMost(maxImageSize / this.height)
    val width = (ratio * this.width).roundToInt()
    val height = (ratio * this.height).roundToInt()
    return Bitmap.createScaledBitmap(
        this, width, height, filter
    )
}

fun Bitmap.getDrawableResult(context: Context): DrawableResult {
    return DrawableResult(
        drawable = this.toDrawable(context.resources),
        isSampled = false,
        dataSource = DataSource.MEMORY_CACHE
    )
}

fun String.findImageThumbnail(): Bitmap? {
    return findThumbnailForm {
        ThumbnailUtils.createImageThumbnail(File(this), Size(360, 360), null)
    }
}

fun String.findAudioThumbnail(): Bitmap? {
    return findThumbnailForm {
        ThumbnailUtils.createAudioThumbnail(File(this), Size(360, 360), null)
    }
}

private fun String.findThumbnailForm(getBitmap: () -> Bitmap? = { null }): Bitmap? {
    return try {

        var bitmap: Bitmap? = null

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                bitmap = getBitmap()
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

        if (bitmap == null) {
            bitmap = BitmapFactory.decodeFile(this)
        }

        bitmap

    } catch (ex: Exception) {
        null
    }
}


@Composable
fun rememberAsyncFileImagePainter(
    model: MediaImageLoader,
    placeholder: Painter? = null,
    error: Painter? = null,
    fallback: Painter? = error,
    onLoading: ((AsyncImagePainter.State.Loading) -> Unit)? = null,
    onSuccess: ((AsyncImagePainter.State.Success) -> Unit)? = null,
    onError: ((AsyncImagePainter.State.Error) -> Unit)? = null,
    contentScale: ContentScale = ContentScale.Fit,
    filterQuality: FilterQuality = DrawScope.DefaultFilterQuality,
): AsyncImagePainter {
    val context = LocalContext.current

    val request = remember(key1 = model.key) {
        ImageRequest.Builder(context)
            .data(model)
            .memoryCacheKey(key = model.key)
            .build()
    }

    return rememberAsyncImagePainter(
        model = request,
        imageLoader = LocalContext.current.imageLoader,
        placeholder = placeholder,
        error = error,
        fallback = fallback,
        onLoading = onLoading,
        onSuccess = onSuccess,
        onError = onError,
        contentScale = contentScale,
        filterQuality = filterQuality
    )
}