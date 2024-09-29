package com.android.sustentare.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.android.sustentare.ui.theme.GreenHigh

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Co2EmissionWorkScreen(navController: NavController) {
    var energyConsumption by remember { mutableStateOf("") }  // Energia em kWh
    var paperUsage by remember { mutableStateOf("") }          // Folhas de papel
    var plasticWaste by remember { mutableStateOf("") }        // Plástico em kg
    var co2FromEnergy by remember { mutableDoubleStateOf(0.0) }
    var co2FromPaper by remember { mutableDoubleStateOf(0.0) }
    var co2FromPlastic by remember { mutableDoubleStateOf(0.0) }
    var co2Total by remember { mutableDoubleStateOf(0.0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cálculo de Emissão de CO2 no Trabalho", fontSize = 22.sp) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                Text("Consumo de Energia Elétrica (em kWh)", fontSize = 18.sp)
                OutlinedTextField(
                    value = energyConsumption,
                    onValueChange = { energyConsumption = it },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text("Quantidade de Papel Utilizado (em folhas)", fontSize = 18.sp)
                OutlinedTextField(
                    value = paperUsage,
                    onValueChange = { paperUsage = it },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text("Peso do Plástico Descartado (em kg)", fontSize = 18.sp)
                OutlinedTextField(
                    value = plasticWaste,
                    onValueChange = { plasticWaste = it },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Botão centralizado e verde
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Button(
                        onClick = {
                            co2FromEnergy = (energyConsumption.toDoubleOrNull() ?: 0.0) * 0.233 // Energia
                            co2FromPaper = (paperUsage.toDoubleOrNull() ?: 0.0) * 0.005   // Papel
                            co2FromPlastic = (plasticWaste.toDoubleOrNull() ?: 0.0) * 6.0  // Plástico
                            co2Total = co2FromEnergy + co2FromPaper + co2FromPlastic
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = GreenHigh),
                        shape = RoundedCornerShape(50),
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text("Calcular Emissão", color = Color.White)
                    }
                }

                // Exibição dos resultados dentro de um Card
                if (co2Total > 0) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(8.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFf1f1f1))
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "Emissão por Consumo de Energia: %.2f kg de CO2".format(co2FromEnergy),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                            Text(
                                text = "Emissão por Consumo de Papel: %.2f kg de CO2".format(co2FromPaper),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                            Text(
                                text = "Emissão por Descarte de Plástico: %.2f kg de CO2".format(co2FromPlastic),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                            HorizontalDivider(thickness = 1.dp, color = Color.Gray)
                            Text(
                                text = "Emissão total estimada: %.2f kg de CO2".format(co2Total),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF00796B)
                            )
                        }
                    }
                }
            }
        }
    )
}