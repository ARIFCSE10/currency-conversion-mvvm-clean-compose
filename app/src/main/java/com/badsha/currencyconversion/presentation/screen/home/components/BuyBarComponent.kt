package com.badsha.currencyconversion.presentation.screen.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalTextInputService
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp


@Composable
fun BuyBarComponent() {
    var showMenu by remember { mutableStateOf(false) }
    var text by remember { mutableStateOf("") }
    val focus = LocalTextInputService.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Color.Green.copy(alpha = 0.1f), shape = RoundedCornerShape(8.dp),
            )
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
    ) {
        Text(
            "You Take",
            style = MaterialTheme.typography.body2,
            textAlign = TextAlign.Center,
        )
        TextField(
            value = text,
            onValueChange = { text = it },
            maxLines = 1,
            textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.End),
            singleLine = true,
            placeholder = {
                Text(
                    text = "100",
                    textAlign = TextAlign.End,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                autoCorrect = false,
            ),
            keyboardActions = KeyboardActions(onDone = { focus?.hideSoftwareKeyboard() }),
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
        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .clickable {
                    showMenu = !showMenu
                }
                .padding(16.dp)

        ) {
            Text(
                "EUR", style = MaterialTheme.typography.subtitle1, fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(16.dp))
            Box {
                Card(
                    backgroundColor = Color.Green.copy(green = 0.75f),
                    modifier = Modifier
                        .clip(shape = CircleShape)
                        .size(32.dp, 32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowUp,
                        tint = Color.White,
                        contentDescription = "You take",
                    )
                }
                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false },
                ) {
                    DropdownMenuItem(onClick = { showMenu = false }) {
                        Text(
                            text = "EUR",
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    DropdownMenuItem(onClick = { showMenu = false }) {
                        Text(
                            text = "USD",
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}
