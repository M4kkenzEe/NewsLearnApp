package com.example.newsapi.models

import com.example.newsapi.utils.DateTimeUTCSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.Date

/**
 * source": {
 * "id": "the-next-web",
 * "name": "The Next Web"
 * },
 * "author": "The Conversation",
 * "title": "Why workers are leaving fossil fuel jobs — and what to do if you feel like ‘climate quitting’",
 * "description": "Are you frustrated with your employer’s lack of commitment to sustainability? Maybe “climate quitting” is for you. Climate quitting means leaving your job due to concerns about your employer’s impact on the climate or because you want to work directly on addr…",
 * "url": "https://thenextweb.com/news/workers-leaving-fossil-fuel-jobs-climate-quitting",
 * "urlToImage": "https://img-cdn.tnwcdn.com/image/tnw-blurple?filter_last=1&fit=1280%2C640&url=https%3A%2F%2Fcdn0.tnwcdn.com%2Fwp-content%2Fblogs.dir%2F1%2Ffiles%2F2024%2F04%2FAdd-a-heading.jpg&signature=b368618f776c630e21c1b26c041cad7f",
 * "publishedAt": "2024-04-04T10:34:57Z",
 * "content": "Are you frustrated with your employers lack of commitment to sustainability? Maybe climate quitting is for you. Climate quitting means leaving your job due to concerns about your employers impact on … [+5901 chars]"
 * },
 */

@Serializable
data class ArticleDTO(
    @SerialName("source") val source: SourceDTO,
    @SerialName("author") val author: String,
    @SerialName("title") val title: String,
    @SerialName("description") val description: String,
    @SerialName("url") val url: String,
    @SerialName("urlToImage") val urlToImage: String,
    @SerialName("publishedAt") @Serializable(with = DateTimeUTCSerializer::class) val publishedAt: Date,
    @SerialName("content") val content: String,
)
