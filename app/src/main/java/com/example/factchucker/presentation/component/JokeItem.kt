package com.example.factchucker.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.factchucker.domain.model.Joke
import com.example.factchucker.presentation.ui.theme.*
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.MainAxisAlignment

@Composable
fun JokeItem(
    joke: Joke,
    onItemClick: (Joke) -> Unit
) {
    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .background(transparentDark)
            .clickable { onItemClick(joke) }
            .padding(30.dp),
        mainAxisAlignment = MainAxisAlignment.SpaceBetween
    ) {
        ConstraintLayout {

            val (jokeText, foundText, div1, div2, div3) = createRefs()

            Divider(thickness = 10.dp, color = transparentDark, modifier = Modifier
                .constrainAs(div1) {
                    top.linkTo(parent.top)
                })

            Text(
                text = joke.value,
                fontSize = 16.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.ExtraLight,
                color = infoText2,
                textAlign = TextAlign.Justify,
                style = TextStyle(
                    shadow = Shadow(
                        color = transparentLight,
                        offset = Offset(1f,1f),
                        blurRadius = 2f
                    )
                ),
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .constrainAs(jokeText) {
                        top.linkTo(div1.bottom)
                    }
            )

            Divider(thickness = 10.dp, color = transparentDark, modifier = Modifier
                .constrainAs(div2) {
                    top.linkTo(jokeText.bottom)
                })

            Text(
                text = "Found at: ${joke.url}",
                fontSize = 12.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.ExtraLight,
                color = infoText1,
                textAlign = TextAlign.Center,
                style = TextStyle(
                    shadow = Shadow(
                        color = transparentLight,
                        offset = Offset(1f,1f),
                        blurRadius = 2f
                    )
                ),
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .constrainAs(foundText) {
                        top.linkTo(div2.bottom)
                    }
            )

            Divider(thickness = 10.dp, color = transparentDark, modifier = Modifier
                .constrainAs(div3) {
                    top.linkTo(foundText.bottom)
                })
        }
    }
}