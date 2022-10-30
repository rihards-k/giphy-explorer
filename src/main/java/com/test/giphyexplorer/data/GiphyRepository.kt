package com.test.giphyexplorer.data

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

interface GiphyRepository {
    fun getSearchResults(query: String): Flow<PagingData<GifItem>>
}