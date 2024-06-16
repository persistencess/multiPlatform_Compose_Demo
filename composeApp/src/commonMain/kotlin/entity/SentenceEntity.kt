package entity

import kotlinx.serialization.Serializable

@Serializable
data class SentenceEntity(
    val name:String,
    val from:String
)