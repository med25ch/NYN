package com.example.nyn.screens.addnotescreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CreateNewFolder
import androidx.compose.material.icons.filled.DriveFolderUpload
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.outlined.CreateNewFolder
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nyn.ui.theme.CustomLightGray
import com.example.nyn.ui.theme.Sen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(onDismiss: () -> Unit,modifier: Modifier = Modifier) {
    val modalBottomSheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = modalBottomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
    ) {

        AddNewCategoryUi(modifier)
        //CountryList()
    }
}

@Preview(showBackground = true)
@Composable
fun AddNewCategoryUi(modifier: Modifier = Modifier){
    //Category input
    var value by rememberSaveable { mutableStateOf("add a new category") }

        Box (modifier = modifier.padding(8.dp)){
            BasicTextField(
                value = value,
                onValueChange = { value = it },
                textStyle = TextStyle(
                    fontFamily = Sen,
                    fontWeight = FontWeight.Light,
                ),
                decorationBox = { innerTextField ->
                    // Because the decorationBox is used, the whole Row gets the same behaviour as the
                    // internal input field would have otherwise. For example, there is no need to add a
                    // Modifier.clickable to the Row anymore to bring the text field into focus when user
                    // taps on a larger text field area which includes paddings and the icon areas.
                    Row(modifier= Modifier
                        .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(percent = 20))
                        .padding(8.dp)
                        .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Outlined.CreateNewFolder, contentDescription = null)
                        Spacer(Modifier.width(16.dp))
                        innerTextField()
                    }
                }
            )
        }


}

@Composable
fun CountryList() {
    val countries = listOf(
        Pair("United States", "\uD83C\uDDFA\uD83C\uDDF8"),
        Pair("Canada", "\uD83C\uDDE8\uD83C\uDDE6"),
        Pair("India", "\uD83C\uDDEE\uD83C\uDDF3"),
        Pair("Germany", "\uD83C\uDDE9\uD83C\uDDEA"),
        Pair("France", "\uD83C\uDDEB\uD83C\uDDF7"),
        Pair("Japan", "\uD83C\uDDEF\uD83C\uDDF5"),
        Pair("China", "\uD83C\uDDE8\uD83C\uDDF3"),
        Pair("Brazil", "\uD83C\uDDE7\uD83C\uDDF7"),
        Pair("Australia", "\uD83C\uDDE6\uD83C\uDDFA"),
        Pair("Russia", "\uD83C\uDDF7\uD83C\uDDFA"),
        Pair("United Kingdom", "\uD83C\uDDEC\uD83C\uDDE7"),
    )

    LazyColumn {
        items(countries) { (country, flag) ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp, horizontal = 20.dp)
            ) {
                Text(
                    text = flag,
                    modifier = Modifier.padding(end = 20.dp)
                )
                Text(text = country)
            }
        }
    }
}