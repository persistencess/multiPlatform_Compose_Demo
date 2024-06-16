package entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
abstract class BaseResp {
    abstract val code: Int
    abstract val message: String
}

@Serializable
class TimeEntityResp(val result: TimeEntity, override val code: Int, override val message: String) :
    BaseResp()
@Serializable
class TimeEntity(
    // 当返回与定义不一致时
//    @JsonNames("date2")
    val date: String,
    @SerialName("weekday") val weekDay: String
)