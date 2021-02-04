package com.devtech.islamicdictionary.ui.dictionary

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.devtech.islamicdictionary.data.DictionaryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import javax.inject.Inject


@HiltViewModel
class DictionaryViewModel @Inject constructor(
    private val repository: DictionaryRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    companion object {
        const val KEY_SEARCH = "subsearch"
        const val DEFAULT_SEARCH = ""
        const val KEY_TYPE = "keytype"
    }

    init {
        if (!savedStateHandle.contains(KEY_SEARCH)) {
            savedStateHandle.set(KEY_SEARCH, DEFAULT_SEARCH)
            savedStateHandle.set(KEY_TYPE, "0")
        }
    }

    private val clearListCh = Channel<Unit>(Channel.CONFLATED)

    @FlowPreview
    @ExperimentalCoroutinesApi
    @ExperimentalPagingApi
    val posts = flowOf(
        clearListCh.receiveAsFlow().map { PagingData.empty() },
        savedStateHandle.getLiveData<String>(KEY_SEARCH)
            .asFlow()
            .combine(savedStateHandle.getLiveData<String>(KEY_TYPE).asFlow()) { search, type ->
                arrayOf(search, type)
            }.flatMapLatest {
                repository.getSearchDictionary(type = it[1], search = it[0], pageSize = 10)
            }
            .cachedIn(viewModelScope)
    ).flattenMerge(2)

    fun shouldShowSearch(
        subsearch: String
    ) = savedStateHandle.get<String>(KEY_SEARCH) != subsearch

    private fun shouldShowType(
        type: String
    ) = savedStateHandle.get<String>(KEY_TYPE) != type

    fun showSearch(type: String, search: String) {
        if (!shouldShowSearch(search) && !shouldShowType(type)) return
        clearListCh.offer(Unit)
        savedStateHandle.set(KEY_SEARCH, search)
        savedStateHandle.set(KEY_TYPE, type)
    }
}