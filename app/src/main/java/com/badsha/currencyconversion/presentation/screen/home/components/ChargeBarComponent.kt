package com.badsha.currencyconversion.presentation.screen.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp


@Composable
fun ChargeBarComponent() {
    var showMenu by remember { mutableStateOf(false) }
    var text by remember { mutableStateOf("") }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Color.LightGray.copy(alpha = 0.1f), shape = RoundedCornerShape(8.dp),
            )
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
    ) {
        Text(
            "Charge",
            style = MaterialTheme.typography.body2,
            textAlign = TextAlign.Center,
        )
        TextField(
            value = text,
            onValueChange = { text = it },
            maxLines = 1,
            textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.End),
            singleLine = true,
            enabled = false,
            placeholder = {
                Text(
                    text = "100", textAlign = TextAlign.End, modifier = Modifier.fillMaxWidth()
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                autoCorrect = false,
            ),
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.DarkGray,
                disabledTextColor = Color.Transparent,
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.DarkGray,
                unfocusedIndicatorColor = Color.LightGray,
                disabledIndicatorColor = Color.Transparent
            ),
            modifier = Modifier
                .weight(1f)
                .height(56.dp)
                .padding(horizontal = 16.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                "EUR", style = MaterialTheme.typography.subtitle1, fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(16.dp))
            Card(
                backgroundColor = Color.LightGray,
                modifier = Modifier
                    .clip(shape = CircleShape)
                    .size(32.dp, 32.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    tint = Color.White,
                    contentDescription = "You take",
                )
            }
        }
    }
}
