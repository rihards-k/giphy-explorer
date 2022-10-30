package com.test.giphyexplorer.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.test.giphyexplorer.api.GiphyService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GiphyRepositoryImpl @Inject constructor(
    private val giphyService: GiphyService
) : GiphyRepository {

    override fun getSearchResults(query: String): Flow<PagingData<GifItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = GiphyService.DEFAULT_PAGE_SIZE,
                prefetchDistance = 10 //Start loading next page when 10th element is reached
            ),
            pagingSourceFactory = { GiphyDataSource(giphyService, query) }
        ).flow
    }
}