package com.example.newsapi

import androidx.annotation.IntRange
import com.example.newsapi.models.ArticleDTO
import com.example.newsapi.models.Language
import com.example.newsapi.models.ResponseDTO
import com.example.newsapi.models.SortBy
import com.example.newsapi.utils.NewsApiKeyInterceptor
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.skydoves.retrofit.adapters.result.ResultCallAdapterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.Date

interface NewsApi {

    /**
     * API details [here](https://newsapi.org/docs/endpoints/everything)
     */
    @GET("/everything")
    suspend fun everything(
        @Query("q") query: String? = null,
        @Query("from") from: Date? = null,
        @Query("to") to: Date? = null,
        @Query("language") language: List<Language>? = null,
        @Query("sortBy") sortBy: SortBy? = null,
        @Query("pageSize") @IntRange(from = 0, to = 100) pageSize: Int = 100,
        @Query("page") @IntRange(from = 1) page: Int = 1,
    ): Result<ResponseDTO<ArticleDTO>>
}

fun NewsApi(
    baseUrl: String,
    okHttpClient: OkHttpClient? = null,
    apiKey: String = "",
    json: Json = Json,
    resultCallAdapter: ResultCallAdapterFactory = ResultCallAdapterFactory.create()
): NewsApi {
    return retrofit(baseUrl, okHttpClient, apiKey, json, resultCallAdapter).create()
}

private fun retrofit(
    baseUrl: String,
    okHttpClient: OkHttpClient?,
    apiKey: String,
    json: Json,
    resultCallAdapter: ResultCallAdapterFactory
): Retrofit {
    val jsonConverterFactory = json.asConverterFactory(MediaType.get("application/json"))

    val modifiedOkhttpClient = (okHttpClient?.newBuilder() ?: OkHttpClient.Builder())
        .addInterceptor(NewsApiKeyInterceptor(apiKey))
        .build()

    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(jsonConverterFactory)
        .addCallAdapterFactory(resultCallAdapter)
        .client(modifiedOkhttpClient)
        .build()
}


const val API_KEY = "239943496c0a4f249f3897882ee5cc6c"


