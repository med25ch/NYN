package com.example.nyn.noteui

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material.icons.outlined.PushPin
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.nyn.data.models.note.Note
import com.example.nyn.screens.dialogs.DeleteNoteDialog
import com.example.nyn.ui.theme.CustomLightGray
import com.example.nyn.ui.theme.Sen


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteCard(modifier: Modifier = Modifier,
             note : Note,
             onCardClick : (Note) -> Unit,
             onCardLongClick : (Boolean) -> Unit
){

     Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .combinedClickable(
                onClick = { onCardClick(note) },
                onLongClick = {
                    onCardLongClick(true)
                }
            )
        ,
         border = BorderStroke(1.dp, Color.LightGray),
         colors = CardDefaults.cardColors(
             containerColor = if(note.color != 0L) Color(note.color) else MaterialTheme.colorScheme.surfaceVariant
         )
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

                Icon(imageVector = if (note.isPinned)
                    Icons.Filled.PushPin
                else
                    Icons.Outlined.PushPin
                    ,
                    contentDescription = null,
                    tint = if (note.isPinned)
                        Color.Red
                    else
                        CustomLightGray
                        ,
                    modifier = modifier
                        .rotate(45f)
                        .clickable {
                            // TODO Pin Note
                        }
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

