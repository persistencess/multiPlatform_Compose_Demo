package network

import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpClientPlugin
import io.ktor.client.statement.HttpResponseContainer
import io.ktor.client.statement.HttpResponsePipeline
import io.ktor.util.AttributeKey
import io.ktor.utils.io.ByteReadChannel
import io.ktor.utils.io.charsets.Charset
import io.ktor.utils.io.core.toByteArray
import io.ktor.utils.io.readUTF8Line
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

class BaseResponsePlugin(private val config: BaseResponseConfig) {
    class BaseResponseConfig {
        var successCode: String = "0"
        var keysForStatus = listOf("status")
        var keysForMessage = listOf("message")
        var keysForData = listOf("data")
    }

    companion object : HttpClientPlugin<BaseResponseConfig, BaseResponsePlugin> {
        override val key: AttributeKey<BaseResponsePlugin>
            get() = AttributeKey("BaseResponsePlugin")

        override fun prepare(block: BaseResponseConfig.() -> Unit): BaseResponsePlugin {
            return BaseResponsePlugin(BaseResponseConfig().apply(block))
        }

        override fun install(plugin: BaseResponsePlugin, scope: HttpClient) {
            scope.responsePipeline.intercept(HttpResponsePipeline.Receive) {
                val responseConfig = plugin.config
                val response = it.response
                if (response !is ByteReadChannel) return@intercept
                val bodyText = response.readUTF8Line() ?: ""
                val json = Json {
                    useAlternativeNames = true
                    ignoreUnknownKeys = true
                }
                // 将取出来的json解析成JsonElement
                val jsonElement = json.parseToJsonElement(bodyText)
                if (jsonElement is JsonNull || jsonElement !is JsonObject) {
                    throw JsonException(ExceptionCode.JSON_NULL.toString(), "数据为空")
                }

                val jsonObject = jsonElement.jsonObject
                // 获取status
                var status = ""
                for (statusKey in responseConfig.keysForStatus) {
                    val tmp = jsonObject[statusKey]
                    if (tmp != null && tmp !is JsonNull && tmp is JsonPrimitive) {
                        status = tmp.jsonPrimitive.content.trim()
                        break
                    }
                }
                // 获取message
                var message = ""
                for (messageKey in responseConfig.keysForMessage) {
                    val tmp = jsonObject[messageKey]
                    if (tmp != null && tmp !is JsonNull && tmp is JsonPrimitive) {
                        message = tmp.jsonPrimitive.content.trim()
                        break
                    }
                }
                if (status != responseConfig.successCode) {
                    throw JsonException(status, message)
                }
                // 获取message
                var dataJson = ""
                for (dataKey in responseConfig.keysForData) {
                    val tmp = jsonObject[dataKey]
                    if (tmp !is JsonNull) {
                        dataJson = json.encodeToString(tmp)
                        break
                    }
                }
                val byteArray = dataJson.toByteArray(Charset.forName("utf-8"))
                val responseContainer =
                    HttpResponseContainer(it.expectedType, ByteReadChannel(byteArray))
                proceedWith(responseContainer)
            }
        }

    }
}