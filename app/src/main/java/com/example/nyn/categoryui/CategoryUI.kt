package com.example.nyn.categoryui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nyn.screens.homescreen.CategoryOccurrence
import com.example.nyn.screens.homescreen.HomeScreenViewModel
import com.example.nyn.ui.theme.Sen
import com.example.nyn.ui.theme.VeryLightGray
import kotlinx.coroutines.selects.select

@Composable
fun CategoryCard(modifier: Modifier = Modifier,
                 categoryOccurrence: CategoryOccurrence,
                 onCategoryClick : (String) -> Unit,
                 selected : Boolean) {


    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = modifier.clickable {
            onCategoryClick(categoryOccurrence.categoryName)
        },
        colors = CardDefaults.cardColors(containerColor = if (selected) Color(0xfffcfad9) else Color(0xffc2dcfd))
    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = categoryOccurrence.categoryName,
                modifier = modifier
                    .padding(8.dp),
                textAlign = TextAlign.Center,
                fontFamily = Sen,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(Modifier.width(9.dp))
            Text(
                text = categoryOccurrence.count.toString(),
                modifier = modifier
                    .padding(9.dp),
                textAlign = TextAlign.Center,
                fontFamily = Sen,
                fontWeight = FontWeight.Light
            )
        }
    }
}


@Composable
fun CategoriesLazyRow(modifier: Modifier = Modifier,
                      homeScreenViewModel: HomeScreenViewModel){

    val categoriesUiState by homeScreenViewModel.categoriesUiState.collectAsState()
    val notesUiState by homeScreenViewModel.notesUiState.collectAsState()
    val selectedCategory by homeScreenViewModel.sortingUiState.collectAsState()
    val categoriesOccurrences = homeScreenViewModel.getCategoriesCount(categoriesUiState.repoList,notesUiState.notesList)



    // Use datastore to fix this problem

    LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
        content = {
            items(categoriesOccurrences) {category ->
                CategoryCard(
                    categoryOccurrence = category,
                    onCategoryClick = {
                        //Write to category to datastore
                        homeScreenViewModel.updateCategorySorting(it)
                        //selectedCategory = if( selectedCategory != category.categoryName) category.categoryName else "All notes"
                    },
                    selected = selectedCategory.sortCategoryString == category.categoryName
                )
            }
        })
}


@Preview
@Composable
fun PreviewCategoryCard(modifier: Modifier = Modifier) {
    val dummyCategory = CategoryOccurrence("My Category",10)
    //CategoryCard(modifier,dummyCategory)
}

@Preview
@Composable
fun PreviewCategoryCardList(modifier: Modifier = Modifier) {
    //CategoriesLazyRow(modifier, homeScreenViewModel)
}