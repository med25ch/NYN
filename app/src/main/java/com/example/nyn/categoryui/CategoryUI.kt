package com.example.nyn.categoryui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nyn.ui.theme.Sen

@Composable
fun CategoryCard(modifier: Modifier = Modifier) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "CategoryName",
                modifier = modifier
                    .padding(8.dp),
                textAlign = TextAlign.Center,
                fontFamily = Sen,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(Modifier.width(9.dp))
            Text(
                text = "12",
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
fun CategoriesLazyRow(modifier: Modifier = Modifier){
    LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp)){
        items(8){
            CategoryCard(modifier)
        }
    }
}


@Preview
@Composable
fun PreviewCategoryCard(modifier: Modifier = Modifier) {
    CategoryCard(modifier)
}

@Preview
@Composable
fun PreviewCategoryCardList(modifier: Modifier = Modifier) {
    CategoriesLazyRow(modifier)
}