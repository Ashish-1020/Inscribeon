package com.example.canvastextapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment

import androidx.compose.ui.platform.LocalContext


import com.example.canvastextapp.ViewModel.CanvasViewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import com.example.canvastextapp.ui.components.BottomNavBar
import com.example.canvastextapp.ui.components.Canvas
import com.example.canvastextapp.ui.components.FontOptionsBox
import com.example.canvastextapp.ui.components.TextSizeOptions
import com.example.canvastextapp.ui.components.UndoRedoButtons

@Composable
fun CanvasScreen(viewModel: CanvasViewModel) {
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        // Top Toolbar with Font Options
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp)
        ) {
            FontOptionsBox(viewModel) // Font options dropdown
            TextSizeOptions(viewModel) // Text size adjustment
            Spacer(modifier = Modifier.width(4.dp))
            UndoRedoButtons(viewModel) // Undo and redo buttons
        }

        // Canvas Section
        Box(modifier = Modifier.fillMaxWidth().padding(top = 48.dp, bottom = 60.dp)) {
            Canvas(viewModel) // Interactive canvas
        }

        // Bottom Navigation Bar
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(2.dp)
        ) {
            BottomNavBar(viewModel) // Bottom navigation bar
        }
    }
}
