package com.example.tankr.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Blue60,
    onPrimary = Color.White,
    primaryContainer = Blue20,
    onPrimaryContainer = Blue90,
    secondary = Cyan60,
    onSecondary = Color.White,
    tertiary = Blue80,
    background = SurfaceDark,
    onBackground = Blue95,
    surface = SurfaceDark,
    onSurface = Blue95,
    surfaceVariant = Blue20,
    onSurfaceVariant = Blue90
)

private val LightColorScheme = lightColorScheme(
    primary = Blue50,
    onPrimary = Color.White,
    primaryContainer = Blue90,
    onPrimaryContainer = Blue10,
    secondary = Cyan40,
    onSecondary = Color.White,
    tertiary = Blue40,
    background = Surface,
    onBackground = OnSurface,
    surface = Color.White,
    onSurface = OnSurface,
    surfaceVariant = Blue95,
    onSurfaceVariant = OnSurfaceVariant
)

@Composable
fun TankrTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
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

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}