package com.Uma.byldwealth.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.Uma.byldwealth.ui.viewmodel.HoldingDetailViewModel

@Composable
fun HoldingDetailScreen(
    holdingId: String,
    viewModel: HoldingDetailViewModel = hiltViewModel()
) {
    LaunchedEffect(holdingId) {
        viewModel.loadHolding(holdingId)
    }

    val uiState by viewModel.uiState.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        when {
            uiState.isLoading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            uiState.error != null -> {
                Text("Error: ${uiState.error}", color = Color.Red)
            }
            uiState.holding != null -> {
                val holding = uiState.holding!!
                Text(holding.symbol, fontSize = 28.sp, fontWeight = FontWeight.Bold)
                Text(holding.companyName, color = Color.Gray, fontSize = 16.sp)
                Spacer(modifier = Modifier.height(16.dp))

                Card(modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(4.dp)) {
                    Column(modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Quantity"); Text("${holding.quantity}", fontWeight = FontWeight.Bold)
                        }
                        Row(modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Avg Cost"); Text("₹${holding.avgCost}", fontWeight = FontWeight.Bold)
                        }
                        Row(modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Current Price"); Text("₹${holding.currentPrice}", fontWeight = FontWeight.Bold)
                        }
                        val pnl = (holding.currentPrice - holding.avgCost) * holding.quantity
                        Row(modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("P&L")
                            Text("₹%.2f".format(pnl),
                                color = if (pnl >= 0) Color(0xFF2E7D32) else Color.Red,
                                fontWeight = FontWeight.Bold)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Variant B — Allocation Chart
                Text("Portfolio Allocation", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))

                val weight = uiState.allocationWeight
                Text("This holding: %.1f%% of your portfolio".format(weight),
                    fontSize = 14.sp, color = Color.Gray)

                Spacer(modifier = Modifier.height(16.dp))

                Box(modifier = Modifier.size(200.dp).align(Alignment.CenterHorizontally)) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val sweepAngle = (weight / 100f * 360f).toFloat()
                        drawArc(
                            color = Color(0xFF1565C0),
                            startAngle = -90f,
                            sweepAngle = sweepAngle,
                            useCenter = false,
                            style = androidx.compose.ui.graphics.drawscope.Stroke(width = 40f)
                        )
                        drawArc(
                            color = Color(0xFFE0E0E0),
                            startAngle = -90f + sweepAngle,
                            sweepAngle = 360f - sweepAngle,
                            useCenter = false,
                            style = androidx.compose.ui.graphics.drawscope.Stroke(width = 40f)
                        )
                    }
                    Text(
                        "%.1f%%".format(weight),
                        modifier = Modifier.align(Alignment.Center),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
            }
        }
    }
}