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
import ui.services.Device
import ui.types.PageModel
import viewmodel.HomeViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen() {
    val homeViewModel = HomeViewModel()
    val page = rememberPagerState(initialPage = 0, pageCount = { 10 })
    HorizontalPager(
        state = page,
        pageSpacing = 16.dp,
        contentPadding = PaddingValues(
            top = 32.dp,
            end = 32.dp,
            start = 32.dp,
            // 使用底部高度
            bottom = Device.getBottomSafeAreaHeight().dp + 32.dp
        )
    ) {
        PageItem(
            PageModel(
                "http://netease.store/upload/2022-08-18/1660811651503.jpg",
                "业精于勤荒于嬉,行成于思毁于随",
                "——solitude",
                "2024.06.15",
                "星期六"
            )
        )
    }
}