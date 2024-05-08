package com.example.newsapi.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**source": {
 * "id": "the-next-web",
 * "name": "The Next Web"
 * },
 */

@Serializable
data class SourceDTO(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String
)
