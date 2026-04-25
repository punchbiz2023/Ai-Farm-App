package com.Uma.byldwealth.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.Uma.byldwealth.ui.viewmodel.HoldingsViewModel

@Composable
fun HoldingsListScreen(
    navController: NavController,
    viewModel: HoldingsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("My Portfolio", fontSize = 24.sp, fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp))

        when {
            uiState.isLoading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            uiState.error != null -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Error: ${uiState.error}", color = Color.Red)
                }
            }
            uiState.holdings.isEmpty() -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No holdings found")
                }
            }
            else -> {
                val totalValue = uiState.holdings.sumOf { it.currentPrice * it.quantity }
                Text("Total Value: ₹%.2f".format(totalValue),
                    fontSize = 16.sp, modifier = Modifier.padding(bottom = 12.dp))

                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(uiState.holdings) { holding ->
                        Card(
                            modifier = Modifier.fillMaxWidth()
                                .clickable { navController.navigate("holding_detail/${holding.id}") },
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(holding.symbol, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                                Text(holding.companyName, color = Color.Gray, fontSize = 13.sp)
                                Spacer(modifier = Modifier.height(8.dp))
                                Row(modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text("Qty: ${holding.quantity}")
                                    Text("₹${holding.currentPrice}")
                                }
                                val pnl = (holding.currentPrice - holding.avgCost) * holding.quantity
                                Text(
                                    "P&L: ₹%.2f".format(pnl),
                                    color = if (pnl >= 0) Color(0xFF2E7D32) else Color.Red,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}