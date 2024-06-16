package service

import entity.TimeEntity
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import network.Network
import ui.types.PageModel

interface TimeService {
    suspend fun getTime(): TimeEntity

    companion object {
        val instance = TimeServiceImpl(Network.client)
    }
}

class TimeServiceImpl(private val http: HttpClient) : TimeService {
    override suspend fun getTime(): TimeEntity =
        http.get("/api/getTime").body<TimeEntity>()
}