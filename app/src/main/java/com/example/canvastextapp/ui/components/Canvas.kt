package com.example.canvastextapp.ui.components
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

import androidx.compose.ui.platform.LocalContext


import com.example.canvastextapp.ViewModel.CanvasViewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.canvastextapp.ViewModel.CanvasIntent

@Composable
fun Canvas(viewModel: CanvasViewModel) {
    val canvasState by viewModel.state.collectAsState()

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(4.dp)
        .border(width = 2.dp, color = Color.Gray, shape = RoundedCornerShape(16.dp))
    )  {
        canvasState.draggableTexts.forEach { draggableText ->
            EditableDraggableTextComposable(
                draggableText = draggableText,
                onDrag = { newOffset ->
                    viewModel.handleIntent(CanvasIntent.UpdateTextPosition(draggableText.id, newOffset))
                },
                onTextChanged = { newText ->
                    viewModel.handleIntent(CanvasIntent.UpdateTextContent(draggableText.id, newText))
                },
                onSelected = { id ->
                    viewModel.handleIntent(CanvasIntent.SelectText(id))
                },
                viewModel = viewModel
            )
        }
    }
}
