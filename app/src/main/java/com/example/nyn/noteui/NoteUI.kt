package com.example.nyn.noteui

import android.widget.Space
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nyn.ui.theme.CustomLightGray
import com.example.nyn.ui.theme.NYNTheme
import com.example.nyn.ui.theme.Sen


@Composable
fun NoteCard(modifier: Modifier = Modifier){
     Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .clickable { },
    ) {
        Column(
            modifier = Modifier.padding(15.dp)
        ) {
            Row {
                Text(
                    text = "Note Title",
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
                text = "Article nor prepare chicken you him now. Shy merits say advice ten before lovers innate add. She cordially behaviour can attempted estimable. Trees delay fancy noise manor do as an small. " +
                        "Felicity now law securing breeding likewise extended and. " +
                        "Roused either who favour why ham.",
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

@Preview(showBackground = true)
@Composable
fun NoteCardPreview() {
    NYNTheme {
        NoteCard()
    }
}