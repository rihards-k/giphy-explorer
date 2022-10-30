package com.test.giphyexplorer

import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.test.giphyexplorer.data.GiphyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: GiphyRepository
) : ViewModel() {

    private val viewModelState = MutableStateFlow<PageUiState>(PageUiState.EmptyInput)

    private val currentQuery = MutableStateFlow("")

    val uiState = viewModelState
        .asStateFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = viewModelState.value
        )

    init {
        currentQuery
            .debounce(LIVE_SEARCH_DELAY_MILLIS)
            .distinctUntilChanged()
            .mapLatest { searchQuery ->
                if (searchQuery.isBlank()) {
                    PageUiState.EmptyInput
                } else {
                    val results = repository.getSearchResults(searchQuery)
                        .cachedIn(viewModelScope)
                    PageUiState.Loaded(data = results)
                }
            }
            .onEach { state ->
                viewModelState.value = state
            }
            .onCompletion { cause ->
                if (cause != null) {
                    viewModelState.value = PageUiState.EmptyInput
                }
            }
            .catch {
                viewModelState.value = PageUiState.Error
            }
            .launchIn(viewModelScope)
    }

    fun search(query: String) {
        currentQuery.value = query
    }

    companion object {
        private const val LIVE_SEARCH_DELAY_MILLIS = 800L
    }
}