package com.example.canvastextapp.ViewModel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.lifecycle.ViewModel
import com.example.canvastextapp.Data.EditableText
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/*
class CanvasViewModel : ViewModel() {

    // List of draggable texts
    private val _draggableTexts = mutableStateListOf<EditableText>()
    val draggableTexts: List<EditableText> get() = _draggableTexts

    // Currently selected text ID
    private val _selectedTextId = mutableStateOf<Int?>(null)
    val selectedTextId: Int? get() = _selectedTextId.value

    private var currentId = 0 // Keeps track of unique IDs

    // Undo/Redo Stacks
    private val undoStack = mutableListOf<List<EditableText>>()
    private val redoStack = mutableListOf<List<EditableText>>()

    // Mutable states to track undo/redo availability
    private val _canUndo = mutableStateOf(false)
    val canUndo: Boolean get() = _canUndo.value

    private val _canRedo = mutableStateOf(false)
    val canRedo: Boolean get() = _canRedo.value

    // Method to update undo/redo button states
    private fun updateUndoRedoStates() {
        _canUndo.value = undoStack.isNotEmpty()
        _canRedo.value = redoStack.isNotEmpty()
    }

    // Save the current state to the undo stack
    private fun saveStateToUndo() {
        undoStack.add(_draggableTexts.map { it.copy() })  // Save a copy of the current state
        redoStack.clear()  // Clear redo stack when a new action is made
        updateUndoRedoStates()
    }

    // Undo the last action
    @RequiresApi(35)
    fun undo() {
        if (undoStack.isNotEmpty()) {
            redoStack.add(_draggableTexts.map { it.copy() })
            val lastState = undoStack.removeLast()
            _draggableTexts.clear()
            _draggableTexts.addAll(lastState)
            updateUndoRedoStates()
        }
    }

    // Redo the last undone action
    @RequiresApi(35)
    fun redo() {
        if (redoStack.isNotEmpty()) {
            undoStack.add(_draggableTexts.map { it.copy() })
            val lastRedoState = redoStack.removeLast()
            _draggableTexts.clear()
            _draggableTexts.addAll(lastRedoState)
            updateUndoRedoStates()
        }
    }

    // Adds a new text item to the canvas
    fun addText() {
        val newText = EditableText(
            id = currentId++,  // Unique ID
            text = "New Text",
            fontSize = 16,
            style = TextStyle.Default,
            positionX = 0f,
            positionY = 0f
        )
        _draggableTexts.add(newText)
        saveStateToUndo()  // Save the state after adding text
    }

    // Updates the position of the text by its ID
    fun updateTextPosition(id: Int, newPosition: Offset) {
        _draggableTexts.find { it.id == id }?.apply {
            positionX = newPosition.x
            positionY = newPosition.y
        }
        saveStateToUndo()  // Save the state after position change
    }

    // Updates the text content by its ID
    fun updateTextContent(id: Int, newText: String) {
        _draggableTexts.find { it.id == id }?.apply {
            Log.d("CanvasViewModel", "Update text called with ID: $id for the new String $newText")
            text = newText
        }
        saveStateToUndo()  // Save the state after text change
    }

    // Updates the style of text (bold, italic, underline)
    fun updateTextStyle(id: Int, newStyle: TextStyle) {
        _draggableTexts.find { it.id == id }?.apply {
            Log.d("CanvasViewModel", "Update text style called with ID: $id for the new String $text")
            style = newStyle  // Update the style
        }
        saveStateToUndo()  // Save the state after style change
    }

    // Functions to toggle text styles (bold, italic, underline)
    fun toggleBold(id: Int) {
        val currentText = _draggableTexts.find { it.id == id }
        currentText?.let {
            val newStyle = if (it.style.fontWeight == FontWeight.Bold) {
                it.style.copy(fontWeight = FontWeight.Normal) // Remove bold
            } else {
                it.style.copy(fontWeight = FontWeight.Bold) // Apply bold
            }
            it.isBold = !it.isBold
            updateTextStyle(id, newStyle)
        }
    }

    fun toggleItalic(id: Int) {
        val currentText = _draggableTexts.find { it.id == id }
        currentText?.let {
            val newStyle = if (it.style.fontStyle == FontStyle.Italic) {
                it.style.copy(fontStyle = FontStyle.Normal) // Remove italic
            } else {
                it.style.copy(fontStyle = FontStyle.Italic) // Apply italic
            }
            it.isItalic = !it.isItalic
            updateTextStyle(id, newStyle)
        }
    }

    fun toggleUnderline(id: Int) {
        val currentText = _draggableTexts.find { it.id == id }
        currentText?.let {
            val newStyle = if (it.style.textDecoration == TextDecoration.Underline) {
                it.style.copy(textDecoration = TextDecoration.None) // Remove underline
            } else {
                it.style.copy(textDecoration = TextDecoration.Underline) // Apply underline
            }
            it.isUnderlined = !it.isUnderlined
            updateTextStyle(id, newStyle)
        }
    }

    // Updates font size for a specific text
    fun updateFontSize(id: Int, newSize: Int) {
        _draggableTexts.find { it.id == id }?.apply {
            fontSize = newSize
        }
        saveStateToUndo()  // Save the state after font size change
    }

    // Updates the font family for a specific text
    fun updateFontFamily(id: Int, newFontFamily: String) {
        _draggableTexts.find { it.id == id }?.apply {
            fontFamily = newFontFamily
        }
        saveStateToUndo()  // Save the state after font family change
    }

    // Selects text based on its ID
    fun selectText(textId: Int) {
        Log.d("CanvasViewModel", "selectText called with ID: $textId")
        _selectedTextId.value = textId
    }




    fun isBold(textId: Int): Boolean {
        return _draggableTexts.find { it.id == textId }?.style?.fontWeight == FontWeight.Bold
    }

    fun isItalic(textId: Int): Boolean {
        return _draggableTexts.find { it.id == textId }?.style?.fontStyle == FontStyle.Italic
    }

    fun isUnderlined(textId: Int): Boolean {
        return _draggableTexts.find { it.id == textId }?.style?.textDecoration == TextDecoration.Underline
    }


    // Reload the text content for the selected text ID
    fun reloadSelectedText() {
        val selectedId = _selectedTextId.value
        if (selectedId != null) {
            _draggableTexts.find { it.id == selectedId }?.apply {
                text = text // Simply re-assign the text to itself to notify recomposition
            }
            Log.d("CanvasViewModel", "Text reloaded for ID: $selectedId")
        } else {
            Log.d("CanvasViewModel", "No text selected to reload")
        }
    }
}*/
class CanvasViewModel : ViewModel() {

