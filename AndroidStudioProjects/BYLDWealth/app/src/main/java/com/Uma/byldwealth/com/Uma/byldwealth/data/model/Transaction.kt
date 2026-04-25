package com.Uma.byldwealth.data.model

data class Transaction(
    val id: String,
    val holdingId: String,
    val type: String,
    val date: String,
    val quantity: Int,
    val price: Double
)