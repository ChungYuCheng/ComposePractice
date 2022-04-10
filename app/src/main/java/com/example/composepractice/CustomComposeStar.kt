package com.example.composepractice

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@OptIn(ExperimentalAnimationApi::class)
@Preview
@Composable
fun MakeCustomComposeView() {
    var boxState by remember { mutableStateOf(BoxState.DEFAULT) }
    var starState by remember { mutableStateOf(StarState.Empty) }

//    var starTintState by remember { mutableStateOf(R.drawable.ic_icon_star) }
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }
    var rotation by remember { mutableStateOf(0f) }
    var defaultVisibility by remember { mutableStateOf(false) }
    val defaultColor = Color(0xFF6200EE)
    val rotateAnim by animateFloatAsState(
        targetValue = rotation,
        animationSpec = tween(
            easing = LinearEasing
        )
    )
    val transition = updateTransition(targetState = boxState, label = "")
    val tintColorAnim by transition.animateColor(label = "") { state ->
        when (state) {
            BoxState.Blue -> Color.Blue
            BoxState.Green -> Color.Green
            BoxState.Red -> Color.Red
            BoxState.DEFAULT -> Color(0xFFFBBF2F)
        }
    }

    Column {
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center) {
            Crossfade(targetState = starState) { state ->
                val imgId = if (state == StarState.Fill) {
                    R.drawable.ic_icon_full_star
                } else {
                    R.drawable.ic_icon_star
                }
                Image(painter = painterResource(id = imgId),
                    colorFilter = ColorFilter.tint(tintColorAnim),
                    contentDescription = "一個星星",
                    modifier = Modifier
                        .size(80.dp)
                        .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
                        .pointerInput(Unit) {
                            detectDragGestures { change, dragAmount ->
                                change.consumeAllChanges()
                                offsetX += dragAmount.x
                                offsetY += dragAmount.y
                                defaultVisibility = true
                            }
                        }
                        .clickable {
                            starState = StarState.Fill
                            defaultVisibility = true
                        }
                        .rotate(rotateAnim)
                )
            }
        }
        
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 16.dp), horizontalArrangement = Arrangement.Center) {
            Rotation(modifier = Modifier
                .size(30.dp)
                .scale(scaleX = -1f, scaleY = 1f)
                .clickable {
                    rotation += 30f
                    defaultVisibility = true
                })
            AnimatedVisibility (defaultVisibility) {
                Text(text = "Default", modifier = Modifier.clickable {
                    boxState = BoxState.DEFAULT
                    starState = StarState.Empty
                    offsetX = 0f
                    offsetY = 0f
                    rotation = 0f
                    defaultVisibility = false
                },
                    color = defaultColor)
            }
            Rotation(modifier = Modifier
                .size(30.dp)
                .scale(scaleX = 1f, scaleY = 1f)
                .clickable {
                    rotation -= 30f
                    defaultVisibility = true
                })
        }

        Row(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Brick(Color.Blue) {
                boxState = BoxState.Blue
                defaultVisibility = true
            }
            Brick(Color.Green) {
                boxState = BoxState.Green
                defaultVisibility = true
            }
            Brick(Color.Red) {
                boxState = BoxState.Red
                defaultVisibility = true
            }
        }
    }
}

private enum class BoxState {
    Blue, Green, Red, DEFAULT
}

private enum class StarState {
    Fill, Empty
}

@Preview
@Composable
fun BrickPreview() {
    Brick(color = Color.Blue) {}
}

@Composable
fun Brick(color: Color, changeColor: () -> Unit) {
    Box(modifier = Modifier
        .size(80.dp)
        .border(2.dp, Color.Black, RectangleShape)
        .padding(8.dp)
        .background(color)
        .clickable { changeColor.invoke() }
    )
}

@Preview
@Composable
fun RotationPreview() {
    Row {
        Rotation(Modifier.scale(scaleX = 1f, scaleY = 1f))
        Rotation(Modifier.scale(scaleX = -1f, scaleY = 1f))
    }
}

@Composable
fun Rotation(modifier: Modifier) {
    Image(painter = painterResource(id = R.drawable.ic_baseline_refresh), contentDescription = "rotation",
        modifier = modifier, colorFilter = ColorFilter.tint(Color.Black))

}