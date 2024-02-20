package com.example.nyn.screens.addnotescreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.AddCircleOutline
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.nyn.data.models.category.NoteCategory
import com.example.nyn.ui.theme.Sen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(onDismiss: () -> Unit,
                addNoteViewModel: AddNoteViewModel,
                modifier: Modifier = Modifier,
                onSaveCategory: (String) -> Unit,
                onSetCategory: (String) -> Unit,
                onDeleteCategory: (NoteCategory) -> Unit) {

    val modalBottomSheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = modalBottomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
    ) {

        AddNewCategoryUi(modifier) {
            onSaveCategory(it)
        }

        CategoryList(
            addNoteViewModel = addNoteViewModel,
            modifier = modifier,
            onSetCategory = { onSetCategory(it) },
            onSelectedCategory = { onDeleteCategory(it) }
        )
    }
}

@Composable
fun AddNewCategoryUi(modifier: Modifier = Modifier,onSaveCategory: (String) -> Unit){
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
                        .background(
                            MaterialTheme.colorScheme.surface,
                            RoundedCornerShape(percent = 20)
                        )
                        .padding(8.dp)
                        .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Outlined.AddCircleOutline,
                            contentDescription = null,
                            Modifier.clickable {
                                onSaveCategory(value)
                            })
                        Spacer(Modifier.width(16.dp))
                        innerTextField()
                    }
                }
            )
        }
}

@Composable
fun CategoryList(addNoteViewModel: AddNoteViewModel,
                 modifier: Modifier = Modifier,
                 onSetCategory : (String) -> Unit,
                 onSelectedCategory: (NoteCategory) -> Unit) {

    val listState = rememberLazyListState()
    var selectedIndex by rememberSaveable { mutableIntStateOf(-1) }
    val categoriesUiState by addNoteViewModel.categoriesUiState.collectAsState()

    LazyColumn(state = listState,
        modifier = modifier.padding(bottom = 45.dp)) {
        items(items = categoriesUiState.repoList) { category ->
            CategoryRow(
                onDeleteCategory = { onSelectedCategory(category) },
                noteCategory = category,
                onCategoryClicked = {
                    selectedIndex = if (selectedIndex != category.id) category.id else -1
                    onSetCategory(category.name)
                },
                selected = selectedIndex == category.id,
                modifier = modifier)
        }
    }
}

@Composable
fun CategoryRow(modifier: Modifier = Modifier,
                onDeleteCategory : (NoteCategory) -> Unit,
                onCategoryClicked :(NoteCategory) -> Unit,
                selected: Boolean,
                noteCategory: NoteCategory){

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 20.dp)
                .clickable {
                    // TODO PUT CHECKED CATEGORY ON VIEWMODEL
                    onCategoryClicked(noteCategory)
                }
        ) {
            Icon(imageVector = if (selected) Icons.Filled.CheckCircle else Icons.Outlined.CheckCircle,
                contentDescription = null,
                tint = if (selected) Color.Black else Color.LightGray,
                modifier = modifier.padding(end = 16.dp)
            )
            Text(text = noteCategory.name, fontFamily = Sen, fontWeight = FontWeight.SemiBold,)
            Spacer(Modifier.weight(1f))
            Icon(imageVector = Icons.Filled.Delete,
                contentDescription = null,
                modifier.clickable {
                    onDeleteCategory(noteCategory)
                }
            )
        }

        Divider( thickness = 1.dp, color = Color.LightGray, modifier = Modifier.padding(start = 16.dp, end = 16.dp))
    }
}