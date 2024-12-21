package com.ucp2.ui.view.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.animation.core.tween
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.ui.graphics.vector.ImageVector


@Composable
fun HomeView(
    onDosenClick: () -> Unit,
    onMataKuliahClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isVisible = true
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Header dengan animasi
        AnimatedVisibility(
            visible = isVisible,
            enter = fadeIn(animationSpec = tween(1000)) + scaleIn(initialScale = 0.8f)
        ) {
            Text(
                text = "Home",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF6200EA),
                modifier = Modifier
                    .padding(bottom = 32.dp)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AnimatedIconButton(
                text = "Go to Dosen",
                icon = Icons.Filled.AccountCircle,
                onClick = onDosenClick,
                modifier = Modifier.weight(1f)
            )
            AnimatedIconButton(
                text = "Go to Mata Kuliah",
                icon = Icons.Filled.Edit,
                onClick = onMataKuliahClick,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun AnimatedIconButton(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp), // Tinggi kotak
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF3A5FCD),
            contentColor = Color.White
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 8.dp,
            pressedElevation = 4.dp
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = text,
                modifier = Modifier.size(24.dp) // Ukuran ikon
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = text,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}