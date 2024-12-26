package com.example.canvastextapp.ui.components


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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
@Composable
fun TextSizeOptions(viewModel: CanvasViewModel) {
    val canvasState by viewModel.state.collectAsState()
    val selectedTextId: Int? = canvasState.selectedTextId

    Box(
        modifier = Modifier
            .width(80.dp)
            .height(42.dp)
            .padding(top = 8.dp, start = 4.dp)
            .background(color = Color.LightGray, shape = RoundedCornerShape(6.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Minus Button
            Button(
                onClick = {
                    selectedTextId?.let {
                        val currentSize = canvasState.draggableTexts.find { it.id == selectedTextId }?.fontSize ?: 14
                        if (currentSize > 8) {
                            viewModel.handleIntent(CanvasIntent.UpdateFontSize(it, currentSize - 1))
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier.size(14.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.minus),
                    contentDescription = "Decrease text size",
                    tint = Color.Black,
                    modifier = Modifier.size(11.dp)
                )
            }

            // Current text size
            Text(
                text = selectedTextId?.let {
                    canvasState.draggableTexts.find { it.id == selectedTextId }?.fontSize.toString()
                } ?: "16",
                fontSize = 14.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )

            // Plus Button
            Button(
                onClick = {
                    selectedTextId?.let {
                        val currentSize = canvasState.draggableTexts.find { it.id == selectedTextId }?.fontSize ?: 14
                        if (currentSize < 72) {
                            viewModel.handleIntent(CanvasIntent.UpdateFontSize(it, currentSize + 1))
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier.size(14.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.add),
                    contentDescription = "Increase text size",
                    tint = Color.Black,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}
