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
import androidx.compose.material.icons.filled.KeyboardArrowDown
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
import com.badsha.currencyconversion.domain.model.Currency


@Composable
fun SellBarComponent(
    sellingCurrency: Currency,
    sellAmount: Double?,
    sellableCurrencies: List<Currency>,
    onAmountChange: (String) -> Unit,
    onCurrencyChange: (Currency) -> Unit,
    onTextFieldClick: () -> Unit,
) {
    var showCurrencyChangeMenu by remember { mutableStateOf(false) }
    val focus = LocalTextInputService.current
    val amount: String = if (sellAmount == null) "" else String.format("%.2f", sellAmount)

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed: Boolean by interactionSource.collectIsPressedAsState()
    if (isPressed) {
        onTextFieldClick()
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Color.Red.copy(alpha = 0.1f), shape = RoundedCornerShape(8.dp),
            )
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
    ) {
        Text(
            "You Give",
            style = MaterialTheme.typography.body2,
            textAlign = TextAlign.Center,
        )
        TextField(
            value = amount,
            onValueChange = { amount ->
                focus?.let {
                    onAmountChange.invoke(amount)
                }
            },
            maxLines = 1,
            textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.End),
            singleLine = true,
            keyboardActions = KeyboardActions(onDone = { focus?.hideSoftwareKeyboard() }),
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
                    showCurrencyChangeMenu = !showCurrencyChangeMenu
                }
                .padding(16.dp)) {
            Text(
                sellingCurrency.name,
                style = MaterialTheme.typography.subtitle1,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(16.dp))
            Box {
                Card(
                    backgroundColor = Color.Red.copy(red = 0.75f),
                    modifier = Modifier
                        .clip(shape = CircleShape)
                        .size(32.dp, 32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        tint = Color.White,
                        contentDescription = "You give",
                    )
                }
                DropdownMenu(
                    expanded = showCurrencyChangeMenu,
                    onDismissRequest = { showCurrencyChangeMenu = false },
                ) {
                    sellableCurrencies.forEach {
                        DropdownMenuItem(onClick = {
                            showCurrencyChangeMenu = false
                            onCurrencyChange.invoke(it)
                        }) {
                            Text(
                                text = it.name,
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
