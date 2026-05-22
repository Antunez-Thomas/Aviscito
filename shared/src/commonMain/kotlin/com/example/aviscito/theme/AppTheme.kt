package com.example.aviscito.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Pallet of Colors
val AviscitoColorScheme = lightColorScheme(
    primary = Color(0xFF99462A),
    primaryContainer = Color(0xFFD97757),
    onPrimary = Color(0xFFFFFFFF),
    onPrimaryContainer = Color(0xFF541400),
    secondary = Color(0xFF5F5E5E),
    secondaryContainer = Color(0xFFE2DFDE),
    onSecondary = Color(0xFFFFFFFF),
    onSecondaryContainer = Color(0xFF636262),
    background = Color(0xFFF9F9F7),
    onBackground = Color(0xFF1A1C1B),
    surface = Color(0xFFF9F9F7),
    surfaceContainerLowest = Color(0xFFFFFFFF),
    surfaceContainerLow = Color(0xFFF4F4F2),
    surfaceContainer = Color(0xFFEEEEEC),
    surfaceContainerHigh = Color(0xFFE8E8E6),
    surfaceContainerHighest = Color(0xFFE2E3E1),
    onSurface = Color(0xFF1A1C1B),
    onSurfaceVariant = Color(0xFF55433D),
    outline = Color(0xFF88726C),
    outlineVariant = Color(0xFFDBC1B9),
    error = Color(0xFFBA1A1A),
    errorContainer = Color(0xFFFFDAD6),
    inverseSurface = Color(0xFF2F3130),
    inverseOnSurface = Color(0xFFF1F1EF),
    inversePrimary = Color(0xFFFFB59E),
)

@Composable
fun AppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = AviscitoColorScheme,
        content = content
    )
}