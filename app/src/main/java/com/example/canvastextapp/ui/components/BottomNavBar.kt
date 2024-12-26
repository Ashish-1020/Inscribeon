package com.example.canvastextapp.ui.components
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment

import androidx.compose.ui.platform.LocalContext


import com.example.canvastextapp.ViewModel.CanvasViewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.canvastextapp.R
import com.example.canvastextapp.ViewModel.CanvasIntent

@Composable
fun BottomNavBar(viewModel: CanvasViewModel) {
    val context = LocalContext.current
    val canvasState by viewModel.state.collectAsState()
    val selectedTextId = canvasState.selectedTextId

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(Color.White, shape = RoundedCornerShape(4.dp))
            .border(width = 2.dp, color = Color.Black, shape = RoundedCornerShape(8.dp)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        // Bold Button
        IconButton(
            iconId = R.drawable.bold,
            isSelected = selectedTextId != null && viewModel.isBold(selectedTextId),
            onClick = {
                if (selectedTextId != null) {
                    viewModel.handleIntent(CanvasIntent.ToggleBold(selectedTextId))

                } else {

                }
            },
            description = "Bold"
        )

        // Italic Button
        IconButton(
            iconId = R.drawable.italic,
            isSelected = selectedTextId != null && viewModel.isItalic(selectedTextId),
            onClick = {
                if (selectedTextId != null) {
                    viewModel.handleIntent(CanvasIntent.ToggleItalic(selectedTextId))

                } else {

                }
            },
            description = "Italic"
        )

        // Underline Button
        IconButton(
            iconId = R.drawable.underline,
            isSelected = selectedTextId != null && viewModel.isUnderline(selectedTextId),
            onClick = {
                if (selectedTextId != null) {
                    viewModel.handleIntent(CanvasIntent.ToggleUnderline(selectedTextId))

                } else {

                }
            },
            description = "Underline"
        )

        // Add Text Button
        Button(
            onClick = { viewModel.addText() },
            modifier = Modifier
                .height(48.dp)
                .border(width = 2.dp, color = Color.Gray, shape = RoundedCornerShape(36.dp)),
            colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.text_size_svgrepo_com),
                contentDescription = "Text",
                tint = Color.Black,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Add Text",
                fontSize = 12.sp,
                color = Color.Black
            )
        }
    }
}
@Composable
fun IconButton(iconId: Int, isSelected: Boolean, onClick: () -> Unit, description: String) {
    Box(
        modifier = Modifier
            .size(20.dp)
            .clickable(
                onClick = onClick,
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = description,
            tint = if (isSelected) Color.Black else Color.Gray,
            modifier = Modifier.size(14.dp)
        )
    }
}