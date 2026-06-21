package com.example.tankr

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tankr.components.Header
import com.example.tankr.components.TankCard
import com.example.tankr.data.RetrofitInstance
import com.example.tankr.data.TankInfo
import com.example.tankr.data.TankService
import com.example.tankr.ui.theme.Blue40
import com.example.tankr.ui.theme.Blue90
import com.example.tankr.ui.theme.Blue95
import com.example.tankr.ui.theme.Cyan60
import com.example.tankr.ui.theme.TankrTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TankrTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TankrScreen(innerPadding)
                }
            }
        }
    }
}

@Composable
fun TankrScreen(
    innerPadding : PaddingValues = PaddingValues(10.dp),
){
    var tankInfo by remember {
        mutableStateOf<TankInfo?>(null)
    }

    LaunchedEffect(true) {
        try{
            val tankService = RetrofitInstance.retrofitInstance.create(TankService::class.java)
            tankInfo = tankService.getTankInfo("22222222-2222-4222-8222-000000000016")
        }
        catch (e: Exception){
            println(e.toString())
        }
    }
    val waterLevel = ((tankInfo?.percentage?.toDouble())?.div(100)) ?: 0.0

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Blue95,Color.White)
                )
            )
            .padding(innerPadding)
            .padding(20.dp)
    ) {
        // Header
        Header()
        // TankCard
        TankCard(
            level = waterLevel
        )
        // Stats Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        )
        {
            // Tarjeta Flujo
            Card(
                modifier = Modifier.weight(1f),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 2.dp
                ),
                shape = RoundedCornerShape(20.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = "Flujo",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "12 L / min ",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Blue40
                    )
                }
            }
            // Tarjeta Estado
            Card(
                modifier = Modifier.weight(1f),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 2.dp
                ),
                shape = RoundedCornerShape(20.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = "Estado",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "Estable",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Blue40
                    )
                }
            }
        }
        // PumpCard
        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 2.dp
            ),
            modifier = Modifier.padding(top = 20.dp)
        )
        {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Bomba de Agua",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Blue40
                    )
                    Text(
                        text = "Encendido",
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Switch(
                    checked = true,
                    onCheckedChange = { },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = Cyan60,
                        checkedBorderColor = Cyan60,
                        uncheckedThumbColor = Color.White,
                        uncheckedTrackColor = Blue90,
                        uncheckedBorderColor = Blue90
                    )
                )
            }
        }
        // Boton Consultar info
        val scope = rememberCoroutineScope()
        Button(
            onClick = {
                scope.launch {
                    try{
                        val tankService = RetrofitInstance.retrofitInstance.create(TankService::class.java)
                        tankInfo = tankService.getTankInfo("22222222-2222-4222-8222-000000000016")
                    }
                    catch (e: Exception){
                        println(e.toString())
                    }
                }
            },
            modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(20.dp),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 2.dp
            )
        ) {
            Text(
                text = "Consultar Informacion"
            )
        }


        // Websockets

        // HTTP Pooling //// Rate Limit
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun TankrScreenPreview(){
    TankrTheme {
        TankrScreen()
    }
}