    // Holds the current state of the canvas
    private val _state = MutableStateFlow(CanvasState())
    val state: StateFlow<CanvasState> get() = _state

    private var currentId = 0
    // Stacks for undo and redo operations
    private val undoStack: MutableList<CanvasState> = mutableListOf()
    private val redoStack: MutableList<CanvasState> = mutableListOf()// Tracks unique IDs for draggable texts

    // fun 1 Handles incoming intents and performs the corresponding action
    @RequiresApi(35)
    fun handleIntent(intent: CanvasIntent) {
        when (intent) {
            is CanvasIntent.AddText -> addText()
            is CanvasIntent.UpdateTextPosition -> updateTextPosition(intent.id, intent.newPosition)
            is CanvasIntent.UpdateTextContent -> updateTextContent(intent.id, intent.newText)
            is CanvasIntent.UpdateTextStyle -> updateTextStyle(intent.id, intent.newStyle)
            is CanvasIntent.ToggleBold -> toggleBold(intent.id)
            is CanvasIntent.ToggleItalic -> toggleItalic(intent.id)
            is CanvasIntent.ToggleUnderline -> toggleUnderline(intent.id)
            is CanvasIntent.UpdateFontSize -> updateFontSize(intent.id, intent.newSize)
            is CanvasIntent.UpdateFontFamily -> updateFontFamily(intent.id, intent.newFontFamily)
            is CanvasIntent.UndoAction -> undoAction()
            is CanvasIntent.RedoAction -> redoAction()
            is CanvasIntent.SelectText -> selectText(intent.id)
            is CanvasIntent.ReloadSelectedText -> reloadSelectedText()
        }
    }

