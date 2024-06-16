package ui.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import ui.components.PageItem
import ui.services.Device
import ui.types.PageModel
import viewmodel.HomeAction
import viewmodel.HomeUIState
import viewmodel.HomeViewModel

object HomeScreen : Screen {
    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun Content() {
//        val homeViewModel = HomeViewModel()
        val homeViewModel = rememberScreenModel { HomeViewModel() }
        val uiState by homeViewModel.uiState.collectAsState()
        val page = rememberPagerState(initialPage = 0, pageCount = { 10 })

        when (uiState) {
            is HomeUIState.Loading -> Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator()
            }

            is HomeUIState.Error -> Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text((uiState as HomeUIState.Error).msg, modifier = Modifier.clickable {
                    homeViewModel.dispatch(HomeAction.FetchData)
                })
            }

            is HomeUIState.Success -> HorizontalPager(
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
                val c = uiState as HomeUIState.Success
                PageItem(
                    /*PageModel(
                        "http://netease.store/upload/2022-08-18/1660811651503.jpg",
                        "业精于勤荒于嬉,行成于思毁于随",
                        "——solitude",
                        "2024.06.15",
                        "星期六"
                    )*/
                    PageModel(
                        c.imgUrl,
                        c.content,
                        c.author,
                        c.date,
                        c.week
                    )
                )
            }
        }

    }
}

/*
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen() {

}*/
