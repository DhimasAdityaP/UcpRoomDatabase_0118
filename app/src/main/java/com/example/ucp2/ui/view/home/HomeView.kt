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
import androidx.compose.foundation.clickable
import androidx.compose.ui.draw.scale

@Composable
fun HomeView(
    onDosenClick: () -> Unit,
    onMataKuliahClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // State untuk kontrol animasi
    var isVisible by remember { mutableStateOf(false) }

    // Memicu animasi saat tampilan dimuat
    LaunchedEffect(Unit) {
        isVisible = true
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AnimatedVisibility(
            visible = isVisible,
            enter = fadeIn(animationSpec = tween(1000)) + scaleIn(initialScale = 0.8f)
        ) {
            Text(
                text = "Home",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF6200EA) // Warna utama
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        AnimatedButton(text = "Go to Dosen", onClick = onDosenClick)
        Spacer(modifier = Modifier.height(16.dp))
        AnimatedButton(text = "Go to Mata Kuliah", onClick = onMataKuliahClick)
    }
}

@Composable
fun AnimatedButton(text: String, onClick: () -> Unit) {
    var isPressed by remember { mutableStateOf(false) }

    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF6200EA),
            contentColor = Color.White
        ),
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .clickable { isPressed = !isPressed }
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .animateScale(isPressed) // Menambahkan animasi pada teks
        )
    }
}

@Composable
fun Modifier.animateScale(isPressed: Boolean): Modifier {
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 1.1f else 1.0f,
        animationSpec = tween(durationMillis = 300)
    )
    return this.then(Modifier.scale(scale))
}
