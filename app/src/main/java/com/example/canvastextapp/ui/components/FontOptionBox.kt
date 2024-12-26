package com.example.canvastextapp.ui.components


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment


import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.canvastextapp.R
import com.example.canvastextapp.ViewModel.CanvasIntent
import com.example.canvastextapp.ViewModel.CanvasViewModel
@Composable
fun FontOptionsBox(viewModel: CanvasViewModel) {
    val canvasState by viewModel.state.collectAsState()
    val selectedTextId = canvasState.selectedTextId

    // Current font family (default or the selected font for the text)
    val currentFontFamily = selectedTextId?.let {
        canvasState.draggableTexts.find { it.id == selectedTextId }?.style?.fontFamily.toString()
    } ?: "Default Font"

    // State for controlling the dropdown menu visibility
    var expanded by remember { mutableStateOf(false) }

    // Available font options
    val fontOptions = listOf("Default Font", "Arial", "Roboto", "Courier New", "Times New Roman")

    Box(
        modifier = Modifier
            .width(120.dp)
            .height(42.dp)
            .padding(top = 8.dp, start = 4.dp)
            .background(color = Color.LightGray, shape = RoundedCornerShape(6.dp))
            .clickable { expanded = !expanded } // Toggle dropdown on click
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Display the selected font
            Text(
                text = currentFontFamily,
                color = Color.Black,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            // Downward arrow icon
            Icon(
                painter = painterResource(id = R.drawable.down_arrow),
                contentDescription = "Downward arrow",
                tint = Color.Black
            )
        }
    }

    // Dropdown menu with available font options
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false }, // Close the dropdown when clicked outside
        modifier = Modifier.width(150.dp)
    ) {
        fontOptions.forEach { font ->
            DropdownMenuItem(
                onClick = {
                    selectedTextId?.let { id ->
                        // Update font family in the ViewModel
                        viewModel.handleIntent(CanvasIntent.UpdateFontFamily(id, font))
                    }
                    expanded = false // Close dropdown after selection
                },
                text = { Text(font) }
            )
        }
    }
}



