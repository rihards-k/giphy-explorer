package com.test.giphyexplorer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.test.giphyexplorer.ui.components.SearchBar
import com.test.giphyexplorer.ui.components.SearchState
import com.test.giphyexplorer.ui.components.SearchStateSaver
import com.test.giphyexplorer.ui.components.TopBar
import com.test.giphyexplorer.ui.screens.EmptyInputScreen
import com.test.giphyexplorer.ui.screens.ErrorScreen
import com.test.giphyexplorer.ui.screens.LoadedScreen
import com.test.giphyexplorer.ui.theme.GiphyExplorerTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    @FlowPreview
    @ExperimentalCoroutinesApi
    @ExperimentalLifecycleComposeApi
    @ExperimentalMaterial3Api
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GiphyExplorerTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = { TopBar() },
                ) { padding ->
                    Box(modifier = Modifier.padding(padding)) {
                        val searchState = rememberSaveable(saver = SearchStateSaver) {
                            SearchState(TextFieldValue(""))
                        }
                        Column {
                            SearchBar(
                                state = searchState,
                                onValueChange = { value ->
                                    viewModel.search(value)
                                })

                            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                            when (uiState) {
                                is PageUiState.Loaded -> LoadedScreen(state = uiState as PageUiState.Loaded)
                                PageUiState.Error -> ErrorScreen()
                                PageUiState.EmptyInput -> EmptyInputScreen()
                            }
                        }
                    }
                }
            }
        }
    }
}