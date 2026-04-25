package com.Uma.byldwealth.data.model

data class Holding(
    val id: String,
    val symbol: String,
    val companyName: String,
    val quantity: Int,
    val avgCost: Double,
    val currentPrice: Double,
    val currency: String
)