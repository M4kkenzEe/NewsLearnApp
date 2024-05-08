package com.example.news.main

import androidx.lifecycle.ViewModel
import com.example.news.main.model.Article
import kotlinx.coroutines.flow.*

internal class NewsMainViewmodel : ViewModel() {

    private val _state = MutableStateFlow(State.None)

    val state: StateFlow<State>
        get() = _state.asStateFlow()

}

sealed class State {

    object None : State()
    class Loading : State()
    class Error : State()
    class Success(val articles: List<Article>) : State()
}