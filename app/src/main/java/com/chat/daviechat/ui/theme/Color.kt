package com.chat.daviechat.ui.theme


import androidx.compose.material.Colors
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)


val gray25 = Color(0.75f, 0.76f, 0.79f, 1.0f)
val gray05 = Color(0.95f, 0.95f, 0.96f, 1.0f)
val gray50 = Color(0.5f, 0.53f, 0.58f, 1.0f)
val gray00 = Color(1.0f, 1.0f, 1.0f, 1.0f)
val green100 = Color(0.0f, 0.46f, 0.33f, 1.0f)
val green70 = Color(0.03f, 0.63f, 0.46f, 1.0f)
val green15 = Color(0.78f, 0.92f, 0.88f, 1.0f)

val Colors.disabled: Color
    get() {
        return green15
    }

val Colors.onDisabled: Color
    get() {
        return gray50
    }