package com.example.nyn.noteui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.nyn.data.models.note.Note
import com.example.nyn.ui.theme.CustomLightGray
import com.example.nyn.ui.theme.Sen


@Composable
fun NoteCard(modifier: Modifier = Modifier,
             note : Note){

     Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .clickable { },
    ) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            Row {
                Text(
                    text = note.title,
                    modifier = Modifier.weight(1f),
                    fontFamily = Sen,
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Icon(imageVector = Icons.Default.PushPin,
                    contentDescription = null,
                    tint = CustomLightGray,
                    modifier = modifier.clickable {  }
                )
            }
            Spacer(modifier = Modifier.size(10.dp))
            Text(
                text = note.body,
                maxLines = 4,
                overflow = TextOverflow.Ellipsis,
                fontFamily = Sen,
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.Normal,
                color = Color.Black,
            )
        }
    }
}

