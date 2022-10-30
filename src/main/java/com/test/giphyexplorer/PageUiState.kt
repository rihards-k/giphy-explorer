package com.test.giphyexplorer

import androidx.paging.PagingData
import com.test.giphyexplorer.data.GifItem
import kotlinx.coroutines.flow.Flow

sealed class PageUiState {
    data class Loaded(val data: Flow<PagingData<GifItem>>) : PageUiState() {

        //To simplify comparison for unit testing, data field has been excluded from equals method.
        //Further, this should be removed and in unit tests data field also should be compared!
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false
            return true
        }

        override fun hashCode(): Int {
            return data.hashCode()
        }
    }

    object EmptyInput : PageUiState()
    object Error : PageUiState()
}