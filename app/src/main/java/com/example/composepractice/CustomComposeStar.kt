package com.example.composepractice

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun MakeCustomComposeView() {
    Column() {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Image(painter = painterResource(id = R.drawable.ic_icon_star), contentDescription = "一個星星",
                modifier = Modifier.size(80.dp))
        }
        
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 8.dp), horizontalArrangement = Arrangement.Center) {
            Rotation(modifier = Modifier.scale(scaleX = -1f, scaleY = 1f))
            Text(text = "Default")
            Rotation(modifier = Modifier.scale(scaleX = 1f, scaleY = 1f))
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Brick(Color.Blue)
            Brick(Color.Green)
            Brick(Color.Red)
        }
    }
}

@Preview
@Composable
fun BrickPreview() {
    Brick(color = Color.Blue)
}

@Composable
fun Brick(color: Color) {
    Box(modifier = Modifier
        .size(80.dp)
        .border(2.dp, Color.Black, RectangleShape)
        .padding(8.dp)
        .background(color))
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

