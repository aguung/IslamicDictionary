package com.devtech.islamicdictionary.ui.dictionary

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.devtech.islamicdictionary.data.DictionaryRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*


class DictionaryViewModel @ViewModelInject constructor(
        private val repository: DictionaryRepository,
        @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    companion object {
        const val KEY_SUBREDDIT = "subreddit"
        const val DEFAULT_SUBREDDIT = ""
        const val KEY_TYPE = "keytype"
    }

    init {
        if (!savedStateHandle.contains(KEY_SUBREDDIT)) {
            savedStateHandle.set(KEY_SUBREDDIT, DEFAULT_SUBREDDIT)
            savedStateHandle.set(KEY_TYPE, "0")
        }
    }

    private val clearListCh = Channel<Unit>(Channel.CONFLATED)

    @FlowPreview
    @ExperimentalCoroutinesApi
    @ExperimentalPagingApi
    val posts = flowOf(
            clearListCh.receiveAsFlow().map { PagingData.empty() },
            savedStateHandle.getLiveData<String>(KEY_SUBREDDIT)
                    .asFlow()
                    .combine(savedStateHandle.getLiveData<String>(KEY_TYPE).asFlow()) { search, type ->
                        arrayOf(search, type)
                    }.flatMapLatest {
                        repository.getSearchDictionary(it[1], it[0], 10)
                    }
                    .cachedIn(viewModelScope)
    ).flattenMerge(2)

    fun shouldShowSubreddit(
            subreddit: String
    ) = savedStateHandle.get<String>(KEY_SUBREDDIT) != subreddit

    private fun shouldShowType(
            type: String
    ) = savedStateHandle.get<String>(KEY_TYPE) != type

    fun showSubreddit(type: String, subreddit: String) {
        if (!shouldShowSubreddit(subreddit) && !shouldShowType(type)) return
        clearListCh.offer(Unit)
        savedStateHandle.set(KEY_SUBREDDIT, subreddit)
        savedStateHandle.set(KEY_TYPE, type)
    }
}