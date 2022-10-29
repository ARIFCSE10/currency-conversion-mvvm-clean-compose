package com.badsha.currencyconversion.presentation.screen.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
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
import com.badsha.currencyconversion.domain.model.Rate


@Composable
fun BuyBarComponent(
    buyingCurrencyRate: Rate?,
    buyableCurrencyRates: List<Rate>,
    buyAmount: Double?,
    onAmountChange: (String) -> Unit,
    onSelectedCurrencyChange: (Rate) -> Unit,
    onTextFieldClick: () -> Unit,
) {
    var showCurrencyChangeMenu by remember { mutableStateOf(false) }
    val focus = LocalTextInputService.current

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed: Boolean by interactionSource.collectIsPressedAsState()
    if (isPressed) {
        onTextFieldClick()
    }

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
        TextField(value = buyAmount?.toString() ?: "",
            onValueChange = { amount ->
                focus?.let {
                    onAmountChange.invoke(amount)
                }
            },
            maxLines = 1,
            textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.End),
            singleLine = true,
            placeholder = {
                Text(
                    text = "100", textAlign = TextAlign.End, modifier = Modifier.fillMaxWidth()
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                autoCorrect = false,
            ),
            interactionSource = interactionSource,
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
                .clickable {
                    onTextFieldClick.invoke()
                })
        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .clickable {
                    showCurrencyChangeMenu = !showCurrencyChangeMenu
                }
                .padding(16.dp)

        ) {
            Text(
                buyingCurrencyRate?.currencyName ?: "--",
                style = MaterialTheme.typography.subtitle1,
                fontWeight = FontWeight.Bold
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
                    expanded = showCurrencyChangeMenu,
                    onDismissRequest = { showCurrencyChangeMenu = false },
                ) {
                    buyableCurrencyRates.forEach {
                        DropdownMenuItem(onClick = {
                            showCurrencyChangeMenu = false
                            onSelectedCurrencyChange.invoke(it)
                        }) {
                            Text(
                                text = it.currencyName,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }
        }
    }
}
