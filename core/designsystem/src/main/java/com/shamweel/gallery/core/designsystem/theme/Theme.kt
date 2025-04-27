package com.shamweel.gallery.core.designsystem.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat


val DarkColorScheme = darkColorScheme(
    primary = Tertiary0,
    onPrimary = Tertiary50,
    primaryContainer = Tertiary10,
    onPrimaryContainer = Tertiary90,
    secondary = Secondary0,
    onSecondary = Secondary50,
    secondaryContainer = Secondary10,
    onSecondaryContainer = Secondary90,
    tertiary = Tertiary0,
    onTertiary = Tertiary50,
    tertiaryContainer = Tertiary10,
    onTertiaryContainer = Tertiary90,
    error = Error0,
    onError = Error50,
    errorContainer = Error30,
    onErrorContainer = Error90,
    background = Neutral0,
    onBackground = Neutral100,
    surface = Neutral10,
    onSurface = Neutral100,
    surfaceVariant = Neutral30,
    onSurfaceVariant = Neutral100,
    outline = Neutral50 ,
)

val LightColorScheme = lightColorScheme(
    primary = Tertiary40,
    onPrimary = Tertiary0,
    primaryContainer = Tertiary90,
    onPrimaryContainer = Tertiary10,

    secondary = Secondary40,
    onSecondary = Secondary0,
    secondaryContainer = Secondary90,
    onSecondaryContainer = Secondary10,

    tertiary = Tertiary_Variant40,
    onTertiary = Tertiary40,
    tertiaryContainer = Tertiary_Variant90,
    onTertiaryContainer = Tertiary_Variant10,

    error = Error40,
    onError = Error0,
    errorContainer = Error90,
    onErrorContainer = Error10,

    background = Neutral99,
    onBackground = Neutral10,

    surface = Neutral99,
    onSurface = Neutral10,

    surfaceVariant = Neutral90,
    onSurfaceVariant = Neutral30,

    outline = Neutral60,
)

/*val LightColorScheme = darkColorScheme(
    primary = Tertiary0,
    onPrimary = Tertiary50,
    primaryContainer = Tertiary10,
    onPrimaryContainer = Tertiary90,
    secondary = Secondary0,
    onSecondary = Secondary50,
    secondaryContainer = Secondary10,
    onSecondaryContainer = Secondary90,
    tertiary = Tertiary0,
    onTertiary = Tertiary50,
    tertiaryContainer = Tertiary10,
    onTertiaryContainer = Tertiary90,
    error = Error0,
    onError = Error50,
    errorContainer = Error30,
    onErrorContainer = Error90,
    background = Neutral0,
    onBackground = Neutral100,
    surface = Neutral10,
    onSurface = Neutral100,
    surfaceVariant = Neutral30,
    onSurfaceVariant = Neutral100,
    outline = Neutral50 ,
)*/

@Composable
fun GalleryTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}