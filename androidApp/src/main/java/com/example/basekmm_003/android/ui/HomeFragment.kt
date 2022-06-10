package com.example.basekmm_003.android.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import coil.compose.rememberImagePainter
import com.example.basekmm_003.android.MovieStates
import com.example.basekmm_003.android.vm.MovieDetailViewModel
import com.example.basekmm_003.presentation.PreviewMovieDisplay
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {
    private val viewModel: MovieDetailViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val screenState: MovieStates.State = viewModel.uiState.collectAsState().value

                MaterialTheme {
                    Surface {
                        when (screenState) {
                            is MovieStates.State.ShowLatestMovies -> {
                                if (screenState.lastMovies is BaseState.Success) {
                                    MovieList(input = screenState.lastMovies.data)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MovieList(
    input: List<PreviewMovieDisplay>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Ultimos estrenos",
            fontSize = 30.sp,
        )
        Spacer(modifier = Modifier.height(10.dp))
        LazyColumn {
            items(input) { movie ->
                MovieCard(
                    movieDisplay = movie
                )
            }
        }
    }
}

@Composable
fun MovieCard(
    movieDisplay: PreviewMovieDisplay,
) {
    androidx.compose.material.Card(elevation = 3.dp) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
                .background(Color.White),
            verticalAlignment = Alignment.CenterVertically
        ) {
            LoadImageFromUrl(
                movieDisplay.posterPath,
                "",
                Modifier.size(100.dp)
            )
            Column {
                Text(text = movieDisplay.title.orEmpty())
            }
        }
    }
}

@Composable
fun LoadImageFromUrl(
    url: String?,
    baseImageUrl: String?,
    modifier: Modifier = Modifier,
    @DrawableRes placeholderResId: Int? = null,
) {
    val painter = rememberImagePainter(
        data = baseImageUrl + url,
        builder = { placeholderResId?.let { placeholder(it) } }
    )
    Image(
        modifier = modifier,
        painter = painter,
        contentDescription = "",
    )
}
