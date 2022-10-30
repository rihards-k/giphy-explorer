package com.test.giphyexplorer.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.test.giphyexplorer.R
import com.test.giphyexplorer.data.GifItem
import com.test.giphyexplorer.ui.theme.Purple40

@Composable
fun GifListItem(item: GifItem) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AsyncImage(
            modifier = Modifier
                .height(120.dp)
                .fillMaxWidth(),

            model = ImageRequest.Builder(LocalContext.current)
                .decoderFactory(ImageDecoderDecoder.Factory())
                .data(item.gifUrl)
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.ic_baseline_gif_24),
            contentDescription = item.title
        )

        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Spacer(modifier = Modifier.padding(vertical = 4.dp))
            Text(
                text = item.title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 12.sp,
            )
        }
    }
}

@Composable
fun ListItemLoading() {
    Box(
        modifier = Modifier
            .height(100.dp)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = Purple40)
    }
}

@Composable
fun ListItemError() {
    Box(
        modifier = Modifier
            .height(100.dp)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Something went wrong :(")
    }
}