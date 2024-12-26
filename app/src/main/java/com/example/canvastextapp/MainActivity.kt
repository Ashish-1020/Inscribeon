package com.example.canvastextapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.canvastextapp.Data.EditableText
import com.example.canvastextapp.ViewModel.CanvasIntent
import com.example.canvastextapp.ViewModel.CanvasViewModel
import com.example.canvastextapp.ui.screens.CanvasScreen
import com.example.canvastextapp.ui.theme.CanvasTextAppTheme
/*
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel = CanvasViewModel()
            CanvasScreen(viewModel)
        }
    }
}


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
                .padding(top = 8.dp)
        ) {
            // Font Options Box
            Box(
                modifier = Modifier
                    .width(120.dp)
                    .height(42.dp)
                    .padding(top = 8.dp, start = 4.dp)
                    .background(color = Color.LightGray, shape = RoundedCornerShape(6.dp))
            ) {
                Box(
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .align(Alignment.CenterStart)
                ) {
                    Text(
                        text = "font",
                        color = Color.Black,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Downward Arrow Icon
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.down_arrow),
                        contentDescription = "Downward arrow",
                        tint = Color.Black
                    )
                }
            }

            // Text Size Option
            Textsize()

            Spacer(modifier = Modifier.width(4.dp))

            // Undo and Redo Buttons with dynamic states
            val canUndo by remember { derivedStateOf { viewModel.canUndo } }
            val canRedo by remember { derivedStateOf { viewModel.canRedo } }

            // Undo Button
            Button(
                onClick = { },
                enabled = canUndo, // Button is enabled only if undo is available
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (canUndo) Color.White else Color.LightGray
                )
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.undo_svgrepo_com),
                    contentDescription = "Undo",
                    tint = if (canUndo) Color.Black else Color.Gray,
                    modifier = Modifier.size(24.dp)
                )
            }

            // Redo Button
            Button(
                onClick = { if (canRedo) viewModel.redo() },
                enabled = canRedo, // Button is enabled only if redo is available
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (canRedo) Color.White else Color.LightGray
                )
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.redo_svgrepo_com),
                    contentDescription = "Redo",
                    tint = if (canRedo) Color.Black else Color.Gray,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        // Canvas Section
        Row(modifier = Modifier.padding(top = 48.dp, bottom = 60.dp)) {
            Canvas(viewModel)
        }

        // Bottom Navigation Bar
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(2.dp)
        ) {
            BottomNavBar(viewModel)
        }
    }
}


@Composable
fun Canvas(viewModel: CanvasViewModel) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp)
            .border(width = 2.dp, color = Color.Gray, shape = RoundedCornerShape(16.dp))
    ) {
        viewModel.draggableTexts.forEach { draggableText ->
            EditableDraggableTextComposable(
                draggableText = draggableText,
                onDrag = { newPosition ->
                    viewModel.updateTextPosition(draggableText.id, newPosition)
                },
                onTextChanged = { newText ->
                    viewModel.updateTextContent(draggableText.id, newText)
                },
                onStyleChanged = { newStyle -> // Style change handler
                    viewModel.updateTextStyle(draggableText.id, newStyle)
                },
                onSelected = { viewModel.selectText(draggableText.id) },
            )
        }
    }
}




@Composable
fun BottomNavBar(viewModel: CanvasViewModel) {
    val context = LocalContext.current
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
        Button(
            onClick = {

                val selectedTextId = viewModel.selectedTextId
                if (selectedTextId != null) {

                    viewModel.toggleBold(selectedTextId) // Toggle bold
                    Toast.makeText(context, "Bold toggled", Toast.LENGTH_SHORT).show()

                } else {

                    Toast.makeText(context, "No text selected", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .size(40.dp) // Adjust size as needed
                .padding(4.dp), // Optional: add padding if you want
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White, // Set transparent background
                contentColor = Color.Gray // Set the content color (text/icon color)
            ),
            contentPadding = PaddingValues(0.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.bold),
                contentDescription = "Bold",
                tint = if (viewModel.selectedTextId != null && viewModel.isBold(viewModel.selectedTextId!!)) Color.Black else Color.Gray,
                modifier = Modifier.size(14.dp)
            )
        }


// Italic Button
        Button(
            onClick = {

                val selectedTextId = viewModel.selectedTextId
                if (selectedTextId != null) {

                    viewModel.toggleItalic(selectedTextId) // Toggle italic style
                    Toast.makeText(context, "Italic toggled", Toast.LENGTH_SHORT).show() // Show toast message

                } else {
                    Toast.makeText(context, "No text selected", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .size(40.dp) // Adjust size as needed
                .padding(4.dp), // Optional: add padding if you want
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White, // Set transparent background
                contentColor = Color.Gray // Set default content color
            ),
            contentPadding = PaddingValues(0.dp) // Remove additional padding
        ) {
            Icon(
                painter = painterResource(id = R.drawable.italic), // Italic icon resource
                contentDescription = "italic", // Accessibility description
                tint =if (viewModel.selectedTextId != null && viewModel.isItalic(viewModel.selectedTextId!!)) Color.Black else Color.Gray ,
                modifier = Modifier.size(14.dp) // Set icon size
            )
        }


// Underline Button
        Button(
            onClick = {

                val selectedTextId = viewModel.selectedTextId
                if (selectedTextId != null) {

                    viewModel.toggleUnderline(selectedTextId)  // Toggle underline
                    Toast.makeText(context, "Underline toggled", Toast.LENGTH_SHORT).show() // Toast message


                } else {
                    Toast.makeText(context, "No text selected", Toast.LENGTH_SHORT).show()
                }

                //change the tint here
            },
            modifier = Modifier
                .size(40.dp) // Adjust size as needed
                .padding(4.dp), // Optional: add padding if you want
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White, // Set transparent background
                contentColor = Color.Gray // Set the content color (text/icon color)
            ),
            contentPadding = PaddingValues(0.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.underline),
                contentDescription = "underline",
                tint = if (viewModel.selectedTextId != null && viewModel.isUnderlined(viewModel.selectedTextId!!)) Color.Black else Color.Gray,
                modifier = Modifier.size(14.dp)
            )
        }




        //align
        Box(
            modifier = Modifier
                .size(20.dp)
                .clickable(
                    onClick = {},
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ),
            contentAlignment = Alignment.Center)
        {
            Icon(
                painter = painterResource(id = R.drawable.align_centre),
                contentDescription ="alignment",
                tint =Color.Gray,
                modifier = Modifier.size(14.dp)
            )
        }

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

@Composable
fun Textsize() {
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
                .padding(horizontal = 8.dp), // Add padding inside the row for better spacing
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Minus Button
            Button(
                onClick = { /* Handle Text Size Decrease */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier.size(14.dp) // Remove extra padding inside button
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.minus),
                    contentDescription = "minus",
                    tint = Color.Black,
                    modifier = Modifier.size(11.dp) // Adjust icon size
                )
            }

            // Text in the center
            Text(
                text = "14",
                fontSize = 14.sp, // Adjust font size for visibility
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )

            // Plus Button
            Button(
                onClick = { /* Handle Text Size Increase */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier.size(14.dp) // Remove extra padding inside button
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.add),
                    contentDescription = "plus",
                    tint = Color.Black,
                    modifier = Modifier.size(16.dp) // Adjust icon size
                )
            }
        }
    }
}

