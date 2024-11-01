package com.example.limeplayer.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Activity.OVERRIDE_TRANSITION_CLOSE
import android.app.Activity.OVERRIDE_TRANSITION_OPEN
import android.content.Context
import android.content.ContextWrapper
import android.content.res.Resources
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.text.format.DateUtils
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.example.limeplayer.R
import java.net.URI
import java.text.Normalizer
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.regex.Pattern

fun Context.getActivity(): AppCompatActivity {
    var currentContext = this
    while (currentContext is ContextWrapper) {
        if (currentContext is AppCompatActivity) {
            return currentContext
        }
        currentContext = currentContext.baseContext
    }


    throw IllegalAccessException("Can't get activity!!!!")
}

fun Long.toTimeString(): String {
    val minUnit = 60 * 1000
    val minutes = this / minUnit
    val seconds = (this - minutes * minUnit) / 1000
    return "%02d:%02d".format(minutes, seconds)
}

fun ComponentActivity.hideSystemUI() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        window.setDecorFitsSystemWindows(false)
        window.insetsController?.let {
            it.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            it.hide(android.view.WindowInsets.Type.statusBars())
        }
    } else {
        window.decorView.systemUiVisibility
        (View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_IMMERSIVE or View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION)
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
    }
}

fun ComponentActivity.showSystemUI() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        window.setDecorFitsSystemWindows(false)
        window.insetsController?.show(android.view.WindowInsets.Type.systemBars())
    } else {
        window.decorView.systemUiVisibility =
            (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    }
}

fun Activity.finishActivityFadeOutAnimation() {
    finish()
    setAnimationTransition(isStartActivity = false)
}

fun Activity.setStartActivityFadeInAnimation() {
    setAnimationTransition(isStartActivity = true)
}

@SuppressLint("WrongConstant")
private fun Activity.setAnimationTransition(isStartActivity: Boolean) {

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
        val overrideType = if (isStartActivity)
            OVERRIDE_TRANSITION_OPEN
        else
            OVERRIDE_TRANSITION_CLOSE

        overrideActivityTransition(
            overrideType,
            R.anim.fade_in_anim,
            R.anim.fade_out_anim
        )
    } else {
        overridePendingTransition(R.anim.fade_in_anim, R.anim.fade_out_anim)
    }
}

fun Long.toDateStr(): String {
    val date = Date(this)
    val dateFormat = SimpleDateFormat("yyyy:MM:dd", Locale.ENGLISH)
    return dateFormat.format(date)
}

fun String.toDate(): Date? = SimpleDateFormat("yyyy:MM:dd", Locale.ENGLISH).parse(this)

fun Date.isYesterday(): Boolean = DateUtils.isToday(this.time + DateUtils.DAY_IN_MILLIS)

fun Date.isToday(): Boolean = DateUtils.isToday(this.time)

fun Date.formatWithPattern(pattern: String): String {
    return SimpleDateFormat(pattern, Locale.ENGLISH).format(this)
}

fun String.toStandard(): String {
    return this.toIPA().lowercase()
}

fun String.toIPA(): String {
    val nfdNormalizedString: String = Normalizer.normalize(this, Normalizer.Form.NFD)
    val pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+")
    return pattern.matcher(nfdNormalizedString).replaceAll("")
}

fun Context.isInternetAvailable(): Boolean {
    var result = false
    val connectivityManager =
        applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val networkCapabilities = connectivityManager.activeNetwork ?: return false
        val actNw = connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
        result = when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    } else {
        connectivityManager.run {
            connectivityManager.activeNetworkInfo?.run {
                result = when (type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
    }
    return result
}

fun Context.dp2Px(dp: Int): Float {
    return dp * resources.displayMetrics.density
}

fun Context.px2Dp(px: Int): Float {
    return px / (resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}

fun Int.px2Dp(): Float {
    return this / (Resources.getSystem().displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}

@SuppressLint("SimpleDateFormat")
fun getSimpleDateFormat(): String {
    val currentDate = Date()
    val dateFormat = SimpleDateFormat("dd:MM:yyyy")

    return dateFormat.format(currentDate)
}

fun String.getDomainName(): String? {
    return try {
        val domain = URI(this).host

        return if (domain.startsWith("www.")) domain.substring(4) else domain
    } catch (ex: Exception) {
        null
    }
}

fun String.getExtension(): String {
    return this.substringAfterLast(".")
}