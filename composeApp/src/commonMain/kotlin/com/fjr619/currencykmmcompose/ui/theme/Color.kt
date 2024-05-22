package com.fjr619.currencykmmcompose.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme

val md_theme_light_primary = Color(0xFF00629D)
val md_theme_light_onPrimary = Color(0xFFFFFFFF)
val md_theme_light_primaryContainer = Color(0xFFCFE5FF)
val md_theme_light_onPrimaryContainer = Color(0xFF001D34)
val md_theme_light_secondary = Color(0xFF526070)
val md_theme_light_onSecondary = Color(0xFFFFFFFF)
val md_theme_light_secondaryContainer = Color(0xFFD5E4F7)
val md_theme_light_onSecondaryContainer = Color(0xFF0E1D2A)
val md_theme_light_tertiary = Color(0xFF3A5BA9)
val md_theme_light_onTertiary = Color(0xFFFFFFFF)
val md_theme_light_tertiaryContainer = Color(0xFFDAE2FF)
val md_theme_light_onTertiaryContainer = Color(0xFF001848)
val md_theme_light_error = Color(0xFFBA1A1A)
val md_theme_light_errorContainer = Color(0xFFFFDAD6)
val md_theme_light_onError = Color(0xFFFFFFFF)
val md_theme_light_onErrorContainer = Color(0xFF410002)
val md_theme_light_background = Color(0xFFFCFCFF)
val md_theme_light_onBackground = Color(0xFF1A1C1E)
val md_theme_light_surface = Color(0xFFFCFCFF)
val md_theme_light_onSurface = Color(0xFF1A1C1E)
val md_theme_light_surfaceVariant = Color(0xFFDEE3EB)
val md_theme_light_onSurfaceVariant = Color(0xFF42474E)
val md_theme_light_outline = Color(0xFF72777F)
val md_theme_light_inverseOnSurface = Color(0xFFF1F0F4)
val md_theme_light_inverseSurface = Color(0xFF2F3033)
val md_theme_light_inversePrimary = Color(0xFF99CBFF)
val md_theme_light_shadow = Color(0xFF000000)
val md_theme_light_surfaceTint = Color(0xFF00629D)
val md_theme_light_outlineVariant = Color(0xFFC2C7CF)
val md_theme_light_scrim = Color(0xFF000000)

val md_theme_dark_primary = Color(0xFF99CBFF)
val md_theme_dark_onPrimary = Color(0xFF003355)
val md_theme_dark_primaryContainer = Color(0xFF004A78)
val md_theme_dark_onPrimaryContainer = Color(0xFFCFE5FF)
val md_theme_dark_secondary = Color(0xFFB9C8DA)
val md_theme_dark_onSecondary = Color(0xFF243240)
val md_theme_dark_secondaryContainer = Color(0xFF3A4857)
val md_theme_dark_onSecondaryContainer = Color(0xFFD5E4F7)
val md_theme_dark_tertiary = Color(0xFFB2C5FF)
val md_theme_dark_onTertiary = Color(0xFF002B73)
val md_theme_dark_tertiaryContainer = Color(0xFF1E438F)
val md_theme_dark_onTertiaryContainer = Color(0xFFDAE2FF)
val md_theme_dark_error = Color(0xFFFFB4AB)
val md_theme_dark_errorContainer = Color(0xFF93000A)
val md_theme_dark_onError = Color(0xFF690005)
val md_theme_dark_onErrorContainer = Color(0xFFFFDAD6)
val md_theme_dark_background = Color(0xFF1A1C1E)
val md_theme_dark_onBackground = Color(0xFFE2E2E5)
val md_theme_dark_surface = Color(0xFF1A1C1E)
val md_theme_dark_onSurface = Color(0xFFE2E2E5)
val md_theme_dark_surfaceVariant = Color(0xFF42474E)
val md_theme_dark_onSurfaceVariant = Color(0xFFC2C7CF)
val md_theme_dark_outline = Color(0xFF8C9199)
val md_theme_dark_inverseOnSurface = Color(0xFF1A1C1E)
val md_theme_dark_inverseSurface = Color(0xFFE2E2E5)
val md_theme_dark_inversePrimary = Color(0xFF00629D)
val md_theme_dark_shadow = Color(0xFF000000)
val md_theme_dark_surfaceTint = Color(0xFF99CBFF)
val md_theme_dark_outlineVariant = Color(0xFF42474E)
val md_theme_dark_scrim = Color(0xFF000000)


