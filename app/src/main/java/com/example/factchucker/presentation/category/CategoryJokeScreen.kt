package com.example.factchucker.presentation.category

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.factchucker.R
import com.example.factchucker.presentation.component.JokeItem
import com.example.factchucker.presentation.ui.theme.*

@Composable
fun CategoryJokeScreen(
    viewModel: CategoryJokeViewModel = hiltViewModel(),
    navController: NavController,
    category: String?
) {
    val state = viewModel.state.value
    val capString = category.toString().replaceFirstChar { it.uppercase() }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(background)
    ) {
        ConstraintLayout {
            val (title, joke, randomButton) = createRefs()

            Text(
                text = stringResource(R.string.chuck)+" $capString "+stringResource(R.string.fact),
                fontSize = 23.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.ExtraBold,
                color = titleText,
                style = TextStyle(
                    shadow = Shadow(
                        color = transparentLight,
                        offset = Offset(1f,1f),
                        blurRadius = 3f
                    )
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .background(color = transparentLight)
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 20.dp)
                    .constrainAs(title) {
                        top.linkTo(parent.top)
                    }
            )

            LazyColumn(modifier = Modifier
                .fillMaxWidth()
                .constrainAs(joke) {
                    top.linkTo(title.bottom, margin = 10.dp)
                }
            ) {
                item {
                    JokeItem(
                        joke = state.joke,
                        onItemClick = { viewModel.getJoke(category) }
                    )
                    Divider()
                }
            }

            Button(
                onClick = { navController.navigate("joke_screen") },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(buttonBG)
                    .padding(horizontal = 20.dp, vertical = 20.dp)
                    .constrainAs(randomButton) {
                        top.linkTo(joke.bottom, margin = 10.dp)
                    }
            ) {
                Text(
                    text = stringResource(R.string.getRandomFact),
                    fontSize = 16.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.ExtraBold,
                    color = buttonText,
                    textAlign = TextAlign.Center
                )
            }
        }

        if (state.error.isNotBlank()) {
            Text(
                text = state.error,
                color = MaterialTheme.colors.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .align(Alignment.Center)
            )
        }
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}
