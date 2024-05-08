package com.example.news.data

import com.example.news.NewsDatabase
import com.example.news.data.model.Article
import com.example.news.database.models.ArticleDBO
import com.example.newsapi.NewsApi
import com.example.newsapi.models.ArticleDTO
import com.example.newsapi.models.ResponseDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.onEach
import java.io.IOException

class ArticlesRepository(
    private val database: NewsDatabase,
    private val api: NewsApi
) {

    fun getAll(): Flow<RequestResult<List<Article>>> {

        val cachedAllArticles: Flow<RequestResult.Success<List<ArticleDBO>>> = getAllFromDatabase()

        val remoteArticle: Flow<RequestResult<*>> = getAllFromServer()

        cachedAllArticles.map {

        }

        return cachedAllArticles.combine(remoteArticle) {

        }
    }

    private fun getAllFromDatabase(): Flow<RequestResult.Success<List<ArticleDBO>>> {
        return database.articlesDao
            .getAll()
            .map { RequestResult.Success(it) }
    }

    private fun getAllFromServer(): Flow<RequestResult.Success<List<ArticleDBO>?>> {
        return flow { emit(api.everything()) }
            .map { result ->
                if (result.isSuccess) {
                    val response: ResponseDTO<ArticleDTO> = result.getOrThrow()
                    RequestResult.Success(response.articles)
                } else {
                    RequestResult.Error(null)
                }
            }
            .filterIsInstance<RequestResult.Success<List<ArticleDTO>?>>()
            .map { requestResult: RequestResult.Success<List<ArticleDTO>?> ->
                requestResult.requireData()
                    .map { articleDTO -> articleDTO.toArticleDbo() }
                    .let { RequestResult.Success<List<ArticleDBO>?>(it) }
            }.onEach { requestResult ->
                database.articlesDao.insert(requestResult.requireData())
            }
    }

    suspend fun search(query: String): Flow<Article> {
        api.everything(query)
        return TODO("Not Implemented")
    }
}


sealed class RequestResult<E>(internal val data: E?) {
    class InProgress<E>(data: E?) : RequestResult<E>(data)
    class Success<E>(data: E?) : RequestResult<E>(data)
    class Error<E>(data: E?) : RequestResult<E>(data)
}

internal fun <T : Any> RequestResult<T?>.requireData(): T = checkNotNull(data)
