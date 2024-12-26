package com.example.canvastextapp.ui.components


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment


import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.canvastextapp.Data.EditableText
import com.example.canvastextapp.R
import com.example.canvastextapp.ViewModel.CanvasIntent
import com.example.canvastextapp.ViewModel.CanvasViewModel
@Composable
fun EditableDraggableTextComposable(
    draggableText: EditableText, // The text item to be displayed and manipulated
    onDrag: (Offset) -> Unit, // Lambda to handle drag events
    onTextChanged: (String) -> Unit, // Lambda to handle text content updates
    onSelected: (Int) -> Unit, // Lambda to handle selection events
    viewModel: CanvasViewModel // The ViewModel to manage state and logic
) {
    // Request focus for text editing
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    // Extract current state values for the text
    var offsetX by remember { mutableFloatStateOf(draggableText.positionX) }
    var offsetY by remember { mutableFloatStateOf(draggableText.positionY) }
    var text by remember { mutableStateOf(draggableText.text) }


    // Current text style with font size taken from draggableText
    val textStyle = draggableText.style.copy(fontSize = draggableText.fontSize.sp)

    // Main container for the draggable and editable text
    Box(
        modifier = Modifier
            .offset { IntOffset(offsetX.toInt(), offsetY.toInt()) } // Apply current offset
            .pointerInput(Unit) { // Enable drag functionality
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    offsetX += dragAmount.x
                    offsetY += dragAmount.y
                    onDrag(Offset(offsetX, offsetY)) // Communicate the new position
                }
            }
    ) {
        // Editable text field
        BasicTextField(
            value = text, // Current text value
            onValueChange = {
                text = it
                onTextChanged(it) // Communicate text updates
            },
            modifier = Modifier
                .background(Color.White) // Background color
                .border(1.dp, Color.Black) // Border around the text
                .padding(4.dp) // Padding for text
                .focusRequester(focusRequester) // Handles focus requests
                .onFocusChanged { focusState ->
                    if (focusState.isFocused) {
                        onSelected(draggableText.id) // Communicate selection event
                    }
                },
            textStyle = textStyle, // Apply the current style
        )
    }
}
