package com.badsha.currencyconversion.presentation.screen.home.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.badsha.currencyconversion.domain.model.Currency


@Composable
fun ChargeBarComponent(chargeAmount: Double, sellingCurrency: Currency?) {
    Log.i("TAG", chargeAmount.toString())
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
        Text(
            chargeAmount.toString(),
            style = MaterialTheme.typography.body2,
            textAlign = TextAlign.End,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                sellingCurrency?.name ?: "--",
                style = MaterialTheme.typography.subtitle1,
                fontWeight = FontWeight.Bold
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
                    contentDescription = "Charge",
                )
            }
        }
    }
}
