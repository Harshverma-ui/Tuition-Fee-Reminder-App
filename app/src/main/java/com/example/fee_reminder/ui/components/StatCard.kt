package com.example.fee_reminder.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Icon
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import com.example.fee_reminder.ui.theme.AppDimens
import androidx.compose.ui.graphics.Color
import androidx.compose.material.icons.Icons

@Composable
fun StatCard(

    title: String,
    value: String,
    icon: ImageVector

) {

    Card(
        modifier = Modifier
            .width(165.dp)
            .height(170.dp),
        shape = RoundedCornerShape(AppDimens.CardRadius),
        elevation = CardDefaults.cardElevation(
            defaultElevation = AppDimens.CardElevation
        )
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(AppDimens.MediumSpacing),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(30.dp)
            )

            Spacer(
                modifier = Modifier.height(AppDimens.MediumSpacing)
            )

            Text(
                text = value,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(AppDimens.SmallSpacing)
            )

            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium
            )

        }

    }

}