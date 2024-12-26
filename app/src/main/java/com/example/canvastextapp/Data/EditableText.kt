package com.example.canvastextapp.Data

import androidx.compose.ui.text.TextStyle

data class EditableText(
    val id: Int,
    var text: String,
    var fontSize: Int,
    var style: TextStyle,
    var isBold: Boolean = false,
    var isItalic: Boolean = false,
    var isUnderlined: Boolean = false,
    var fontFamily: String = "Default",
    var positionX: Float = 0f, // Ensure this is Float
    var positionY: Float = 0f  // Ensure this is Float
)


