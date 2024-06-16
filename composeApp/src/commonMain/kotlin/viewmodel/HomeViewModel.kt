package viewmodel

import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import service.TimeService

class HomeViewModel {
    private val timeService = TimeService.instance
    private val mainScope = MainScope()

    init {
        mainScope.launch {
            val time = timeService.getTime()
            println(time)
        }
    }
}