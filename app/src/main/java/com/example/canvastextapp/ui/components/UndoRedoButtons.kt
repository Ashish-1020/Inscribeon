package com.example.canvastextapp.ui.components
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment


import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.canvastextapp.R
import com.example.canvastextapp.ViewModel.CanvasIntent
import com.example.canvastextapp.ViewModel.CanvasViewModel

@RequiresApi(35)
@Composable
fun UndoRedoButtons(viewModel: CanvasViewModel) {
    Button(
        onClick = { viewModel.handleIntent(CanvasIntent.UndoAction) },
        colors = ButtonDefaults.buttonColors(containerColor = Color.White)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.undo_svgrepo_com),
            contentDescription = "Undo",
            tint = Color.Gray,
            modifier = Modifier.size(24.dp)
        )
    }

    Button(
        onClick = { viewModel.handleIntent(CanvasIntent.RedoAction) },
        colors = ButtonDefaults.buttonColors(containerColor = Color.White)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.redo_svgrepo_com),
            contentDescription = "Redo",
            tint = Color.Gray,
            modifier = Modifier.size(24.dp)
        )
    }
}
