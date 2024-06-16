package network

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object Network {
    val client = HttpClient {
        // 默认配置参数 请求地址
        defaultRequest {
            url("https://api.apiopen.top")
        }
        //  日志插件
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
        }
        // json 解析
        install(ContentNegotiation) {
            json(Json { // application/json
                ignoreUnknownKeys = true
                useAlternativeNames = true // 防止返回字段键名不一致（会影响性能）
            })
        }
        // BaseResponse插件
        install(BaseResponsePlugin) {
            keysForStatus = listOf("code")
            successCode = "200"
            keysForData = listOf("result")
        }
    }
}