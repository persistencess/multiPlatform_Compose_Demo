package ui.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import ui.components.PageItem

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen() {
    val page = rememberPagerState(initialPage = 0, pageCount = { 10 })
    HorizontalPager(
        state = page,
        pageSpacing = 16.dp,
        contentPadding = PaddingValues(all = 32.dp)
    ) {
        PageItem()
    }
}