    // fun 2 Adds a new text element to the canvas
    fun addText() {
        performAction {
            val newText = EditableText(
                id = currentId++, // Assign unique ID
                text = "New Text",
                fontSize = 16,
                style = TextStyle.Default,
                positionX = 0f,
                positionY = 0f
            )
            _state.value = _state.value.copy(draggableTexts = _state.value.draggableTexts + newText)
        }
    }

    // fun 3 Updates the position of a text element
    private fun updateTextPosition(id: Int, newPosition: Offset) {
   performAction {
       val updatedTexts = _state.value.draggableTexts.map {
           if (it.id == id) it.copy(positionX = newPosition.x, positionY = newPosition.y) else it
       }
       _state.value = _state.value.copy(draggableTexts = updatedTexts)
   }
    }

    // fun 4 Updates the content of a text element
    private fun updateTextContent(id: Int, newText: String) {
        performAction {
        val updatedTexts = _state.value.draggableTexts.map {
            if (it.id == id) it.copy(text = newText) else it
        }
        _state.value = _state.value.copy(draggableTexts = updatedTexts)
    }

    }

    // fun 5Updates the style of a text element
    private fun updateTextStyle(id: Int, newStyle: TextStyle) {
        performAction {
            val updatedTexts = _state.value.draggableTexts.map {
                if (it.id == id) it.copy(style = newStyle) else it
            }
            _state.value = _state.value.copy(draggableTexts = updatedTexts)

        }
    }

    // fun 6 Toggles bold styling for a text element
    private fun toggleBold(id: Int) {
        val updatedTexts = _state.value.draggableTexts.map {
            if (it.id == id) {
                val newStyle = if (it.style.fontWeight == FontWeight.Bold) {
                    it.style.copy(fontWeight = FontWeight.Normal)
                } else {
                    it.style.copy(fontWeight = FontWeight.Bold)
                }
                it.copy(style = newStyle)
            } else it
        }
        _state.value = _state.value.copy(draggableTexts = updatedTexts)
    }

    // fun 7 Toggles italic styling for a text element
    private fun toggleItalic(id: Int) {
        val updatedTexts = _state.value.draggableTexts.map {
            if (it.id == id) {
                val newStyle = if (it.style.fontStyle == FontStyle.Italic) {
                    it.style.copy(fontStyle = FontStyle.Normal)
                } else {
                    it.style.copy(fontStyle = FontStyle.Italic)
                }
                it.copy(style = newStyle)
            } else it
        }
        _state.value = _state.value.copy(draggableTexts = updatedTexts)
    }

    //fun 8 Toggles underline styling for a text element
    private fun toggleUnderline(id: Int) {
        val updatedTexts = _state.value.draggableTexts.map {
            if (it.id == id) {
                val newStyle = if (it.style.textDecoration == TextDecoration.Underline) {
                    it.style.copy(textDecoration = TextDecoration.None)
                } else {
                    it.style.copy(textDecoration = TextDecoration.Underline)
                }
                it.copy(style = newStyle)
            } else it
        }
        _state.value = _state.value.copy(draggableTexts = updatedTexts)
    }

    //fun 9 Updates font size for a text element
    private fun updateFontSize(id: Int, newSize: Int)
    {performAction {
        println("Updating font size for ID: $id to $newSize")
        val updatedTexts = _state.value.draggableTexts.map {
            if (it.id == id) it.copy(fontSize = newSize) else it
        }
        println("Updated texts: $updatedTexts")
        _state.value = _state.value.copy(draggableTexts = updatedTexts)
        println("State updated: ${_state.value}")
    }}

