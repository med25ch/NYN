package com.example.nyn.screens.dialogscreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog


@Composable
fun ColorPickerDialog(
    onDismissRequest: () -> Unit,
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        // Draw a rectangle shape with rounded corners inside the dialog
        Card(
            modifier = Modifier
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
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
                    ColorSurface(color = 0xffc2dcfd)
                    ColorSurface(color = 0xffffd8f4)
                    ColorSurface(color = 0xfffbf6aa)
                    ColorSurface(color = 0xffb0e9ca)
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    //Surface Colors
                    ColorSurface(color = 0xfffcfad9)
                    ColorSurface(color = 0xfff1dbf5)
                    ColorSurface(color = 0xffd9e8fc)
                    ColorSurface(color = 0xffffdbe3)
                }
            }
        }
    }
}

@Preview
@Composable
fun ColorSurface(modifier: Modifier = Modifier,color : Long){
    Surface(
        modifier = Modifier
            .height(64.dp)
            .width(64.dp)
            .padding(1.dp),
        color = Color(color),
        tonalElevation = 40.dp,
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(0.5.dp, Color.Gray)
    ) {
    }
}

@Preview
@Composable
fun showDialog(){
        ColorPickerDialog(onDismissRequest = { /*TODO*/ })
}