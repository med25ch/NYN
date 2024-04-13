package com.example.nyn.screens.dialogs

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog


@Composable
fun ColorPickerDialog(
    onDismissRequest: () -> Unit,
    onSetNoteColor : (Long) -> Unit,
) {


    var clickedColorSurface by remember { mutableLongStateOf(0) }

    Dialog(onDismissRequest = { onDismissRequest() }) {
        // Draw a rectangle shape with rounded corners inside the dialog
        Card(
            modifier = Modifier
                .padding(16.dp),
            shape = RoundedCornerShape(15.dp),
        ) {
            Column(
                modifier = Modifier.padding(bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                Text(
                    text = "Choose your note color",
                    modifier = Modifier.padding(16.dp),
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    //Surface Colors
                    ColorSurface(color = 0xffc2dcfd, onColorSurfaceClicked = {clickedColorSurface = 0xffc2dcfd}, isSelected = clickedColorSurface == 0xffc2dcfd, onSetNoteColor = {onSetNoteColor(it)} )
                    ColorSurface(color = 0xffffd8f4, onColorSurfaceClicked = {clickedColorSurface = 0xffffd8f4}, isSelected = clickedColorSurface == 0xffffd8f4, onSetNoteColor = {onSetNoteColor(it)})
                    ColorSurface(color = 0xfffbf6aa, onColorSurfaceClicked = {clickedColorSurface = 0xfffbf6aa}, isSelected = clickedColorSurface == 0xfffbf6aa, onSetNoteColor = {onSetNoteColor(it)})
                    ColorSurface(color = 0xffb0e9ca, onColorSurfaceClicked = {clickedColorSurface = 0xffb0e9ca}, isSelected = clickedColorSurface == 0xffb0e9ca, onSetNoteColor = {onSetNoteColor(it)})
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    //Surface Colors
                    ColorSurface(color = 0xfffcfad9, onColorSurfaceClicked = {clickedColorSurface = 0xfffcfad9}, isSelected = clickedColorSurface == 0xfffcfad9, onSetNoteColor = {onSetNoteColor(it)})
                    ColorSurface(color = 0xfff1dbf5, onColorSurfaceClicked = {clickedColorSurface = 0xfff1dbf5}, isSelected = clickedColorSurface == 0xfff1dbf5, onSetNoteColor = {onSetNoteColor(it)})
                    ColorSurface(color = 0xffd9e8fc, onColorSurfaceClicked = {clickedColorSurface = 0xffd9e8fc}, isSelected = clickedColorSurface == 0xffd9e8fc, onSetNoteColor = {onSetNoteColor(it)})
                    ColorSurface(color = 0xffffdbe3, onColorSurfaceClicked = {clickedColorSurface = 0xffffdbe3}, isSelected = clickedColorSurface == 0xffffdbe3, onSetNoteColor = {onSetNoteColor(it)})
                }
            }
        }
    }
}

@Preview
@Composable
fun ColorSurface(
    modifier: Modifier = Modifier,
    color : Long ,
    onColorSurfaceClicked : ()-> Unit,
    isSelected : Boolean,
    onSetNoteColor : (Long) -> Unit,
    ){

    Surface(
        modifier = Modifier
            .height(64.dp)
            .width(64.dp)
            .padding(1.dp),
        color = Color(color),
        tonalElevation = 40.dp,
        shape = RoundedCornerShape(15.dp),
        border = BorderStroke(0.5.dp, Color.Gray)
    ) {
        IconButton(
            onClick = {
                onColorSurfaceClicked()
                onSetNoteColor(color)
            }
        ) {
            Icon(
                imageVector = Icons.Filled.Check,
                contentDescription = "Localized description",
                tint = if(isSelected) Color(0,0,0) else Color(color)
            )
        }
    }
}

@Preview
@Composable
fun ShowDialog(){
        ColorPickerDialog(onDismissRequest = { /*TODO*/ } , onSetNoteColor = { /*TODO*/ })
}