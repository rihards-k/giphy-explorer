package com.test.giphyexplorer.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.Saver
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.test.giphyexplorer.ui.theme.Purple40

@ExperimentalMaterial3Api
@Composable
fun SearchBar(state: SearchState = rememberSearchState(), onValueChange: (String) -> Unit) {
    TextField(
        value = state.query,
        onValueChange = { value ->
            state.query = value
            onValueChange(value.text)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        textStyle = TextStyle(color = Purple40, fontSize = 18.sp),
        leadingIcon = {
            Icon(
                Icons.Default.Search,
                contentDescription = "",
                modifier = Modifier
                    .padding(15.dp)
                    .size(24.dp)
            )
        },
        singleLine = true,
        shape = RoundedCornerShape(8.dp),
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.White,
            cursorColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}

@Composable
private fun rememberSearchState(
    query: TextFieldValue = TextFieldValue("")
): SearchState {
    return remember {
        SearchState(
            query = query
        )
    }
}

val SearchStateSaver = Saver<SearchState, String>(
    save = { it.query.text },
    restore = { SearchState(query = TextFieldValue(it)) }
)


class SearchState(
    query: TextFieldValue
) {
    var query by mutableStateOf(query)
}

@ExperimentalMaterial3Api
@Preview(showBackground = true)
@Composable
fun SearchFieldPreview() {
    val searchState = remember { SearchState(TextFieldValue("")) }
    SearchBar(searchState) { }
}