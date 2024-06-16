package service

import entity.TimeEntityResp
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import network.Network

interface TimeService {
    suspend fun getTime(): TimeEntityResp

    companion object {
        val instance = TimeServiceImpl(Network.client)
    }
}

class TimeServiceImpl(private val http: HttpClient) : TimeService {
    override suspend fun getTime(): TimeEntityResp =
        http.get("/api/getTime").body<TimeEntityResp>()
}