@Composable
fun EditableDraggableTextComposable(
    draggableText: EditableText,
    onDrag: (Offset) -> Unit,
    onTextChanged: (String) -> Unit,
    onStyleChanged: (TextStyle) -> Unit, // New function for style changes
    onSelected: (Int) -> Unit
) {
    var offsetX by remember { mutableStateOf(draggableText.positionX) }
    var offsetY by remember { mutableStateOf(draggableText.positionY) }
    var text by remember { mutableStateOf(draggableText.text) }
    var style by remember { mutableStateOf(draggableText.style) } // Add a mutable state for style
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    Box(
        modifier = Modifier
            .offset { IntOffset(offsetX.toInt(), offsetY.toInt()) }
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    offsetX += dragAmount.x
                    offsetY += dragAmount.y
                    onDrag(Offset(offsetX, offsetY))
                }
            }
    ) {
        BasicTextField(
            value = text,
            onValueChange = {
                text = it
                onTextChanged(it)
            },
            modifier = Modifier
                .background(Color.White)
                .border(1.dp, Color.Black)
                .padding(4.dp)
                .focusRequester(focusRequester)
                .onFocusChanged { focusState ->
                    if (focusState.isFocused) {
                        onSelected(draggableText.id)
                    }
                },
            textStyle = style, // Use the mutable style state
        )


        // Observe and apply style changes
        if (style != draggableText.style) {
            style = draggableText.style
            onStyleChanged(style)
        }
    }
}*/




class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Box(modifier = Modifier.padding(4.dp)){
                CanvasScreen(CanvasViewModel())
            }
        }
    }
}




@Preview(showBackground = true)
@Composable
fun GreetingPreview() {

        CanvasScreen(CanvasViewModel())


}
