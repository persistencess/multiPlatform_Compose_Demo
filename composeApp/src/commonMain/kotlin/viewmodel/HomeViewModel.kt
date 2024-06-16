package viewmodel

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import network.JsonException
import service.ImageService
import service.SentenceService
import service.TimeService

class HomeViewModel : ScreenModel {
    private val timeService = TimeService.instance
    private val imageService = ImageService.instance
    private val sentenceService = SentenceService.instance

    //    private val mainScope = MainScope()
    private val _uiState = MutableStateFlow<HomeUIState>(HomeUIState.Loading)
    val uiState = _uiState

    init {
        dispatch(HomeAction.FetchData)
    }

    fun dispatch(act: HomeAction) {
        when (act) {
            is HomeAction.FetchData -> {
                screenModelScope.launch {
                    runCatching {
                        // 同步处理
                        /* val time = timeService.getTime()
                         val image = imageService.getImage()
                         val sentences = sentenceService.getSent()*/
                        //   异步处理
                        val timeDeferred = async { timeService.getTime() }
                        val imageDeferred = async { imageService.getImage() }
                        val sentencesDeferred = async { sentenceService.getSent() }
                        val time = timeDeferred.await()
                        val image = imageDeferred.await()
                        val sentences = sentencesDeferred.await()
                        _uiState.value = HomeUIState.Success(
                            image.list[0].url,
                            sentences.name,
                            sentences.from,
                            time.date,
                            time.weekDay
                        )
                    }.onFailure {
                        if (it is JsonException) _uiState.value = HomeUIState.Error(it.message)
                        else _uiState.value = HomeUIState.Error("出错了，请点击重试")
                    }
                }
            }
        }
    }
}

// ui State
sealed class HomeUIState {
    object Loading : HomeUIState()
    data class Success(
        val imgUrl: String,
        val content: String,
        val author: String,
        val date: String,
        val week: String
    ) : HomeUIState()

    data class Error(val msg: String) : HomeUIState()
}

// 界面动作
sealed class HomeAction {
    object FetchData : HomeAction()
}