package com.test.giphyexplorer.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.test.giphyexplorer.api.GiphyService
import retrofit2.HttpException
import java.io.IOException

class GiphyDataSource(
    private val giphyService: GiphyService,
    private val query: String
) : PagingSource<Int, GifItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GifItem> {
        val position = params.key ?: 0
        return try {
            val response = giphyService.search(query = query, page = position)
            LoadResult.Page(
                data = response.data.map { data ->
                    GifItem(
                        gifUrl = data.images?.fixedWidthDownSampled?.webp,
                        title = data.title ?: ""
                    )

                },
                prevKey = if (position == 0) null else position - 1,
                nextKey = if (response.data.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, GifItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}