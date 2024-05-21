package com.fjr619.currencykmmcompose.ui.screens.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.dropUnlessResumed
import com.fjr619.currencykmmcompose.ui.screens.home.backKey

val keys = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "C", backKey)

@Composable
fun KeyboardButton(
    modifier: Modifier = Modifier,
    key: String,
    backgroundColor: Color,
    onClick: (String) -> Unit
) {
    Box(
        modifier = modifier
            .padding(8.dp)
            .clip(CircleShape)
            .background(color = backgroundColor)
            .clickable(
                onClick = dropUnlessResumed {
                    onClick(key)
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        if (key != backKey) {
            Text(text = key, fontSize = 32.sp, color = Color.White)
        } else {
            Icon(
                modifier = Modifier.size(32.dp).rotate(270f),
                imageVector = Icons.Filled.KeyboardArrowUp,
                contentDescription = "",
                tint = Color.White
            )
        }

    }
}