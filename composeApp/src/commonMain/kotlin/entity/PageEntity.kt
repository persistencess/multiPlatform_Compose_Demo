package entity

import kotlinx.serialization.Serializable

@Serializable
data class PageEntity(val list: List<ImageEntity>, val total: Int) {
}

@Serializable
data class ImageEntity(val id: Int, val url: String)