package com.example.cookbook.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun WelcomeScreen(onStartClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF00FF9D)) // Kolor z makiety
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Książka Kucharska",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Text(
            text = "Twoje ulubione przepisy w jednym miejscu",
            modifier = Modifier.padding(top = 8.dp, bottom = 32.dp),
            color = Color.Black,
            fontSize = 16.sp
        )

        // Elementy listy (Karty z funkcjami)
        FeatureCard(
            icon = Icons.Default.List, // Zamień na Icons.Outlined.Book jeśli masz wgraną bibliotekę material-icons-extended
            title = "Przeglądaj przepisy",
            description = "Odkryj nowe smaki i przechowuj swoje ulubione przepisy"
        )

        FeatureCard(
            icon = Icons.Default.ShoppingCart,
            title = "Lista zakupów",
            description = "Planuj zakupy i zarządzaj składnikami"
        )

        FeatureCard(
            icon = Icons.Default.Build, // Zamień na Icons.Outlined.Inventory2 lub podobny (wymaga extended icons)
            title = "Spiżarka",
            description = "Zobacz co masz w lodówce i spiżarni"
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onStartClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
            shape = RoundedCornerShape(28.dp)
        ) {
            Text("Rozpocznij", color = Color(0xFF00FF9D), fontSize = 18.sp)
        }
    }
}

@Composable
fun FeatureCard(icon: ImageVector, title: String, description: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Black),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color(0xFF00FF9D),
                modifier = Modifier
                    .size(32.dp)
                    .padding(bottom = 12.dp)
            )
            Text(
                text = title,
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = description,
                color = Color(0xFFD0D0D0), // Jasnoszary kolor dla opisu
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 4.dp),
                lineHeight = 20.sp
            )
        }
    }
}