    private fun updateFontFamily(id: Int, newFontFamily: String) {
        val fontFamily = when (newFontFamily) {
            "Arial" -> FontFamily.SansSerif
            "Roboto" -> FontFamily.Default
            "Courier New" -> FontFamily.Monospace
            "Times New Roman" -> FontFamily.Serif
            else -> FontFamily.Default // Default fallback
        }

        performAction {
            val updatedTexts = _state.value.draggableTexts.map {
                if (it.id == id) it.copy(style = it.style.copy(fontFamily = fontFamily))
                else it
            }
            _state.value = _state.value.copy(draggableTexts = updatedTexts)
        }
    }





    // fun 11 Placeholder for Undo functionality
    private fun performAction(action: () -> Unit) {
        saveStateToUndoStack()
        action()
        clearRedoStack()
    }

    private fun saveStateToUndoStack() {
        undoStack.add(
            _state.value.copy(
                draggableTexts = _state.value.draggableTexts.map { it.copy() }
            )
        )
    }

    private fun clearRedoStack() {
        redoStack.clear()
    }

    @RequiresApi(35)
    private fun undoAction() {
        if (undoStack.isNotEmpty()) {
            redoStack.add(_state.value.copy(
                draggableTexts = _state.value.draggableTexts.map { it.copy() }
            ))
            _state.value = undoStack.removeLast()
        }
    }

    @RequiresApi(35)
    private fun redoAction() {
        if (redoStack.isNotEmpty()) {
            undoStack.add(_state.value.copy(
                draggableTexts = _state.value.draggableTexts.map { it.copy() }
            ))
            _state.value = redoStack.removeLast()
        }
    }

    //fun 13 Selects a text element
    private fun selectText(id: Int) {
        _state.value = _state.value.copy(selectedTextId = id)
    }

    //fun 14 Reloads the selected text (no changes needed as the state is in sync)
    private fun reloadSelectedText() {}

    //fun 15 Helper functions to check styles of selected text
    fun isBold(selectedId: Int): Boolean {
        val selectedText = _state.value.draggableTexts.find { it.id == selectedId }
        return selectedText?.style?.fontWeight == FontWeight.Bold
    }


    //fun 16
    fun isItalic(selectedId: Int): Boolean {
        val selectedText = _state.value.draggableTexts.find { it.id == selectedId }
        return selectedText?.style?.fontStyle == FontStyle.Italic
    }


    //fun 17
    fun isUnderline(selectedId: Int): Boolean {
        val selectedText = _state.value.draggableTexts.find { it.id == selectedId }
        return selectedText?.style?.textDecoration == TextDecoration.Underline
    }
}

//fun 18
data class CanvasState(
    val draggableTexts: List<EditableText> = emptyList(),
    val selectedTextId: Int? = null
)


//fun 19
sealed class CanvasIntent {
    object AddText : CanvasIntent()
    data class UpdateTextPosition(val id: Int, val newPosition: Offset) : CanvasIntent()
    data class UpdateTextContent(val id: Int, val newText: String) : CanvasIntent()
    data class UpdateTextStyle(val id: Int, val newStyle: TextStyle) : CanvasIntent()
    data class ToggleBold(val id: Int) : CanvasIntent()
    data class ToggleItalic(val id: Int) : CanvasIntent()
    data class ToggleUnderline(val id: Int) : CanvasIntent()
    data class UpdateFontSize(val id: Int, val newSize: Int) : CanvasIntent()
    data class UpdateFontFamily(val id: Int, val newFontFamily: String) : CanvasIntent()
    object UndoAction : CanvasIntent()
    object RedoAction : CanvasIntent()
    data class SelectText(val id: Int) : CanvasIntent()
    object ReloadSelectedText : CanvasIntent()
}
