package com.from.styleassisthaircuts.view

import android.widget.RatingBar
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.compose.ui.viewinterop.AndroidView
import com.from.styleassisthaircuts.model.infoList
import com.from.styleassisthaircuts.ui.theme.Orange200
import com.google.accompanist.pager.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield
import kotlin.math.absoluteValue


@ExperimentalPagerApi
@Preview
@Composable
fun ViewPagerSlider() {

    val pagerState  = rememberPagerState(
        pageCount = infoList.size,
        initialPage = 0
    )

    LaunchedEffect(Unit){
        while (true) {
            yield()
            delay(2500)
            pagerState.animateScrollToPage(
                page = (pagerState.currentPage + 1) % (pagerState.pageCount),
                animationSpec = tween(600)
            )
        }
    }

    //Modifier.background(Color.Gray)

    Column(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier
            .height(50.dp)
            .fillMaxWidth()
            //.border(dp = 4, color = Color.Black, shape = RoundedCornerShape(10.dp))
            //.scale(50F, 50F,)
            //.clip(shape = 10)
            //.CutCornerShape(10.dp)
            .background(color = Orange200),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,) {
            Text(
                text = "Style Assist Haircuts",
                fontFamily = FontFamily.Cursive,
                color = Color.White,
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold,

            )
        }

        Spacer(modifier = Modifier.height(30.dp))
        HorizontalPager(state = pagerState,
            modifier = Modifier
                .weight(1f)
                .padding(0.dp, 40.dp, 0.dp, 40.dp)) {
                page ->
                Card(modifier = Modifier.graphicsLayer {
                val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue
                    lerp(
                        start = 0.85f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    ).also { scale ->
                        scaleX = scale
                        scaleY = scale
                    }
                    alpha = lerp(
                        start = 0.5f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    )

                }
                .fillMaxWidth()
                .padding(25.dp, 0.dp, 25.dp, 0.dp),
                shape = RoundedCornerShape(20.dp)) {

                val newInfo = infoList[page]
                 Box(modifier = Modifier
                     .fillMaxSize()
                     .background(Color.LightGray)
                     .align(Alignment.Center)) {

                     Image(painter = painterResource(id = newInfo.imgUri),
                         contentDescription = "Image",
                         contentScale = ContentScale.Crop,
                         modifier = Modifier.fillMaxSize()
                     )

                     Column(modifier = Modifier
                         .align(Alignment.BottomStart)
                         .padding(15.dp)) {

                         Text(
                             text = newInfo.title,
                             style = MaterialTheme.typography.h5,
                             color = Color.White,
                             fontWeight = FontWeight.Bold
                         )

                         val ratingBar = RatingBar(
                             //LocalContext.current, null, R.attr.ratingBarStyleSmall
                             LocalContext.current, null, androidx.core.R.attr.fontProviderFetchStrategy
                         ).apply {
                             rating = newInfo.rating
//                             progressDrawable.setColorFilter(
//                                 android.graphics.Color.parseColor("#FF0000"),
//                                 PorterDuff.Mode.SRC_ATOP
//                             )
                         }

                         AndroidView(factory ={ratingBar},
                             modifier = Modifier.padding(0.dp,8.dp,0.dp,0.dp)
                         )
                         Text(
                             text = newInfo.desc,
                             style = MaterialTheme.typography.body1,
                             color = Color.White,
                             fontWeight = FontWeight.Normal,
                             modifier = Modifier.padding(0.dp,8.dp,0.dp,0.dp)
                         )
                     }

                 }


            }

        }

        //Horizontal dot indicator
        HorizontalPagerIndicator(
            pagerState = pagerState,modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp)
        )

    }

}