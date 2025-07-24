package com.paymorecase.ui.standart

import androidx.compose.ui.unit.dp

/**
 * Centralized padding dimensions for consistent spacing in the app's UI.
 * These values are defined in dp (density-independent pixels) and can be used for padding in composables.
 *
 * Measurements:
 * - Extra-small padding: 12.dp — Suitable for tight spacing, like between icons and text.
 * - Small padding: 20.dp — Commonly used as the default spacing between standard elements.
 * - Medium padding: 24.dp — Provides a comfortable gap between components.
 * - Large padding: 32.dp — Useful for separating larger sections or layout blocks.
 * - Extra-large padding: 40.dp — Ideal for screen edges or major visual separation.
 */
object ScreenPaddingDimensions {

    /** Extra-small padding (12.dp) — suitable for tight spacing, like between icons and text. */
    val extraSmall = 12.dp

    /** Small padding (20.dp) — commonly used as the default spacing between standard elements. */
    val small = 20.dp

    /** Medium padding (24.dp) — provides a comfortable gap between components. */
    val medium = 24.dp

    /** Large padding (32.dp) — useful for separating larger sections or layout blocks. */
    val large = 32.dp

    /** Extra-large padding (40.dp) — ideal for screen edges or major visual separation. */
    val extraLarge = 40.dp
}