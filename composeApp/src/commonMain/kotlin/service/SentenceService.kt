package service

import entity.SentenceEntity
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import network.Network

interface SentenceService {
    suspend fun getSent(): SentenceEntity

    companion object {
        val instance = SentenceServiceImpl(Network.client)
    }
}

class SentenceServiceImpl(private val http: HttpClient) : SentenceService {
    override suspend fun getSent(): SentenceEntity {
        return http.get("/api/sentences").body()
    }
}