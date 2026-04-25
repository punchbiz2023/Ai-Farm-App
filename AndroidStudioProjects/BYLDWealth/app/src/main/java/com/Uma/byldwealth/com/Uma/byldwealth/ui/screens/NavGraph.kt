package com.Uma.byldwealth.ui.screens

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "holdings_list") {
        composable("holdings_list") {
            HoldingsListScreen(navController = navController)
        }
        composable("holding_detail/{holdingId}") { backStackEntry ->
            val holdingId = backStackEntry.arguments?.getString("holdingId") ?: ""
            HoldingDetailScreen(holdingId = holdingId)
        }
    }
}