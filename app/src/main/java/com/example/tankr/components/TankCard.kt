package com.example.tankr.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tankr.ui.theme.Blue40
import com.example.tankr.ui.theme.Blue50
import com.example.tankr.ui.theme.Blue60
import com.example.tankr.ui.theme.Blue80
import com.example.tankr.ui.theme.Blue90
import com.example.tankr.ui.theme.Blue95
import com.example.tankr.ui.theme.TankrTheme

@Composable
fun TankCard(
    level : Double = 0.5,
    currentLiters : Int = 0,
    capacityLiters : Int = 1100,
    lastUpdate : Int = 0
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            // Nivel de llenado del tanque
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Nivel de agua",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.SemiBold
                )
                LevelBadge(level = level)
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                TankShape(
                    level = level,
                    modifier = Modifier.width(120.dp).height(200.dp)
                )
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Text(
                            text = (level * 100).toInt().toString(),
                            fontSize = 64.sp,
                            fontWeight = FontWeight.Bold,
                            color = Blue40,
                        )
                        Text(
                            text = "%",
                            fontSize = 40.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Blue40,
                            modifier = Modifier.padding(bottom = 10.dp)
                        )
                    }

                    Text(
                        text = "$currentLiters L de $capacityLiters L"
                    )
                    Text(
                        text = "Ultima lectura: hace $lastUpdate min"
                    )
                }
            }
        }
    }
}

@Composable
private fun LevelBadge(level: Double){
    // Tupla --> Pair ("Juan",12) Label (Titulo) Background (Color) Foreground(Color Texto)
    // Javascript / Typescript
    // Destructuracion de Objetos
    // Switch ----> w
    val (label, bg, fg) = when{
        level >= 0.7 -> Triple("Optimo", Blue90, Blue40)
        level >= 0.3 -> Triple("Medio", Color(0xFFFFF3E0), Color(0xFFE65100))
        else -> Triple("Bajo", Color(0xFFFFEBEE), Color(0xFFC62828))
    }

    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(bg)
            .padding(horizontal = 15.dp, vertical = 10.dp)
    ) {
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = fg
        )
    }
}

@Composable
private fun TankShape(level: Double, modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        androidx.compose.foundation.Canvas(modifier = Modifier.fillMaxSize()) {
            val cornerRadius = CornerRadius(28f, 28f)
            val strokeWidth = 6f
            val inset = strokeWidth / 2

            drawRoundRect(
                color = Blue95,
                topLeft = Offset(inset, inset),
                size = Size(size.width - strokeWidth, size.height - strokeWidth),
                cornerRadius = cornerRadius
            )

            val waterHeight = (size.height - strokeWidth) * level.toFloat()
            val waterTop = size.height - inset - waterHeight

            clipRect(
                left = inset,
                top = waterTop,
                right = size.width - inset,
                bottom = size.height - inset
            ) {
                drawRoundRect(
                    brush = Brush.verticalGradient(
                        colors = listOf(Blue60, Blue50, Blue40),
                        startY = waterTop,
                        endY = size.height
                    ),
                    topLeft = Offset(inset, inset),
                    size = Size(size.width - strokeWidth, size.height - strokeWidth),
                    cornerRadius = cornerRadius
                )
            }

            drawRoundRect(
                color = Blue50,
                topLeft = Offset(inset, inset),
                size = Size(size.width - strokeWidth, size.height - strokeWidth),
                cornerRadius = cornerRadius,
                style = Stroke(width = strokeWidth)
            )

            val markerSpacing = (size.height - strokeWidth) / 4f
            for (i in 1..3) {
                val y = size.height - inset - markerSpacing * i
                drawLine(
                    color = Blue80.copy(alpha = 0.6f),
                    start = Offset(inset + 6f, y),
                    end = Offset(inset + 18f, y),
                    strokeWidth = 2f
                )
            }
        }
    }
}

@Preview
@Composable
fun TankCardPreview(){
    TankrTheme {
        TankCard()
    }
}