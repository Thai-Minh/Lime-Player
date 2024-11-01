package com.example.all.video.downloader.utils.clipboard

import android.content.ClipData
import android.content.ClipboardManager
import android.view.ViewTreeObserver.OnWindowFocusChangeListener
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class ClipboardManager : DefaultLifecycleObserver, OnWindowFocusChangeListener {
    private lateinit var activity: ComponentActivity

    private var shouldCheckClipboard = true

    private val clipboardManager by lazy {
        ContextCompat.getSystemService(activity, ClipboardManager::class.java)
    }

    private val _clipboard = MutableSharedFlow<String>(1)
    val clipboard: Flow<String> get() = _clipboard

    fun attach(activity: ComponentActivity) {
        this.activity = activity
    }

    fun init() {

        activity.apply {
            lifecycle.addObserver(this@ClipboardManager)
            window.decorView.viewTreeObserver.addOnWindowFocusChangeListener(this@ClipboardManager)
        }
    }

    fun clear() {
        activity.apply {
            lifecycle.removeObserver(this@ClipboardManager)
            window.decorView.viewTreeObserver.removeOnWindowFocusChangeListener(this@ClipboardManager)
        }
        shouldCheckClipboard = true
        updateClipboard("")
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        if (hasFocus && shouldCheckClipboard) {
            updateClipboard()
            shouldCheckClipboard = false
        }
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        shouldCheckClipboard = true
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)

        clear()
    }

    fun clearClipboard() {
        clipboardManager?.setPrimaryClip(ClipData.newPlainText("", null))
        updateClipboard("")
    }

    private fun updateClipboard() {
        val clipBoard = clipboardManager?.getClipBoard() ?: return
        updateClipboard(clipBoard)
    }

    private fun updateClipboard(text: String) {
        _clipboard.tryEmit(text)
    }

    private fun ClipboardManager.getClipBoard(): String? {
        if (primaryClip != null && (primaryClip?.itemCount ?: -1) > 0) {
            return primaryClip?.getItemAt(0)?.text?.toString()?.getUrl()
        }

        return null
    }

    private fun String.getUrl(): String? {
        return Regex("""(https?://\S+)""").find(this)?.value
    }

}