val seed = Color(0xFF006B58)


val freshColor = Color(0xFF44FF78)
val staleColor = Color(0xFFFF9E44)
val errorColor = Color(0xFFEB4896)

val primaryColor
    @Composable
    get() = if (isSystemInDarkTheme()) Color(0xFF86A8FC)
    else Color(0xFF283556)

val headerColor
    @Composable
    get() = if (isSystemInDarkTheme()) Color(0xFF0C0C0C)
    else Color(0xFF283556)

val surfaceColor
    @Composable
    get() = if (isSystemInDarkTheme()) Color(0xFF161616)
    else Color(0xFFFFFFFF)

val textColor
    @Composable
    get() = if (isSystemInDarkTheme()) Color(0xFFFFFFFF)
    else Color(0xFF000000)

val LightColors = lightColorScheme(
    primary = md_theme_light_primary,
    onPrimary = md_theme_light_onPrimary,
    primaryContainer = md_theme_light_primaryContainer,
    onPrimaryContainer = md_theme_light_onPrimaryContainer,
    secondary = md_theme_light_secondary,
    onSecondary = md_theme_light_onSecondary,
    secondaryContainer = md_theme_light_secondaryContainer,
    onSecondaryContainer = md_theme_light_onSecondaryContainer,
    tertiary = md_theme_light_tertiary,
    onTertiary = md_theme_light_onTertiary,
    tertiaryContainer = md_theme_light_tertiaryContainer,
    onTertiaryContainer = md_theme_light_onTertiaryContainer,
    error = md_theme_light_error,
    errorContainer = md_theme_light_errorContainer,
    onError = md_theme_light_onError,
    onErrorContainer = md_theme_light_onErrorContainer,
    background = md_theme_light_background,
    onBackground = md_theme_light_onBackground,
    surface = md_theme_light_surface,
    onSurface = md_theme_light_onSurface,
    surfaceVariant = md_theme_light_surfaceVariant,
    onSurfaceVariant = md_theme_light_onSurfaceVariant,
    outline = md_theme_light_outline,
    inverseOnSurface = md_theme_light_inverseOnSurface,
    inverseSurface = md_theme_light_inverseSurface,
    inversePrimary = md_theme_light_inversePrimary,
    surfaceTint = md_theme_light_surfaceTint,
    outlineVariant = md_theme_light_outlineVariant,
    scrim = md_theme_light_scrim,
)

val DarkColors = darkColorScheme(
    primary = md_theme_dark_primary,
    onPrimary = md_theme_dark_onPrimary,
    primaryContainer = md_theme_dark_primaryContainer,
    onPrimaryContainer = md_theme_dark_onPrimaryContainer,
    secondary = md_theme_dark_secondary,
    onSecondary = md_theme_dark_onSecondary,
    secondaryContainer = md_theme_dark_secondaryContainer,
    onSecondaryContainer = md_theme_dark_onSecondaryContainer,
    tertiary = md_theme_dark_tertiary,
    onTertiary = md_theme_dark_onTertiary,
    tertiaryContainer = md_theme_dark_tertiaryContainer,
    onTertiaryContainer = md_theme_dark_onTertiaryContainer,
    error = md_theme_dark_error,
    errorContainer = md_theme_dark_errorContainer,
    onError = md_theme_dark_onError,
    onErrorContainer = md_theme_dark_onErrorContainer,
    background = md_theme_dark_background,
    onBackground = md_theme_dark_onBackground,
    surface = md_theme_dark_surface,
    onSurface = md_theme_dark_onSurface,
    surfaceVariant = md_theme_dark_surfaceVariant,
    onSurfaceVariant = md_theme_dark_onSurfaceVariant,
    outline = md_theme_dark_outline,
    inverseOnSurface = md_theme_dark_inverseOnSurface,
    inverseSurface = md_theme_dark_inverseSurface,
    inversePrimary = md_theme_dark_inversePrimary,
    surfaceTint = md_theme_dark_surfaceTint,
    outlineVariant = md_theme_dark_outlineVariant,
    scrim = md_theme_dark_scrim,
)