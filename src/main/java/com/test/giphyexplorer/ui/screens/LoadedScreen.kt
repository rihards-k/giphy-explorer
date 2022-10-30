package com.test.giphyexplorer.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.test.giphyexplorer.PageUiState
import com.test.giphyexplorer.data.GifItem
import com.test.giphyexplorer.ui.components.GifListItem
import com.test.giphyexplorer.ui.components.ListItemError
import com.test.giphyexplorer.ui.components.ListItemLoading

@Composable
fun LoadedScreen(state: PageUiState.Loaded) {
    val lazyGiphyItems: LazyPagingItems<GifItem> = state.data.collectAsLazyPagingItems()

    LazyVerticalGrid(
        modifier = Modifier.padding(8.dp),
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            count = lazyGiphyItems.itemCount,
            key = { index -> lazyGiphyItems.peek(index)?.gifUrl ?: index }
        ) { index ->
            val item = lazyGiphyItems[index]
            if (item != null) {
                GifListItem(item)
            }
        }
    }

    lazyGiphyItems.apply {
        when {
            loadState.refresh is LoadState.Loading -> {
                ListItemLoading()
            }
            loadState.append is LoadState.Loading -> {
                ListItemLoading()
            }
            loadState.refresh is LoadState.Error -> {
                ListItemError()
            }
            loadState.append is LoadState.Error -> {
                ListItemError()
            }
        }
    }
}