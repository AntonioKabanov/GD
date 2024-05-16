package com.example.gd.presentation.components

import android.content.res.Configuration
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gd.R
import com.example.gd.domain.repositories.CategoriesRepository
import com.example.gd.domain.model.Category
import com.example.gd.ui.theme.GDTheme
import com.example.gd.ui.theme.colorBlack
import com.example.gd.ui.theme.colorGray

@Composable
fun CategoryTabs(
    categories: List<Category>,
    selectedCategory: Category,
    onCategorySelected: (Category) -> Unit
) {
    ScrollableTabRow(
        selectedTabIndex = categories.indexOf(selectedCategory),
        backgroundColor = MaterialTheme.colors.background,
        contentColor = MaterialTheme.colors.onSurface,
        edgePadding = 8.dp,
        indicator = {},
        divider = {}
    ) {
        categories.forEach { category ->
            CategoryTab(
                category = category,
                selected = category == selectedCategory,
                onClick = { onCategorySelected(category) },
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp)
            )
        }
    }
}

private enum class CategoryTabState { Selected, NotSelected }

@Composable
private fun CategoryTab(
    category: Category,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    val transition = updateTransition(
        if (selected) CategoryTabState.Selected else CategoryTabState.NotSelected,
        label = ""
    )

    val backgroundColor by transition.animateColor(label = "") { state ->
        when (state) {
            CategoryTabState.Selected -> Color.LightGray
            CategoryTabState.NotSelected -> colorGray
        }
    }
    val contentColor by transition.animateColor(label = "") { state ->
        when (state) {
            CategoryTabState.Selected -> colorBlack
            CategoryTabState.NotSelected -> colorBlack
        }
    }

    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(24.dp),
        color = backgroundColor,
        contentColor = contentColor,
    ) {
        Row(
            modifier = Modifier
                .clickable(onClick = onClick)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center

        ) {
            /*NetworkImage(
                imageUrl = category.image,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .wrapContentWidth()
                    .height(32.dp),
                previewPlaceholder = R.drawable.burger
            )
            Spacer(modifier = Modifier.width(8.dp))*/
            Text(
                text = category.name,
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W400
                ),
            )
        }
    }
}

@Composable
@Preview
fun CategoryTabsPreviewPreview() {
    CategoryTabs(
        categories = CategoriesRepository.getCategoriesData(),
        selectedCategory = CategoriesRepository.getCategoriesData().first(),
        onCategorySelected = {}
    )
}


@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
fun CategoryTabsPreviewDarkPreview() {
    CategoryTabs(
        categories = CategoriesRepository.getCategoriesData(),
        selectedCategory = CategoriesRepository.getCategoriesData().first(),
        onCategorySelected = {}
    )
}

@Preview("CategoryTab • Selected")
@Composable
private fun CategoryTabSelectedPreview() {
    GDTheme {
        CategoryTab(
            category = Category(
                id = "0",
                name = "Burgers",
                image = ""
            ),
            selected = true,
            onClick = {}
        )
    }
}

@Preview("CategoryTab • NotSelected")
@Composable
private fun CategoryTabNotSelectedPreview() {
    GDTheme {
        CategoryTab(
            category = Category(
                id = "0",
                name = "Burgers",
                image = ""
            ),
            selected = false,
            onClick = {}
        )
    }
}

@Preview("CategoryTab • Selected • Dark")
@Composable
private fun CategoryTabSelectedDarkPreview() {
    GDTheme(darkTheme = false) {
        CategoryTab(
            category = Category(
                id = "0",
                name = "Burgers",
                image = ""
            ),
            selected = true,
            onClick = {}
        )
    }
}

@Preview("CategoryTab • NotSelected • Dark")
@Composable
private fun CategoryTabNotSelectedDarkPreview() {
    GDTheme(darkTheme = false) {
        CategoryTab(
            category = Category(
                id = "0",
                name = "Burgers",
                image = ""
            ),
            selected = false,
            onClick = {}
        )
    }
}
