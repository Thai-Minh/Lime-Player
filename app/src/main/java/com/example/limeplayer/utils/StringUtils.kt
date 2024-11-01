package com.example.limeplayer.utils

import android.content.Context
import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import java.util.Locale

val LocalLocaleContext = compositionLocalOf<Context?> {
    error("locale not created")
}

@Composable
fun LocaleWrapper(content: @Composable () -> Unit) {
    val context = LocalContext.current
    val langCode by LanguageCode.collectAsStateWithLifecycle(initialValue = null)

    CompositionLocalProvider(
        LocalLocaleContext provides context.createConfig(langCode),
    ) {
        content()
    }
}

@Composable
fun stringResource(@StringRes id: Int, vararg formatArgs: Any): String {
    val localLocaleContext = LocalLocaleContext.current

    val string = localLocaleContext?.getString(id, *formatArgs)
    return string ?: ""
}

fun Context.createConfig(langCode: String?): Context? {
    if (langCode == null) return null

    val config = Configuration(resources.configuration)
    config.setLocale(Locale(langCode))

    return createConfigurationContext(config)
}